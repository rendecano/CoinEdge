package com.teamdecano.cryptocoin.coins.coindetails

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.teamdecano.cryptocoin.coins.coindetails.data.repository.source.CoinDetailsLocalRepository
import com.teamdecano.cryptocoin.coins.coindetails.data.repository.source.CoinDetailsNetworkRepository
import com.teamdecano.cryptocoin.coins.coinlist.data.network.CoinService
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
    lateinit var coinDetailsNetworkRepository: CoinDetailsNetworkRepository

    @Inject
    lateinit var coinDetailsLocalRepository: CoinDetailsLocalRepository

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
                    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(twitterUsername)))
                })

        getCachedContents()
        getDetailsFromNetwork()
    }

    private fun getCachedContents() {
        launchAsync {

            try {

                val coinDetails = coinDetailsLocalRepository.getCoinDetails(coinId) ?: CoinDetailsViewModel()
                presenter.showDetails(coinDetails)

            } catch (exception: Exception) {

                presenter.showError("Ooops. An error occured. Please try again.")
                presenter.hideLoadingProgress()
            }
        }
    }

    private fun getDetailsFromNetwork() {

        launchAsync {

            try {

                val coinDetailsObject = coinDetailsNetworkRepository.getCoinDetails(coinId)
                val coinSocialStats = coinDetailsNetworkRepository.getSocialStats(coinId)
                coinDetailsLocalRepository.updateList(coinDetailsObject, coinSocialStats)
                val coinDetails = coinDetailsLocalRepository.getCoinDetails(coinId) ?: CoinDetailsViewModel()

                // try to use live data

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
