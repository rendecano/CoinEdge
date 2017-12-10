package com.teamdecano.cryptocoin.services.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SEO {

    @SerializedName("PageTitle")
    @Expose
    var pageTitle: String? = ""
    @SerializedName("PageDescription")
    @Expose
    var pageDescription: String? = ""
    @SerializedName("BaseUrl")
    @Expose
    var baseUrl: String? = ""
    @SerializedName("BaseImageUrl")
    @Expose
    var baseImageUrl: String? = ""
    @SerializedName("OgImageUrl")
    @Expose
    var ogImageUrl: String? = ""
    @SerializedName("OgImageWidth")
    @Expose
    var ogImageWidth: String? = ""
    @SerializedName("OgImageHeight")
    @Expose
    var ogImageHeight: String? = ""

}
