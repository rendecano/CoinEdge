package com.teamdecano.cryptocoin.coins.coinlist.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class General {

    @SerializedName("Id")
    @Expose
    var id: String? = ""
    @SerializedName("DocumentType")
    @Expose
    var documentType: String? = ""
    @SerializedName("H1Text")
    @Expose
    var h1Text: String? = ""
    @SerializedName("DangerTop")
    @Expose
    var dangerTop: String? = ""
    @SerializedName("WarningTop")
    @Expose
    var warningTop: String? = ""
    @SerializedName("InfoTop")
    @Expose
    var infoTop: String? = ""
    @SerializedName("Symbol")
    @Expose
    var symbol: String? = ""
    @SerializedName("Url")
    @Expose
    var url: String? = ""
    @SerializedName("BaseAngularUrl")
    @Expose
    var baseAngularUrl: String? = ""
    @SerializedName("Name")
    @Expose
    var name: String? = ""
    @SerializedName("ImageUrl")
    @Expose
    var imageUrl: String? = ""
    @SerializedName("Description")
    @Expose
    var description: String? = ""
    @SerializedName("Features")
    @Expose
    var features: String? = ""
    @SerializedName("Technology")
    @Expose
    var technology: String? = ""
    @SerializedName("TotalCoinSupply")
    @Expose
    var totalCoinSupply: String? = ""
    @SerializedName("Algorithm")
    @Expose
    var algorithm: String? = ""
    @SerializedName("ProofType")
    @Expose
    var proofType: String? = ""
    @SerializedName("StartDate")
    @Expose
    var startDate: String? = ""
    @SerializedName("Twitter")
    @Expose
    var twitter: String? = ""
    @SerializedName("AffiliateUrl")
    @Expose
    var affiliateUrl: String? = ""
    @SerializedName("Website")
    @Expose
    var website: String? = ""
    @SerializedName("Sponsor")
    @Expose
    var sponsor: Sponsor? = null
    @SerializedName("LastBlockExplorerUpdateTS")
    @Expose
    var lastBlockExplorerUpdateTS: String? = ""
    @SerializedName("DifficultyAdjustment")
    @Expose
    var difficultyAdjustment: Any? = null
    @SerializedName("BlockRewardReduction")
    @Expose
    var blockRewardReduction: Any? = null
    @SerializedName("BlockNumber")
    @Expose
    var blockNumber: String? = ""
    @SerializedName("BlockTime")
    @Expose
    var blockTime: String? = ""
    @SerializedName("NetHashesPerSecond")
    @Expose
    var netHashesPerSecond: String? = ""
    @SerializedName("TotalCoinsMined")
    @Expose
    var totalCoinsMined: String? = ""
    @SerializedName("PreviousTotalCoinsMined")
    @Expose
    var previousTotalCoinsMined: String? = ""
    @SerializedName("BlockReward")
    @Expose
    var blockReward: String? = ""

}
