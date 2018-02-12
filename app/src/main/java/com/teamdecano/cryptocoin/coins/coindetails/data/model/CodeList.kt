package com.teamdecano.cryptocoin.coins.coindetails.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject

open class CodeList: RealmObject() {

    @SerializedName("stars")
    @Expose
    var stars: Int = 0
    @SerializedName("language")
    @Expose
    var language: String? = null
    @SerializedName("forks")
    @Expose
    var forks: Int = 0
    @SerializedName("open_total_issues")
    @Expose
    var openTotalIssues: String? = null
    @SerializedName("subscribers")
    @Expose
    var subscribers: Int = 0
    @SerializedName("size")
    @Expose
    var size: String? = null
    @SerializedName("url")
    @Expose
    var url: String? = null
    @SerializedName("last_update")
    @Expose
    var lastUpdate: String? = null
    @SerializedName("last_push")
    @Expose
    var lastPush: String? = null
    @SerializedName("created_at")
    @Expose
    var createdAt: String? = null
    @SerializedName("fork")
    @Expose
    var fork: String? = null
    @SerializedName("source")
    @Expose
    var source: Source? = null
    @SerializedName("parent")
    @Expose
    var parent: Parent? = null
    @SerializedName("open_pull_issues")
    @Expose
    var openPullIssues: String? = null
    @SerializedName("closed_pull_issues")
    @Expose
    var closedPullIssues: String? = null
    @SerializedName("closed_total_issues")
    @Expose
    var closedTotalIssues: String? = null
    @SerializedName("open_issues")
    @Expose
    var openIssues: String? = null
    @SerializedName("closed_issues")
    @Expose
    var closedIssues: String? = null

}
