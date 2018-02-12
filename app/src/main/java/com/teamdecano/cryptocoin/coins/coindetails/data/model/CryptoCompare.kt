package com.teamdecano.cryptocoin.coins.coindetails.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CryptoCompare {

    @SerializedName("SimilarItems")
    @Expose
    var similarItems: List<SimilarItem>? = null
    @SerializedName("CryptopianFollowers")
    @Expose
    var cryptopianFollowers: List<CryptopianFollower>? = null
    @SerializedName("Followers")
    @Expose
    var followers: Int = 0
    @SerializedName("Points")
    @Expose
    var points: Int = 0
    @SerializedName("Posts")
    @Expose
    var posts: String? = null
    @SerializedName("Comments")
    @Expose
    var comments: String? = null
    @SerializedName("PageViewsSplit")
    @Expose
    var pageViewsSplit: PageViewsSplit? = null
    @SerializedName("PageViews")
    @Expose
    var pageViews: Int = 0

}
