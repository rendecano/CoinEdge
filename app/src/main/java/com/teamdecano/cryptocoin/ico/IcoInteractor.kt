package com.teamdecano.cryptocoin.ico

import com.teamdecano.cryptocoin.ico.data.model.IcoItem
import com.teamdecano.cryptocoin.ico.data.repository.source.IcoListLocalRepository
import com.teamdecano.cryptocoin.ico.data.repository.source.IcoListNetworkRepository
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
 * Coordinates Business Logic for [IcoScope].
 *
 * TODO describe the logic of this scope.
 */
@RibInteractor
class IcoInteractor : Interactor<IcoInteractor.IcoPresenter, IcoRouter>() {

    @Inject
    lateinit var presenter: IcoPresenter

    @Inject
    lateinit var icoListLocalRepository: IcoListLocalRepository

    @Inject
    lateinit var icoListNetworkRepository: IcoListNetworkRepository

    override fun didBecomeActive(savedInstanceState: Bundle?) {
        super.didBecomeActive(savedInstanceState)

        getCachedContents()
        updateListFromNetwork()
    }

    private fun getCachedContents() {

        launchAsync {

            try {

                val coinList = icoListLocalRepository.getList()
                presenter.loadActiveIco(coinList.await())

            } catch (exception: Exception) {

                presenter.showError("Error occured")
                presenter.hideLoadingProgress()
            }
        }
    }

    private fun updateListFromNetwork() {

        launchAsync {

            try {

                val icoActiveList = icoListNetworkRepository.getActiveIcoList()
                val icoUpcomingList = icoListNetworkRepository.getUpcomingIcoList()
                icoListLocalRepository.updateList(icoActiveList, icoUpcomingList)
                var list = icoListLocalRepository.getList()

                presenter.loadActiveIco(list.await())
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
    interface IcoPresenter {

        fun loadActiveIco(icoList: List<List<IcoItem>>)
        fun showLoadingProgress()
        fun hideLoadingProgress()
        fun showError(error: String)
    }

    private fun launchAsync(block: suspend CoroutineScope.() -> Unit): Job {
        return launch(UI) { block() }
    }
}
