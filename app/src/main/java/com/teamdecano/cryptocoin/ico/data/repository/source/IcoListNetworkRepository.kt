package com.teamdecano.cryptocoin.ico.data.repository.source

import com.teamdecano.cryptocoin.coins.coinlist.data.network.IcoService
import com.teamdecano.cryptocoin.ico.data.model.IcoItem

/**
 * Created by rendecano on 7/2/18.
 */
class IcoListNetworkRepository(private val icoService: IcoService) {

    suspend fun getActiveIcoList(): List<IcoItem> {

        return icoService.getActiveIcoList()
    }

    suspend fun getUpcomingIcoList(): List<IcoItem> {

        return icoService.getUpcomingIcoList()
    }
}