package com.teamdecano.cryptocoin.navigation

import com.uber.rib.core.Bundle
import com.uber.rib.core.Interactor
import com.uber.rib.core.RibInteractor
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.exceptions.OnErrorNotImplementedException
import javax.inject.Inject

/**
 * Coordinates Business Logic for [NavigationScope].
 *
 * TODO describe the logic of this scope.
 */
@RibInteractor
class NavigationInteractor : Interactor<NavigationInteractor.NavigationPresenter, NavigationRouter>() {

    @Inject
    lateinit var presenter: NavigationPresenter

    @Inject
    lateinit var listener: Listener

    private val disposables = CompositeDisposable()

    override fun didBecomeActive(savedInstanceState: Bundle?) {
        super.didBecomeActive(savedInstanceState)

        disposables.add(presenter.menuEvents()
                .subscribe({ event ->
                    when (event) {

                        MenuEvent.LIST -> listener.coinListSelected()
                        MenuEvent.ICO -> listener.icoListSelected()
                        MenuEvent.CONVERT -> listener.converterSelected()
                        MenuEvent.SETTINGS -> listener.settingsSelected()

                    }
                }, { OnErrorNotImplementedException(it) }))

    }

    override fun willResignActive() {
        super.willResignActive()
        disposables.clear()
    }

    /**
     * Presenter interface implemented by this RIB's view.
     */
    interface NavigationPresenter {
        fun menuEvents(): Observable<MenuEvent>
    }

    interface Listener {

        fun coinListSelected()
        fun icoListSelected()
        fun converterSelected()
        fun settingsSelected()
    }
}
