package com.teamdecano.cryptocoin.coins.coinlist.data.repository.source

import com.teamdecano.cryptocoin.coins.coinlist.data.model.*
import com.teamdecano.cryptocoin.coins.coinlist.presentation.CoinListModel
import io.objectbox.Box
import io.objectbox.BoxStore
import io.objectbox.kotlin.boxFor
import io.objectbox.query.Query
import io.objectbox.query.QueryBuilder
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async

/**
 * Created by rendecano on 7/2/18.
 */
class CoinListLocalRepository(boxStore: BoxStore) {

    private var coinListCccBox: Box<CoinListCcc> = boxStore.boxFor<CoinListCcc>()
    private var coinListCmcBox: Box<CoinListCmc> = boxStore.boxFor<CoinListCmc>()

    fun getList(): Deferred<List<CoinListModel>>{

        return async(CommonPool) {

            // Get all coins from Cmc
            var coinListCmcQuery = coinListCmcBox.query().sort { a, b ->
                when {
                    a.rank!!.toInt() != b.rank!!.toInt() -> a.rank!!.toInt() - b.rank!!.toInt()
                    else -> a.rank!!.toInt() - b.rank!!.toInt()
                }
            }.build()


            val coinsCmc = coinListCmcQuery.find()
            var coinList = ArrayList<CoinListModel>()
            val baseUrl = "https://chasing-coins.com/api/v1/std/logo/"

            for (coin in coinsCmc) {

                val id = coinListCccBox.query().equal(CoinListCcc_.coinSymbol, coin.symbol).build().findFirst()?.coinId

                coinList.add(CoinListModel(id,
                        baseUrl + coin.symbol,
                        coin.symbol,
                        coin.name,
                        coin.percentChange24h,
                        coin.priceUsd,
                        coin._24hVolumeUsd,
                        coin.rank))
            }

            coinList
        }
    }

    fun updateList(coinListObjectCmc: List<CoinListCmc>, coinListObject: CoinList) {

        async(CommonPool) {

            // Save to coinListObjectCmc to DB
            // Delete all
            coinListCmcBox.removeAll()

            // Insert all
            coinListCmcBox.put(coinListObjectCmc)

            // Save to coinListObject to DB
            val coinCccList = ArrayList<CoinListCcc>()
            coinListObject.feeds!!.values!!.mapTo(coinCccList) {
                CoinListCcc(coinId = it.id,
                        coinSymbol = it.name)
            }

            // Delete all
            coinListCccBox.removeAll()

            // Insert all
            coinListCccBox.put(coinCccList)
        }
    }
}