package com.teamdecano.cryptocoin.coins

import com.uber.rib.core.Bundle
import com.uber.rib.core.EmptyPresenter
import com.uber.rib.core.Interactor
import com.uber.rib.core.RibInteractor

/**
 * Coordinates Business Logic for [CoinScope].
 *
 * TODO describe the logic of this scope.
 */
@RibInteractor
class CoinInteractor : Interactor<EmptyPresenter, CoinRouter>() {

    override fun didBecomeActive(savedInstanceState: Bundle?) {
        super.didBecomeActive(savedInstanceState)

        router.routeToCoinList()
    }

    override fun willResignActive() {
        super.willResignActive()

        router.detachCoinList()
    }
}
