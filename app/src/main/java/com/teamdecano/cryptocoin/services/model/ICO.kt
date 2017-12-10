package com.teamdecano.cryptocoin.services.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ICO {

    @SerializedName("Status")
    @Expose
    var status: String? = ""
    @SerializedName("Description")
    @Expose
    var description: String? = ""
    @SerializedName("TokenType")
    @Expose
    var tokenType: String? = ""
    @SerializedName("Website")
    @Expose
    var website: String? = ""
    @SerializedName("PublicPortfolioUrl")
    @Expose
    var publicPortfolioUrl: String? = ""
    @SerializedName("PublicPortfolioId")
    @Expose
    var publicPortfolioId: String? = ""
    @SerializedName("Features")
    @Expose
    var features: String? = ""
    @SerializedName("FundingTarget")
    @Expose
    var fundingTarget: String? = ""
    @SerializedName("FundingCap")
    @Expose
    var fundingCap: String? = ""
    @SerializedName("ICOTokenSupply")
    @Expose
    var icoTokenSupply: String? = ""
    @SerializedName("TokenSupplyPostICO")
    @Expose
    var tokenSupplyPostICO: String? = ""
    @SerializedName("TokenPercentageForInvestors")
    @Expose
    var tokenPercentageForInvestors: String? = ""
    @SerializedName("TokenReserveSplit")
    @Expose
    var tokenReserveSplit: String? = ""
    @SerializedName("Date")
    @Expose
    var date: Int = 0
    @SerializedName("EndDate")
    @Expose
    var endDate: Int = 0
    @SerializedName("FundsRaisedList")
    @Expose
    var fundsRaisedList: String? = ""
    @SerializedName("FundsRaisedUSD")
    @Expose
    var fundsRaisedUSD: String? = ""
    @SerializedName("StartPrice")
    @Expose
    var startPrice: String? = ""
    @SerializedName("StartPriceCurrency")
    @Expose
    var startPriceCurrency: String? = ""
    @SerializedName("PaymentMethod")
    @Expose
    var paymentMethod: String? = ""
    @SerializedName("Jurisdiction")
    @Expose
    var jurisdiction: String? = ""
    @SerializedName("LegalAdvisers")
    @Expose
    var legalAdvisers: String? = ""
    @SerializedName("LegalForm")
    @Expose
    var legalForm: String? = ""
    @SerializedName("SecurityAuditCompany")
    @Expose
    var securityAuditCompany: String? = ""
    @SerializedName("Blog")
    @Expose
    var blog: String? = ""
    @SerializedName("WhitePaper")
    @Expose
    var whitePaper: String? = ""
    @SerializedName("WhitePaperLink")
    @Expose
    var whitePaperLink: String? = ""

}
