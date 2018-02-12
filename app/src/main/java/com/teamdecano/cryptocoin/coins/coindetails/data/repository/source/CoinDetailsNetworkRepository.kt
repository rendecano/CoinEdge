package com.teamdecano.cryptocoin.coins.coindetails.data.repository.source

import com.teamdecano.cryptocoin.coins.coindetails.data.model.SocialStats
import com.teamdecano.cryptocoin.coins.coinlist.data.model.CoinDetails
import com.teamdecano.cryptocoin.coins.coinlist.data.network.CoinService

/**
 * Created by rendecano on 7/2/18.
 */
class CoinDetailsNetworkRepository(private val coinService: CoinService) {

    suspend fun getCoinDetails(coinId: String): CoinDetails {

        return coinService.getCoinDetails(coinId)
    }

    suspend fun getSocialStats(coinId: String): SocialStats {

        return coinService.getSocialStats(coinId)
    }
}