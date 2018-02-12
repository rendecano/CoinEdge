package com.teamdecano.cryptocoin.coins.coindetails.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SimilarItem {

    @SerializedName("Id")
    @Expose
    var id: Int = 0
    @SerializedName("Name")
    @Expose
    var name: String? = null
    @SerializedName("FullName")
    @Expose
    var fullName: String? = null
    @SerializedName("ImageUrl")
    @Expose
    var imageUrl: String? = null
    @SerializedName("Url")
    @Expose
    var url: String? = null
    @SerializedName("FollowingType")
    @Expose
    var followingType: Int = 0

}
