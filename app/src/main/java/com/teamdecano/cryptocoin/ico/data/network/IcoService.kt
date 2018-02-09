package com.teamdecano.cryptocoin.coins.coinlist.data.network

import com.teamdecano.cryptocoin.ico.data.model.IcoItem
import com.teamdecano.cryptocoin.ico.data.network.IcoApi
import okhttp3.OkHttpClient

/**
 * Created by Ren Decano on 12/9/17.
 */
class IcoService(okHttpClient: OkHttpClient) {
    
    private val icoApi = IcoApi.create(okHttpClient)

    suspend fun getUpcomingIcoList(): List<IcoItem> {

        return icoApi.getUpcomingIcoList().await()
    }

    suspend fun getActiveIcoList(): List<IcoItem> {

        return icoApi.getActiveIcoList().await()
    }

}