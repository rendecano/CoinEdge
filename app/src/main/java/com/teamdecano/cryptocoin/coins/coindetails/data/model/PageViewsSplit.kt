package com.teamdecano.cryptocoin.coins.coindetails.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PageViewsSplit {

    @SerializedName("Overview")
    @Expose
    var overview: Int = 0
    @SerializedName("Markets")
    @Expose
    var markets: Int = 0
    @SerializedName("Analysis")
    @Expose
    var analysis: Int = 0
    @SerializedName("Charts")
    @Expose
    var charts: Int = 0
    @SerializedName("Trades")
    @Expose
    var trades: Int = 0
    @SerializedName("Orderbook")
    @Expose
    var orderbook: Int = 0
    @SerializedName("Forum")
    @Expose
    var forum: Int = 0
    @SerializedName("Influence")
    @Expose
    var influence: Int = 0

}
