package com.teamdecano.cryptocoin.coins.coindetails.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class General {

    @SerializedName("Name")
    @Expose
    var name: String? = null
    @SerializedName("CoinName")
    @Expose
    var coinName: String? = null
    @SerializedName("Type")
    @Expose
    var type: String? = null
    @SerializedName("Points")
    @Expose
    var points: Int = 0

}
