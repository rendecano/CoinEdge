package com.teamdecano.cryptocoin.coins.coinlist.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Sponsor {

    @SerializedName("TextTop")
    @Expose
    var textTop: String? = ""
    @SerializedName("Link")
    @Expose
    var link: String? = null
    @SerializedName("ImageUrl")
    @Expose
    var imageUrl: String? = ""

}
