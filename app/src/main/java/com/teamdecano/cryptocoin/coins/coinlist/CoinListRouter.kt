package com.teamdecano.cryptocoin.coins.coinlist

import android.content.Intent
import android.view.View
import com.teamdecano.cryptocoin.coins.coindetails.CoinDetailsActivity

import com.uber.rib.core.ViewRouter

/**
 * Adds and removes children of [CoinListBuilder.CoinListScope].
 *
 * TODO describe the possible child configurations of this scope.
 */
class CoinListRouter(
        view: CoinListView,
        interactor: CoinListInteractor,
        component: CoinListBuilder.Component) : ViewRouter<CoinListView, CoinListInteractor, CoinListBuilder.Component>(view, interactor, component) {

    fun routeToCoinDetails(coinListViewModel: CoinListViewModel) {

        val intent = Intent(view.context, CoinDetailsActivity::class.java)
        intent.putExtra("coinId", coinListViewModel.id)
        view.context.startActivity(intent)

    }
}
