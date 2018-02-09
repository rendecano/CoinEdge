package com.teamdecano.cryptocoin.coins.coinlist.data.network

import com.teamdecano.cryptocoin.coins.coinlist.data.model.CoinDetails
import com.teamdecano.cryptocoin.coins.coinlist.data.model.CoinList
import com.teamdecano.cryptocoin.coins.coinlist.data.model.CoinListCmc
import retrofit2.Call

/**
 * Created by Ren Decano on 12/9/17.
 */
class CoinService {

    // TODO: baseURL should be a constant. API limitation :(
    suspend fun getCoinList(): CoinList {

        val coinApi = CoinApi.create("https://min-api.cryptocompare.com/")
        return coinApi.getCoinList().await()
    }

    suspend fun getCoinListCmc(): List<CoinListCmc> {

        val coinApi = CoinApi.create("https://api.coinmarketcap.com/")
        return coinApi.getCoinListCmc().await()
    }

    suspend fun getCoinDetails(coinId: String): CoinDetails {

        val coinApi = CoinApi.create("https://www.cryptocompare.com/")
        return coinApi.getCoinDetails(coinId).await()
    }

    fun getCoinPrice(fromSymbol: String, toSymbol: String): Call<Map<String, String>> {

        val coinApi = CoinApi.create("https://min-api.cryptocompare.com/")
        return coinApi.getCoinPrice(fromSymbol, toSymbol)
    }
}