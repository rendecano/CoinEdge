package com.teamdecano.cryptocoin.coins.coindetails.data.repository.source

import com.teamdecano.cryptocoin.coins.coindetails.CoinDetailsViewModel
import com.teamdecano.cryptocoin.coins.coindetails.data.model.CodeList
import com.teamdecano.cryptocoin.coins.coindetails.data.model.SocialStats
import com.teamdecano.cryptocoin.coins.coinlist.data.model.CoinDetails
import io.realm.Realm
import io.realm.kotlin.where

/**
 * Created by rendecano on 7/2/18.
 */
class CoinDetailsLocalRepository(val realm: Realm) {


    suspend fun getCoinDetails(coinId: String?): CoinDetailsViewModel? {
        return realm.where<CoinDetailsViewModel>().equalTo("coinId", coinId).findFirst()
    }

    suspend fun updateList(coinDetails: CoinDetails, socialStats: SocialStats) {

        realm.executeTransaction {

            realm.where<CoinDetailsViewModel>().findAll().deleteAllFromRealm()

            val baseUrl = coinDetails.data.seo.baseImageUrl
            val general = coinDetails.data.general
            val ico = coinDetails.data.ico
            val socialStatsRepo = socialStats.data?.codeRepository

            val coinDetails = CoinDetailsViewModel(
                    general.id,
                    general.name,
                    general.symbol,
                    general.description,
                    general.technology,
                    general.features,
                    baseUrl + general.imageUrl,
                    general.totalCoinSupply,
                    general.startDate,
                    if (!general.affiliateUrl.isNullOrEmpty() && general.affiliateUrl!!.contains("http")) general.affiliateUrl else "",
                    baseUrl + general.sponsor?.imageUrl,
                    ico.status,
                    ico.description,
                    ico.icoTokenSupply,
                    ico.fundingTarget,
                    ico.whitePaperLink,
                    socialStats.data?.twitter,
                    socialStats.data?.reddit,
                    socialStats.data?.facebook,
                    if (!socialStatsRepo?.codeList.orEmpty().isEmpty()) socialStatsRepo?.codeList?.get(0) else CodeList()
            )


            realm.insert(coinDetails)
        }
    }
}