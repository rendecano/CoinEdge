package com.teamdecano.cryptocoin.coins.coindetails.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject

open class Twitter: RealmObject() {

    @SerializedName("followers")
    @Expose
    var followers: Int = 0
    @SerializedName("following")
    @Expose
    var following: String? = null
    @SerializedName("lists")
    @Expose
    var lists: Int = 0
    @SerializedName("favourites")
    @Expose
    var favourites: String? = null
    @SerializedName("statuses")
    @Expose
    var statuses: Int = 0
    @SerializedName("account_creation")
    @Expose
    var accountCreation: String? = null
    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("link")
    @Expose
    var link: String? = null
    @SerializedName("Points")
    @Expose
    var points: Int = 0

}
