package com.teamdecano.cryptocoin.coins.coindetails.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SocialStats {

    @SerializedName("Response")
    @Expose
    var response: String? = null
    @SerializedName("Message")
    @Expose
    var message: String? = null
    @SerializedName("Type")
    @Expose
    var type: Int = 0
    @SerializedName("Data")
    @Expose
    var data: Data? = null

}
