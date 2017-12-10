package com.teamdecano.cryptocoin.services.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


/**
 * Created by Ren Decano on 12/9/17.
 */
class Coin {

    @SerializedName("Id")
    @Expose
    var id: String? = ""
    @SerializedName("Url")
    @Expose
    var url: String? = ""
    @SerializedName("ImageUrl")
    @Expose
    var imageUrl: String? = ""
    @SerializedName("Name")
    @Expose
    var name: String? = ""
    @SerializedName("CoinName")
    @Expose
    var coinName: String? = ""
    @SerializedName("FullName")
    @Expose
    var fullName: String? = ""
    @SerializedName("Algorithm")
    @Expose
    var algorithm: String? = ""
    @SerializedName("ProofType")
    @Expose
    var proofType: String? = ""
    @SerializedName("SortOrder")
    @Expose
    var sortOrder: String? = ""
}