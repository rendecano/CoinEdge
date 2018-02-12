package com.teamdecano.cryptocoin.coins.coindetails

import android.content.Context
import android.graphics.Bitmap
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.graphics.Palette
import android.text.Html
import android.util.AttributeSet
import android.view.View
import android.widget.*
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.jakewharton.rxbinding2.view.RxView
import com.teamdecano.cryptocoin.R
import io.reactivex.Observable
import kotlinx.android.synthetic.main.coin_details_features.view.*
import kotlinx.android.synthetic.main.coin_details_info.view.*
import kotlinx.android.synthetic.main.coin_details_rib.view.*
import java.util.*

/**
 * Top level view for [CoinDetailsBuilder.CoinDetailsScope].
 */
class CoinDetailsView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : RelativeLayout(context, attrs, defStyle), CoinDetailsInteractor.CoinDetailsPresenter {

    private lateinit var coinDetailsViewModel: CoinDetailsViewModel

    override fun onFinishInflate() {
        super.onFinishInflate()

        details_layout.visibility = View.GONE
    }

    override fun showDetails(coinDetailsViewModel: CoinDetailsViewModel) {

        this.coinDetailsViewModel = coinDetailsViewModel

        // TODO: We can use data binding here
        details_layout.visibility = View.VISIBLE

        txt_symbol.text = coinDetailsViewModel.symbol
        txt_name.text = coinDetailsViewModel.name
        txt_description.text = Html.fromHtml(coinDetailsViewModel.description)

        txt_features.text = Html.fromHtml(coinDetailsViewModel.features)

        txt_coin_supply.text = coinDetailsViewModel.totalCoinSupply
        txt_start_date.text = coinDetailsViewModel.startDate

        if (coinDetailsViewModel.website.isNullOrEmpty()) {
            btn_website.visibility = View.GONE
        }

        if (coinDetailsViewModel.twitterObj?.link.isNullOrEmpty()) {
            btn_twitter.visibility = View.GONE
        }

        try {
            Glide.with(context)
                    .asBitmap()
                    .load(coinDetailsViewModel.imageUrl)
                    .into(object : SimpleTarget<Bitmap>(96, 96) {
                        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                            coin_logo.setImageBitmap(resource)
                            bg_dominant.setBackgroundColor(getDominantColor(resource))
                        }
                    })
        } catch (e: Exception) {
            // No implementation
        }
    }

    override fun showLoadingProgress() {

        findViewById<LottieAnimationView>(R.id.animation_view).visibility = View.VISIBLE
        findViewById<LottieAnimationView>(R.id.animation_view).playAnimation()

    }

    override fun hideLoadingProgress() {

        findViewById<LottieAnimationView>(R.id.animation_view).visibility = View.GONE
        findViewById<LottieAnimationView>(R.id.animation_view).cancelAnimation()
    }

    override fun showError(error: String) {
        Snackbar.make(this, error, Snackbar.LENGTH_SHORT).show()
    }

    fun getDominantColor(bitmap: Bitmap): Int {
        val swatchesTemp = Palette.from(bitmap).generate().swatches
        val swatches = ArrayList(swatchesTemp)
        Collections.sort(swatches, object : Comparator<Palette.Swatch> {
            override fun compare(swatch1: Palette.Swatch, swatch2: Palette.Swatch): Int {
                return swatch2.population - swatch1.population
            }
        })
        return if (swatches.size > 0) swatches[0].rgb else ContextCompat.getColor(context, R.color.colorPrimary)
    }

    override fun showTwitter(): Observable<String> {
        return RxView.clicks(findViewById<Button>(R.id.btn_twitter))
                .map { coinDetailsViewModel.twitterObj?.link }
    }

    override fun showWebsite(): Observable<String> {
        return RxView.clicks(findViewById<Button>(R.id.btn_website))
                .map { coinDetailsViewModel.website }
    }
}
