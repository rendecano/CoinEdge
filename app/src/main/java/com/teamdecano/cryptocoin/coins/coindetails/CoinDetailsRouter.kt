package com.teamdecano.cryptocoin.coins.coindetails

import android.view.View

import com.uber.rib.core.ViewRouter

/**
 * Adds and removes children of [CoinDetailsBuilder.CoinDetailsScope].
 *
 * TODO describe the possible child configurations of this scope.
 */
class CoinDetailsRouter(
        view: CoinDetailsView,
        interactor: CoinDetailsInteractor,
        component: CoinDetailsBuilder.Component) : ViewRouter<CoinDetailsView, CoinDetailsInteractor, CoinDetailsBuilder.Component>(view, interactor, component)
