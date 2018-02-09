package com.teamdecano.cryptocoin.coins.coinlist.presentation

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

/**
 * Created by Ren Decano on 12/9/17.
 */
data class CoinListModel(var id: String?,
                         val imageUrl: String?,
                         val name: String?,
                         val coinName: String?,
                         val percentage: String?,
                         val price: String?,
                         val volume: String?,
                         val sortOrder: String?)