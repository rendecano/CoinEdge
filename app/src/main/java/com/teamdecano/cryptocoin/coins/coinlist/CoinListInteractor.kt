package com.teamdecano.cryptocoin.coins.coinlist

import com.teamdecano.cryptocoin.services.CoinService
import com.teamdecano.cryptocoin.services.model.CoinList
import com.uber.rib.core.Bundle
import com.uber.rib.core.Interactor
import com.uber.rib.core.RibInteractor
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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

    override fun didBecomeActive(savedInstanceState: Bundle?) {
        super.didBecomeActive(savedInstanceState)

        // Listen for item selection
        presenter
                .onSelectCoin()
                .subscribe({ coinListViewModel -> router.routeToCoinDetails(coinListViewModel) })

        presenter.showLoadingProgress()

        // TODO: Inject coin service

        var coinService = CoinService()

        coinService.getCoinList().enqueue(object : Callback<CoinList> {

            override fun onFailure(call: Call<CoinList>?, t: Throwable?) {

                presenter.showError(t!!.message.toString())
                presenter.showLoadingProgress()

            }

            override fun onResponse(call: Call<CoinList>?, response: Response<CoinList>) {

                if (response.isSuccessful) {

                    val coinList = ArrayList<CoinListViewModel>()

                    val baseUrl = response.body()!!.baseImageUrl

                    for (item in response.body()!!.feeds!!.entries) {
                        coinList.add(CoinListViewModel(item.value.id,
                                baseUrl + item.value.imageUrl,
                                item.value.name,
                                item.value.coinName,
                                item.value.fullName,
                                item.value.sortOrder))
                    }

                    presenter.showCoinList(coinList.sortedWith(CoinSorter))

                } else {
                    presenter.showError("Ooops. An error occured. Please try again.")
                }
                presenter.hideLoadingProgress()
            }
        })
    }

    private class CoinSorter {

        companion object : Comparator<CoinListViewModel> {

            override fun compare(a: CoinListViewModel, b: CoinListViewModel): Int = when {
                a.sortOrder!!.toInt() != b.sortOrder!!.toInt() -> a.sortOrder!!.toInt() - b.sortOrder!!.toInt()
                else -> a.sortOrder!!.toInt() - b.sortOrder!!.toInt()
            }
        }
    }

    /**
     * Presenter interface implemented by this RIB's view.
     */
    interface CoinListPresenter {

        fun showCoinList(coinList: List<CoinListViewModel>)
        fun showLoadingProgress()
        fun hideLoadingProgress()
        fun showError(error: String)
        fun onSelectCoin(): Observable<CoinListViewModel>
    }
}
