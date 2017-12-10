package com.teamdecano.cryptocoin.root

import com.teamdecano.cryptocoin.coins.CoinBuilder
import com.teamdecano.cryptocoin.coins.CoinRouter

import com.uber.rib.core.ViewRouter

/**
 * Adds and removes children of [RootBuilder.RootScope].
 *
 * TODO describe the possible child configurations of this scope.
 */
class RootRouter(
        view: RootView,
        interactor: RootInteractor,
        component: RootBuilder.Component,
        private val coinBuilder: CoinBuilder) : ViewRouter<RootView, RootInteractor, RootBuilder.Component>(view, interactor, component) {

    private lateinit var coinRouter: CoinRouter

    fun routeToCoin() {

        coinRouter = coinBuilder.build()
        attachChild(coinRouter)
    }

    fun detachCoin() {

        if (coinRouter != null) {
            detachChild(coinRouter)
        }

    }

    override fun willDetach() {
        super.willDetach()
        detachCoin()
    }

}
