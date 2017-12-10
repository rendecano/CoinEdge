package com.teamdecano.cryptocoin.coins.coindetails

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.teamdecano.cryptocoin.services.CoinService
import com.teamdecano.cryptocoin.services.model.CoinDetails
import com.uber.rib.core.Bundle
import com.uber.rib.core.Interactor
import com.uber.rib.core.RibInteractor
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

/**
 * Coordinates Business Logic for [CoinDetailsScope].
 *
 * TODO describe the logic of this scope.
 */
@RibInteractor
class CoinDetailsInteractor : Interactor<CoinDetailsInteractor.CoinDetailsPresenter, CoinDetailsRouter>() {

    @Inject
    lateinit var presenter: CoinDetailsPresenter

    @Inject
    lateinit var coinId: String

    @Inject
    lateinit var context: Context

    override fun didBecomeActive(savedInstanceState: Bundle?) {
        super.didBecomeActive(savedInstanceState)

        var coinService = CoinService()

        presenter.showLoadingProgress()

        // TODO: Refactor this code to use Kotlin's coroutines. Nice!!!
        coinService.getCoinDetails(coinId).enqueue(object : Callback<CoinDetails> {

            override fun onFailure(call: Call<CoinDetails>?, t: Throwable?) {

                presenter.showError(t!!.message.toString())
                presenter.hideLoadingProgress()

            }

            override fun onResponse(call: Call<CoinDetails>?, response: Response<CoinDetails>) {

                if (response?.body() != null && response.isSuccessful) {

                    val baseUrl = response.body()?.data?.seo?.baseImageUrl
                    val general = response.body()?.data?.general
                    val ico = response.body()?.data?.ico

                    if (general != null && ico != null) {

                        val coinDetails = CoinDetailsViewModel(general.name,
                                general.symbol,
                                general.description,
                                general.technology,
                                general.features,
                                baseUrl + general.imageUrl,
                                general.totalCoinSupply,
                                general.startDate,
                                if (!general.affiliateUrl.isNullOrEmpty() && general.affiliateUrl!!.contains("http")) general.affiliateUrl else "",
                                general.twitter,
                                baseUrl + general.sponsor?.imageUrl,
                                ico.status,
                                ico.description,
                                ico.icoTokenSupply,
                                ico.fundingTarget,
                                ico.whitePaperLink)

                        presenter.showDetails(coinDetails)
                    }

                } else {
                    presenter.showError("Ooops. An error occured. Please try again.")
                }

                presenter.hideLoadingProgress()
            }
        })

        presenter.showWebsite().subscribe({ url ->
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            context.startActivity(browserIntent)

        })

        presenter.showTwitter().subscribe({ twitterUsername ->
            try {
                context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name=" + twitterUsername)))
            } catch (e: Exception) {
                context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/#!/" + twitterUsername)))
            }
        })
    }

    /**
     * Presenter interface implemented by this RIB's view.
     */
    interface CoinDetailsPresenter {

        fun showDetails(coinDetailsViewModel: CoinDetailsViewModel)
        fun showLoadingProgress()
        fun hideLoadingProgress()
        fun showError(error: String)
        fun showWebsite(): Observable<String>
        fun showTwitter(): Observable<String>
    }

    interface Listener
}
