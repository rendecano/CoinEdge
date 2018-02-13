package com.teamdecano.cryptocoin.root

import com.teamdecano.cryptocoin.R
import com.teamdecano.cryptocoin.coins.CoinBuilder
import com.teamdecano.cryptocoin.coins.CoinRouter
import com.teamdecano.cryptocoin.common.screen_stack.ScreenStack
import com.teamdecano.cryptocoin.ico.IcoBuilder
import com.teamdecano.cryptocoin.ico.IcoRouter
import com.teamdecano.cryptocoin.navigation.NavigationBuilder
import com.teamdecano.cryptocoin.navigation.NavigationRouter
import com.teamdecano.cryptocoin.settings.SettingsBuilder
import com.teamdecano.cryptocoin.settings.SettingsRouter

import com.uber.rib.core.ViewRouter
import io.reactivex.disposables.CompositeDisposable

/**
 * Adds and removes children of [RootBuilder.RootScope].
 *
 * TODO describe the possible child configurations of this scope.
 */
class RootRouter(
        view: RootView,
        private val screenStack: ScreenStack,
        interactor: RootInteractor,
        component: RootBuilder.Component,
        val navigationBuilder: NavigationBuilder,
        val coinBuilder: CoinBuilder,
        val icoBuilder: IcoBuilder,
        val settingsBuilder: SettingsBuilder) : ViewRouter<RootView, RootInteractor, RootBuilder.Component>(view, interactor, component) {

    private var navigationRouter: NavigationRouter? = navigationBuilder.build(view.viewNavigationLayout())
    private var coinRouter: CoinRouter? = null
    private var icoRouter: IcoRouter? = null
    private var settingsRouter: SettingsRouter? = null

    private val disposables = CompositeDisposable()

    fun attachNavigation() {

        navigationRouter = navigationBuilder.build(view.viewNavigationLayout())
        attachChild(navigationRouter)
        view.viewNavigationLayout().addView(navigationRouter?.view)
    }

    fun detachNavigation() {
        if (navigationRouter != null) {
            detachChild(navigationRouter)
            navigationRouter = null
        }
    }

    fun attachCoinList() {
        view.getToolbarTitle().setText(R.string.coin_list_title)
        coinRouter = coinBuilder.build()
        attachChild(coinRouter)
    }

    fun detachCoinList() {

        if (coinRouter != null) {
            detachChild(coinRouter)
            coinRouter = null
        }
    }

    fun attachIco() {

        view.getToolbarTitle().setText(R.string.ico_list_title)
        icoRouter = icoBuilder.build(view.viewContent())
        attachChild(icoRouter)
        view.viewContent().addView(icoRouter?.view)
    }

    fun detachIco() {
        if (icoRouter != null) {
            detachChild(icoRouter)
            view.viewContent().removeView(icoRouter?.view)
            icoRouter = null
        }
    }

    fun attachConvert() {
        view.getToolbarTitle().setText(R.string.converter_title)
    }

    fun detachConvert() {

    }

    fun attachSettings() {
        view.getToolbarTitle().setText(R.string.about_title)
        settingsRouter = settingsBuilder.build(view.viewContent())
        attachChild(settingsRouter)
        view.viewContent().addView(settingsRouter?.view)
    }

    fun detachSettings() {
        if (settingsRouter != null) {
            detachChild(settingsRouter)
            view.viewContent().removeView(settingsRouter?.view)
            settingsRouter = null
        }
    }

    override fun willDetach() {
        super.willDetach()

        disposables.clear()
        detachCoinList()
        detachIco()
        detachNavigation()
        detachConvert()
        detachSettings()
    }

    fun dispatchBackPress(): Boolean {
        return screenStack.handleBackPress()
    }

}
