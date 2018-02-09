package com.teamdecano.cryptocoin.coins.coinlist.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


/**
 * Created by Ren Decano on 12/9/17.
 */
class CoinList {

    @SerializedName("Response")
    @Expose
    var response: String? = ""
    @SerializedName("Message")
    @Expose
    var message: String? = ""
    @SerializedName("BaseImageUrl")
    @Expose
    var baseImageUrl: String? = ""
    @SerializedName("BaseLinkUrl")
    @Expose
    var baseLinkUrl: String? = ""
    @SerializedName("Data")
    @Expose
    val feeds: Map<String, Coin>? = null
    @SerializedName("Type")
    @Expose
    var type: Int = 0

}