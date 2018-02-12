package com.teamdecano.cryptocoin.coins.coindetails.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CodeRepository {

    @SerializedName("List")
    @Expose
    var codeList: List<CodeList>? = null
    @SerializedName("Points")
    @Expose
    var points: Int = 0

}
