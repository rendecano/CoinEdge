package com.teamdecano.cryptocoin.ico.data.model

import com.google.gson.annotations.SerializedName
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

/**
 * Created by rendecano on 8/2/18.
 */
@Entity
data class IcoItem(@Id var id: Long,
                   @SerializedName("name") var name: String? = "",
                   @SerializedName("image") var image: String? = "",
                   @SerializedName("website") var website: String? = "",
                   @SerializedName("url") var url: String? = "",
                   @SerializedName("start_time") var startTime: String? = "",
                   @SerializedName("end_time") var endTime: String? = "",
                   @SerializedName("description") var description: String? = "")