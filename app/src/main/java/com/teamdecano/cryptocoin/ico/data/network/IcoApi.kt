package com.teamdecano.cryptocoin.ico.data.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import com.teamdecano.cryptocoin.coins.coinlist.data.model.CoinDetails
import com.teamdecano.cryptocoin.coins.coinlist.data.model.CoinList
import com.teamdecano.cryptocoin.coins.coinlist.data.model.CoinListCmc
import com.teamdecano.cryptocoin.ico.data.model.IcoItem
import kotlinx.coroutines.experimental.Deferred
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


/**
 * Created by Ren Decano on 12/9/17.
 */
interface IcoApi {

    @GET("/api/v1/icos/active")
    fun getActiveIcoList(): Deferred<List<IcoItem>>

    @GET("/api/v1/icos/upcoming")
    fun getUpcomingIcoList(): Deferred<List<IcoItem>>

    companion object {

        fun create(okHttpClient: OkHttpClient): IcoApi {

            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(CoroutineCallAdapterFactory())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("https://chasing-coins.com/")
                    .client(okHttpClient)
                    .build()

            return retrofit.create(IcoApi::class.java)
        }
    }
}