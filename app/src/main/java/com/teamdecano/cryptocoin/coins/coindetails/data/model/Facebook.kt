package com.teamdecano.cryptocoin.coins.coindetails.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject

open class Facebook: RealmObject() {

    @SerializedName("likes")
    @Expose
    var likes: Int = 0
    @SerializedName("is_closed")
    @Expose
    var isClosed: String? = null
    @SerializedName("talking_about")
    @Expose
    var talkingAbout: String? = null
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
