package com.teamdecano.cryptocoin.coins

import com.teamdecano.cryptocoin.coins.coinlist.CoinListBuilder
import com.teamdecano.cryptocoin.coins.coinlist.CoinListRouter
import com.teamdecano.cryptocoin.root.RootView
import com.uber.rib.core.Router

/**
 * Adds and removes children of [CoinBuilder.CoinScope].
 *
 * TODO describe the possible child configurations of this scope.
 */
class CoinRouter(
        val rootView: RootView,
        interactor: CoinInteractor,
        component: CoinBuilder.Component,
        val coinListBuilder: CoinListBuilder) : Router<CoinInteractor, CoinBuilder.Component>(interactor, component) {

    private var coinListRouter: CoinListRouter? = null

    fun routeToCoinList() {

        coinListRouter = coinListBuilder.build(rootView.viewContent())
        attachChild(coinListRouter)
        rootView.viewContent().addView(coinListRouter?.view)
    }

    fun detachCoinList() {

        if (coinListRouter != null) {
            detachChild(coinListRouter)
            rootView.viewContent().removeView(coinListRouter?.view)
            coinListRouter = null
        }
    }

    override fun willDetach() {
        super.willDetach()
        detachCoinList()
    }
}
