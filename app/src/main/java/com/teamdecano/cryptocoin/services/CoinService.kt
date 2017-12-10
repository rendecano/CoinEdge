package com.teamdecano.cryptocoin.services

import com.teamdecano.cryptocoin.services.model.CoinDetails
import com.teamdecano.cryptocoin.services.model.CoinList
import retrofit2.Call

/**
 * Created by Ren Decano on 12/9/17.
 */
class CoinService {

    // TODO: baseURL should be a constant. API limitation :(
    fun getCoinList(): Call<CoinList> {

        val coinApi = CoinApi.create("https://min-api.cryptocompare.com/")
        return coinApi.getCoinList()
    }

    fun getCoinDetails(coinId: String): Call<CoinDetails> {

        val coinApi = CoinApi.create("https://www.cryptocompare.com/")
        return coinApi.getCoinDetails(coinId)
    }

    fun getCoinPrice(fromSymbol: String, toSymbol: String): Call<Map<String, String>> {

        val coinApi = CoinApi.create("https://min-api.cryptocompare.com/")
        return coinApi.getCoinPrice(fromSymbol, toSymbol)
    }
}