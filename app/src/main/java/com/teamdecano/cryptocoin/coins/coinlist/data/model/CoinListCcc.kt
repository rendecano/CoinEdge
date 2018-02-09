package com.teamdecano.cryptocoin.coins.coinlist.data.model

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

/**
 * Created by rendecano on 7/2/18.
 */
@Entity
data class CoinListCcc(@Id(assignable = true) var id: Long = 0,
                       var coinId: String?,
                       val coinSymbol: String?)