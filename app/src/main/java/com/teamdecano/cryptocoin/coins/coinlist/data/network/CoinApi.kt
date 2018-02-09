package com.teamdecano.cryptocoin.coins.coinlist.data.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import com.teamdecano.cryptocoin.coins.coinlist.data.model.CoinDetails
import com.teamdecano.cryptocoin.coins.coinlist.data.model.CoinList
import com.teamdecano.cryptocoin.coins.coinlist.data.model.CoinListCmc
import kotlinx.coroutines.experimental.Deferred
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
    fun getCoinList(): Deferred<CoinList>

    @GET("/v1/ticker/?limit=0")
    fun getCoinListCmc(): Deferred<List<CoinListCmc>>

    @GET("/api/data/coinsnapshotfullbyid")
    fun getCoinDetails(@Query("id") id: String): Deferred<CoinDetails>

    @GET("/data/price")
    fun getCoinPrice(@Query("fsym") fromSymbol: String, @Query("tsym") toSymbol: String): Call<Map<String, String>>

    companion object {

        fun create(baseUrl: String): CoinApi {

            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(CoroutineCallAdapterFactory())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(baseUrl)
                    .build()

            return retrofit.create(CoinApi::class.java)
        }
    }
}