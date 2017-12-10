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
import java.util.*

/**
 * Top level view for [CoinDetailsBuilder.CoinDetailsScope].
 */
class CoinDetailsView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : RelativeLayout(context, attrs, defStyle), CoinDetailsInteractor.CoinDetailsPresenter {

    private lateinit var coinDetailsViewModel: CoinDetailsViewModel

    override fun onFinishInflate() {
        super.onFinishInflate()

        findViewById<LinearLayout>(R.id.details_layout).visibility = View.GONE
    }

    override fun showDetails(coinDetailsViewModel: CoinDetailsViewModel) {

        this.coinDetailsViewModel = coinDetailsViewModel

        // TODO: We can use data binding here
        findViewById<LinearLayout>(R.id.details_layout).visibility = View.VISIBLE

        findViewById<TextView>(R.id.txt_symbol).setText(coinDetailsViewModel.symbol)
        findViewById<TextView>(R.id.txt_name).setText(coinDetailsViewModel.name)
        findViewById<TextView>(R.id.txt_description).setText(Html.fromHtml(coinDetailsViewModel.description))

        findViewById<TextView>(R.id.txt_features).setText(Html.fromHtml(coinDetailsViewModel.features))

        findViewById<TextView>(R.id.txt_coin_supply).setText(coinDetailsViewModel.totalCoinSupply)
        findViewById<TextView>(R.id.txt_start_date).setText(coinDetailsViewModel.startDate)

        if (coinDetailsViewModel.website.isNullOrEmpty() || coinDetailsViewModel.website.isNullOrBlank()) {
            findViewById<Button>(R.id.btn_website).visibility = View.GONE
        }

        if (coinDetailsViewModel.twitter.isNullOrEmpty()) {
            findViewById<Button>(R.id.btn_twitter).visibility = View.GONE
        }

        try {
            Glide.with(context)
                    .asBitmap()
                    .load(coinDetailsViewModel.imageUrl)
                    .into(object : SimpleTarget<Bitmap>(96, 96) {
                        override fun onResourceReady(resource: Bitmap?, transition: Transition<in Bitmap>?) {
                            findViewById<ImageView>(R.id.coin_logo).setImageBitmap(resource)
                            findViewById<View>(R.id.bg_dominant).setBackgroundColor(getDominantColor(resource!!))
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
                .map { coinDetailsViewModel.twitter }
    }

    override fun showWebsite(): Observable<String> {
        return RxView.clicks(findViewById<Button>(R.id.btn_website))
                .map { coinDetailsViewModel.website }
    }
}
