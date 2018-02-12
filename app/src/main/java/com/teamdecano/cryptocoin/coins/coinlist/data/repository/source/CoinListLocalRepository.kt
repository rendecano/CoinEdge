package com.teamdecano.cryptocoin.coins.coinlist.data.repository.source

import com.teamdecano.cryptocoin.coins.coinlist.data.model.CoinList
import com.teamdecano.cryptocoin.coins.coinlist.data.model.CoinListCcc
import com.teamdecano.cryptocoin.coins.coinlist.data.model.CoinListCcc_
import com.teamdecano.cryptocoin.coins.coinlist.data.model.CoinListCmc
import com.teamdecano.cryptocoin.coins.coinlist.presentation.CoinListModel
import io.objectbox.Box
import io.objectbox.BoxStore
import io.objectbox.kotlin.boxFor

/**
 * Created by rendecano on 7/2/18.
 */
class CoinListLocalRepository(boxStore: BoxStore) {

    private val BASE_IMAGE_URL = "https://chasing-coins.com/api/v1/std/logo/"

    private var coinListCccBox: Box<CoinListCcc> = boxStore.boxFor()
    private var coinListCmcBox: Box<CoinListCmc> = boxStore.boxFor()

    suspend fun getList(): List<CoinListModel> {

        // Get all coins from Cmc
        val coinListCmcQuery = coinListCmcBox.query().sort { a, b ->
            when {
                a.rank!!.toInt() != b.rank!!.toInt() -> a.rank!!.toInt() - b.rank!!.toInt()
                else -> a.rank!!.toInt() - b.rank!!.toInt()
            }
        }.build()

        val coinsCmc = coinListCmcQuery.find()
        val coinList = ArrayList<CoinListModel>()

        coinsCmc.forEach { coin ->

            coinList.add(CoinListModel("",
                    BASE_IMAGE_URL + coin.symbol,
                    coin.symbol,
                    coin.name,
                    coin.percentChange24h,
                    coin.priceUsd,
                    coin._24hVolumeUsd,
                    coin.rank))
        }

        return coinList
    }

    suspend fun updateList(coinListObjectCmc: List<CoinListCmc>, coinListObject: CoinList) {

        // Save to coinListObjectCmc to DB
        // Delete all
        coinListCmcBox.removeAll()

        // Insert all
        coinListCmcBox.put(coinListObjectCmc)

        // Save to coinListObject to DB
        val coinCccList = ArrayList<CoinListCcc>()
        coinListObject.feeds!!.values.mapTo(coinCccList) {
            CoinListCcc(coinId = it.id,
                    coinSymbol = it.name)
        }

        // Delete all
        coinListCccBox.removeAll()

        // Insert all
        coinListCccBox.put(coinCccList)

    }

    fun getId(symbol: String): String? {
        return coinListCccBox.query().equal(CoinListCcc_.coinSymbol, symbol).build().findFirst()?.coinId
    }
}