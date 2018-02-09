package com.teamdecano.cryptocoin

import android.app.Application
import io.objectbox.BoxStore

/**
 * Created by rendecano on 7/2/18.
 */
class CoinEdgeApplication : Application() {

    lateinit var boxStore: BoxStore
        private set

    override fun onCreate() {
        super.onCreate()
        boxStore = MyObjectBox.builder().androidContext(applicationContext).build()
    }
}