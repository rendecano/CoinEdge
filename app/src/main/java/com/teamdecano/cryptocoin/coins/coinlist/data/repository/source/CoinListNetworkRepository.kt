package com.teamdecano.cryptocoin.coins.coinlist.data.repository.source

import com.teamdecano.cryptocoin.coins.coinlist.data.model.CoinList
import com.teamdecano.cryptocoin.coins.coinlist.data.model.CoinListCmc
import com.teamdecano.cryptocoin.coins.coinlist.data.network.CoinService

/**
 * Created by rendecano on 7/2/18.
 */
class CoinListNetworkRepository(private val coinService: CoinService) {

    suspend fun getCoinListCcc(): CoinList {

        return coinService.getCoinList()
    }

    suspend fun getCoinListCoinMarketCap(): List<CoinListCmc> {

        return coinService.getCoinListCmc()
    }
}