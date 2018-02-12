package com.teamdecano.cryptocoin.coins.coindetails.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CryptopianFollower {

    @SerializedName("Id")
    @Expose
    var id: Int = 0
    @SerializedName("Name")
    @Expose
    var name: String? = null
    @SerializedName("ImageUrl")
    @Expose
    var imageUrl: String? = null
    @SerializedName("Url")
    @Expose
    var url: String? = null
    @SerializedName("Type")
    @Expose
    var type: String? = null

}
