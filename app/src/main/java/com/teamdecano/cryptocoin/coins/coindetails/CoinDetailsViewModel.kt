package com.teamdecano.cryptocoin.coins.coindetails

import com.teamdecano.cryptocoin.coins.coindetails.data.model.CodeList
import com.teamdecano.cryptocoin.coins.coindetails.data.model.Facebook
import com.teamdecano.cryptocoin.coins.coindetails.data.model.Reddit
import com.teamdecano.cryptocoin.coins.coindetails.data.model.Twitter
import io.realm.RealmObject

/**
 * Created by Ren Decano on 12/10/17.
 */
open class CoinDetailsViewModel(var coinId: String? = "",
                                var name: String? = "",
                                var symbol: String? = "",
                                var description: String? = "",
                                var technology: String? = "",
                                var features: String? = "",
                                var imageUrl: String? = "",
                                var totalCoinSupply: String? = "",
                                var startDate: String? = "",
                                var website: String? = "",
                                var sponsorImageUrl: String? = "",
                                var icoStatus: String? = "",
                                var icoDescription: String? = "",
                                var icoTokenSupply: String? = "",
                                var fundingTarget: String? = "",
                                var icoWhitePaperUrl: String? = "",
                                var twitterObj: Twitter? = null,
                                var reddit: Reddit? = null,
                                var facebook: Facebook? = null,
                                var codeList: CodeList? = null) : RealmObject()