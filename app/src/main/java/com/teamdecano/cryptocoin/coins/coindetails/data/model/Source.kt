package com.teamdecano.cryptocoin.coins.coindetails.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject


open class Source: RealmObject() {

    @SerializedName("Name")
    @Expose
    var name: String? = null
    @SerializedName("Url")
    @Expose
    var url: String? = null
    @SerializedName("InternalId")
    @Expose
    var internalId: Int = 0

}
