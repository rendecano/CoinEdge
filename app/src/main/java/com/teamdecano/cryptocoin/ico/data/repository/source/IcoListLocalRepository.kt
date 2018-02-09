package com.teamdecano.cryptocoin.ico.data.repository.source


import com.teamdecano.cryptocoin.ico.data.model.IcoItem
import com.teamdecano.cryptocoin.ico.data.model.IcoItem_
import io.objectbox.Box
import io.objectbox.BoxStore
import io.objectbox.kotlin.boxFor
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async

/**
 * Created by rendecano on 9/2/18.
 */
class IcoListLocalRepository(boxStore: BoxStore) {

    private var iCoListCccBox: Box<IcoItem> = boxStore.boxFor()

    fun getList(): Deferred<List<List<IcoItem>>> {

        return async(CommonPool) {

            val upcomingList = iCoListCccBox.query().equal(IcoItem_.type, 1).build().find()
            val activeList = iCoListCccBox.query().equal(IcoItem_.type, 0).build().find()

            listOf<List<IcoItem>>(activeList, upcomingList)
        }
    }

    fun updateList(activeList: List<IcoItem>, upcomingList: List<IcoItem>) {

        async(CommonPool) {

            iCoListCccBox.removeAll()

            activeList.forEach { active -> active.type = 0 }
            upcomingList.forEach { upcoming -> upcoming.type = 1 }

            iCoListCccBox.put(activeList)
            iCoListCccBox.put(upcomingList)
        }
    }
}