package com.teamdecano.cryptocoin.coins.coindetails.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Data {

    @SerializedName("General")
    @Expose
    var general: General? = null
    @SerializedName("CryptoCompare")
    @Expose
    var cryptoCompare: CryptoCompare? = null
    @SerializedName("Twitter")
    @Expose
    var twitter: Twitter? = null
    @SerializedName("Reddit")
    @Expose
    var reddit: Reddit? = null
    @SerializedName("Facebook")
    @Expose
    var facebook: Facebook? = null
    @SerializedName("CodeRepository")
    @Expose
    var codeRepository: CodeRepository? = null

}
