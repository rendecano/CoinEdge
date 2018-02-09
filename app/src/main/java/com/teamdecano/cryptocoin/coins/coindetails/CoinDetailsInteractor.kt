package com.teamdecano.cryptocoin.coins.coindetails

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.teamdecano.cryptocoin.coins.coinlist.data.network.CoinService
import com.teamdecano.cryptocoin.coins.coinlist.data.network.IcoService
import com.uber.rib.core.Bundle
import com.uber.rib.core.Interactor
import com.uber.rib.core.RibInteractor
import io.reactivex.Observable
import kotlinx.coroutines.experimental.CoroutineScope
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
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

    @Inject
    lateinit var coinService: CoinService

    override fun didBecomeActive(savedInstanceState: Bundle?) {
        super.didBecomeActive(savedInstanceState)

        presenter.showLoadingProgress()


        presenter.showWebsite().subscribe(
                { url ->
                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    context.startActivity(browserIntent)

                })

        presenter.showTwitter().subscribe(
                { twitterUsername ->
                    try {
                        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name=" + twitterUsername)))
                    } catch (e: Exception) {
                        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/#!/" + twitterUsername)))
                    }
                })

        getDetailsFromNetwork()
    }

    private fun getDetailsFromNetwork() {

        launchAsync {

            try {

                val coinDetailsObject = coinService.getCoinDetails(coinId)

                val baseUrl = coinDetailsObject.data.seo.baseImageUrl
                val general = coinDetailsObject.data.general
                val ico = coinDetailsObject.data.ico

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
                presenter.hideLoadingProgress()


            } catch (exception: Exception) {
                presenter.showError("Ooops. An error occured. Please try again.")
                presenter.hideLoadingProgress()
            }
        }
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

    private fun launchAsync(block: suspend CoroutineScope.() -> Unit): Job {
        return launch(UI) { block() }
    }
}
