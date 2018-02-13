package com.teamdecano.cryptocoin.root

import com.teamdecano.cryptocoin.navigation.NavigationInteractor
import com.uber.rib.core.Bundle
import com.uber.rib.core.Interactor
import com.uber.rib.core.RibInteractor
import io.objectbox.BoxStore
import javax.inject.Inject

/**
 * Coordinates Business Logic for [RootScope].
 *
 * TODO describe the logic of this scope.
 */
@RibInteractor
class RootInteractor : Interactor<RootInteractor.RootPresenter, RootRouter>() {

    @Inject
    lateinit var presenter: RootPresenter

    @Inject
    lateinit var boxstore: BoxStore

    override fun didBecomeActive(savedInstanceState: Bundle?) {
        super.didBecomeActive(savedInstanceState)
        router.attachNavigation()
    }

    fun attachCoinList() {

        router.detachIco()
        router.detachConvert()
        router.detachSettings()

        router.attachCoinList()
    }

    fun attachIco() {

        router.detachCoinList()
        router.detachConvert()
        router.detachSettings()

        router.attachIco()
    }

    fun attachConvert() {
        router.attachConvert()

        router.detachCoinList()
        router.detachIco()
        router.detachSettings()
    }

    fun attachSettings() {

        router.detachCoinList()
        router.detachIco()
        router.detachConvert()

        router.attachSettings()
    }

    /**
     * Presenter interface implemented by this RIB's view.
     */
    interface RootPresenter

    inner class NavigationListener : NavigationInteractor.Listener {

        override fun converterSelected() {
            attachConvert()
        }

        override fun settingsSelected() {
            attachSettings()
        }

        override fun coinListSelected() {
            attachCoinList()
        }

        override fun icoListSelected() {
            attachIco()
        }
    }
}
