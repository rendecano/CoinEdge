package com.teamdecano.cryptocoin.services

import com.teamdecano.cryptocoin.services.model.Coin
import com.teamdecano.cryptocoin.services.model.CoinDetails
import com.teamdecano.cryptocoin.services.model.CoinList
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


/**
 * Created by Ren Decano on 12/9/17.
 */
interface CoinApi {

    @GET("/data/all/coinlist")
    fun getCoinList(): Call<CoinList>

    @GET("/api/data/coinsnapshotfullbyid")
    fun getCoinDetails(@Query("id") id: String): Call<CoinDetails>

    @GET("/data/price")
    fun getCoinPrice(@Query("fsym") fromSymbol: String, @Query("tsym") toSymbol: String): Call<Map<String, String>>

    companion object {

        fun create(baseUrl: String): CoinApi {

            val retrofit = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(baseUrl)
                    .build()

            return retrofit.create(CoinApi::class.java)
        }
    }
}