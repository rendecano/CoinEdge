package com.teamdecano.cryptocoin.coins.coinlist.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Data {

    @SerializedName("SEO")
    @Expose
    var seo: SEO = SEO()
    @SerializedName("General")
    @Expose
    var general: General = General()
    @SerializedName("ICO")
    @Expose
    var ico: ICO = ICO()
    @SerializedName("Subs")
    @Expose
    var subs: List<String> = ArrayList<String>()
    @SerializedName("StreamerDataRaw")
    @Expose
    var streamerDataRaw: List<String> = ArrayList<String>()

}
