package com.teamdecano.cryptocoin.common

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem

interface OptionsMenuService {
    fun addOptionsMenuListener(listener: Listener)
    fun removeOptionsMenuListener(listener: Listener)

    interface Listener {
        fun onPrepareOptionsMenu(menuInflater: MenuInflater, menu: Menu)
        fun onOptionsItemSelected(item: MenuItem): Boolean
    }
}
