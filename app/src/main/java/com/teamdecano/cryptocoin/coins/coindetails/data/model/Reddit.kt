package com.teamdecano.cryptocoin.coins.coindetails.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.realm.RealmObject

open class Reddit: RealmObject() {

    @SerializedName("subscribers")
    @Expose
    var subscribers: Int = 0
    @SerializedName("active_users")
    @Expose
    var activeUsers: Int = 0
    @SerializedName("community_creation")
    @Expose
    var communityCreation: String? = null
    @SerializedName("posts_per_hour")
    @Expose
    var postsPerHour: String? = null
    @SerializedName("posts_per_day")
    @Expose
    var postsPerDay: String? = null
    @SerializedName("comments_per_hour")
    @Expose
    var commentsPerHour: String? = null
    @SerializedName("comments_per_day")
    @Expose
    var commentsPerDay: Double = 0.toDouble()
    @SerializedName("link")
    @Expose
    var link: String? = null
    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("Points")
    @Expose
    var points: Int = 0

}
