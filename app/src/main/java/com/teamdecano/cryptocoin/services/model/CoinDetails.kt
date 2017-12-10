package com.teamdecano.cryptocoin.services.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CoinDetails {

    @SerializedName("Response")
    @Expose
    var response: String = ""
    @SerializedName("Message")
    @Expose
    var message: String = ""
    @SerializedName("Data")
    @Expose
    var data: Data = Data()
    @SerializedName("Type")
    @Expose
    var type: Int = 0

}
