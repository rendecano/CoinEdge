package com.teamdecano.cryptocoin.coins.coindetails

import android.view.ViewGroup
import com.uber.rib.core.RibActivity
import com.uber.rib.core.ViewRouter

/**
 * Created by Ren Decano on 12/10/17.
 */
class CoinDetailsActivity : RibActivity(), CoinDetailsInteractor.Listener {

    override fun createRouter(parentViewGroup: ViewGroup): ViewRouter<*, *, *> {

        val coinId = intent.getStringExtra("coinId")
        val coinDetailsBuilder = CoinDetailsBuilder(object : CoinDetailsBuilder.ParentComponent {})
        return coinDetailsBuilder.build(parentViewGroup, coinId, this)
    }
}