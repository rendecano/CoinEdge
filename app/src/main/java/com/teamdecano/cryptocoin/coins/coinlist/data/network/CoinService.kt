package com.teamdecano.cryptocoin.coins.coinlist.data.network

import com.teamdecano.cryptocoin.coins.coinlist.data.model.CoinDetails
import com.teamdecano.cryptocoin.coins.coinlist.data.model.CoinList
import com.teamdecano.cryptocoin.coins.coinlist.data.model.CoinListCmc
import okhttp3.OkHttpClient

/**
 * Created by Ren Decano on 12/9/17.
 */
class CoinService(val okHttpClient: OkHttpClient) {

    // TODO: baseURL should be a constant. API limitation :(
    suspend fun getCoinList(): CoinList {

        val coinApi = CoinApi.create("https://min-api.cryptocompare.com/", okHttpClient)
        return coinApi.getCoinList().await()
    }

    suspend fun getCoinListCmc(): List<CoinListCmc> {

        val coinApi = CoinApi.create("https://api.coinmarketcap.com/", okHttpClient)
        return coinApi.getCoinListCmc().await()
    }

    suspend fun getCoinDetails(coinId: String): CoinDetails {

        val coinApi = CoinApi.create("https://www.cryptocompare.com/", okHttpClient)
        return coinApi.getCoinDetails(coinId).await()
    }
}