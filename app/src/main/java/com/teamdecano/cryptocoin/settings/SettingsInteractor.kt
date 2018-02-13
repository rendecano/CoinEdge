package com.teamdecano.cryptocoin.settings

import com.uber.rib.core.Bundle
import com.uber.rib.core.Interactor
import com.uber.rib.core.RibInteractor
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Coordinates Business Logic for [SettingsScope].
 *
 * TODO describe the logic of this scope.
 */
@RibInteractor
class SettingsInteractor : Interactor<SettingsInteractor.SettingsPresenter, SettingsRouter>() {

    @Inject
    lateinit var presenter: SettingsPresenter

    override fun didBecomeActive(savedInstanceState: Bundle?) {
        super.didBecomeActive(savedInstanceState)


        presenter.selectChasingCoins().subscribe({

            router.routeToWeb(UrlAddress("https://chasing-coins.com"))
        })


        presenter.selectCryptoCompare().subscribe({

            router.routeToWeb(UrlAddress("https://www.cryptocompare.com"))
        })

        presenter.selectCoinMarketCap().subscribe({

            router.routeToWeb(UrlAddress("https://coinmarketcap.com"))
        })

    }

    /**
     * Presenter interface implemented by this RIB's view.
     */
    interface SettingsPresenter {

        fun selectCryptoCompare(): Observable<Any>
        fun selectCoinMarketCap(): Observable<Any>
        fun selectChasingCoins(): Observable<Any>
    }

    data class UrlAddress(val value: String)
}
