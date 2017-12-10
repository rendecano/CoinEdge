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
        private val rootView: RootView,
        interactor: CoinInteractor,
        component: CoinBuilder.Component,
        private val coinListBuilder: CoinListBuilder) : Router<CoinInteractor, CoinBuilder.Component>(interactor, component) {

    private lateinit var coinListRouter: CoinListRouter

    fun routeToCoinList() {

        coinListRouter = coinListBuilder.build(rootView)
        attachChild(coinListRouter)
        rootView.addView(coinListRouter.view)
    }

    fun detachCoinList() {

    }

    override fun willDetach() {
        super.willDetach()
        detachCoinList()

    }

}
