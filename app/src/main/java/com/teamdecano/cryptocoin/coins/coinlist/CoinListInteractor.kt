package com.teamdecano.cryptocoin.coins.coinlist

import com.teamdecano.cryptocoin.coins.coinlist.data.repository.source.CoinListLocalRepository
import com.teamdecano.cryptocoin.coins.coinlist.data.repository.source.CoinListNetworkRepository
import com.teamdecano.cryptocoin.coins.coinlist.presentation.CoinListModel
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
 * Coordinates Business Logic for [CoinListScope].
 *
 * TODO describe the logic of this scope.
 */
@RibInteractor
class CoinListInteractor : Interactor<CoinListInteractor.CoinListPresenter, CoinListRouter>() {

    @Inject
    lateinit var presenter: CoinListPresenter

    @Inject
    lateinit var coinListNetworkRepository: CoinListNetworkRepository

    @Inject
    lateinit var coinListLocalRepository: CoinListLocalRepository

    override fun didBecomeActive(savedInstanceState: Bundle?) {
        super.didBecomeActive(savedInstanceState)

        // Listen for item selection
        presenter.onSelectCoin()
                .subscribe({ coinListViewModel ->

                    coinListViewModel.id = coinListLocalRepository.getId(coinListViewModel.name!!)

                    if (!coinListViewModel.id.isNullOrEmpty()) {
                        router.routeToCoinDetails(coinListViewModel)
                    } else {
                        presenter.showError("No data found for this crypto currency")
                    }
                })

        presenter.onRefresh().subscribe({

            updateListFromNetwork()

        })

        presenter.showLoadingProgress()

        getCachedContents()
        updateListFromNetwork()
    }

    private fun getCachedContents() {
        launchAsync {

            try {

                val coinList = coinListLocalRepository.getList()
                presenter.showCoinList(coinList.await())

            } catch (exception: Exception) {

                presenter.showError("Error occured")
                presenter.hideLoadingProgress()
            }
        }
    }

    private fun updateListFromNetwork() {

        launchAsync {

            try {

                val coinListObjectCmc = coinListNetworkRepository.getCoinListCoinMarketCap()
                val coinListObject = coinListNetworkRepository.getCoinListCcc()
                coinListLocalRepository.updateList(coinListObjectCmc, coinListObject)
                val coinList = coinListLocalRepository.getList()

                presenter.showCoinList(coinList.await())
                presenter.hideLoadingProgress()

            } catch (exception: Exception) {

                presenter.showError("Error occured")
                presenter.hideLoadingProgress()
            }
        }
    }

    /**
     * Presenter interface implemented by this RIB's view.
     */
    interface CoinListPresenter {

        fun showCoinList(coinList: List<CoinListModel>)
        fun showLoadingProgress()
        fun hideLoadingProgress()
        fun showError(error: String)
        fun onSelectCoin(): Observable<CoinListModel>
        fun onRefresh(): Observable<Any>
    }

    private fun launchAsync(block: suspend CoroutineScope.() -> Unit): Job {
        return launch(UI) { block() }
    }
}
