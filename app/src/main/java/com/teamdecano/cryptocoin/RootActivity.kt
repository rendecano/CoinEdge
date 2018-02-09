package com.teamdecano.cryptocoin

import android.annotation.SuppressLint
import android.content.Context
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import com.teamdecano.cryptocoin.common.OptionsMenuService
import com.teamdecano.cryptocoin.common.OptionsMenuService.Listener
import com.teamdecano.cryptocoin.common.screen_stack.Services
import com.teamdecano.cryptocoin.common.screen_stack.Services.Companion.ACTION_BAR_SERVICE
import com.teamdecano.cryptocoin.common.screen_stack.Services.Companion.OPTIONS_MENU_SERVICE
import com.teamdecano.cryptocoin.root.RootBuilder
import com.uber.rib.core.RibActivity
import com.uber.rib.core.ViewRouter
import java.util.concurrent.CopyOnWriteArrayList

class RootActivity : RibActivity(), OptionsMenuService {

    private val TAG = javaClass.simpleName

    private val menuListeners = CopyOnWriteArrayList<Listener>()

    @SuppressLint("WrongConstant")
    operator fun get(context: Context): RootActivity {
        return context.getSystemService(TAG) as RootActivity
    }

    override fun createRouter(parentViewGroup: ViewGroup): ViewRouter<*, *, *> {

        // Get boxstore instance
        val boxStore = (application as CoinEdgeApplication).boxStore

        val rootBuilder = RootBuilder(object : RootBuilder.ParentComponent {})
        return rootBuilder.build(this, parentViewGroup, boxStore)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // if anybody configure the menu as UP, then just handle the tap thru the onBackPressed().
        if (item.itemId == android.R.id.home && Services.isMenuAsUp) {
            onBackPressed()
            return true
        }

        //...otherwise, just relay the event to the listeners in reverse order
        for (i in menuListeners.indices.reversed()) {
            val listener = menuListeners.get(i)
            val result = listener.onOptionsItemSelected(item)
            if (result) {
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun getSystemService(name: String): Any? {
        when (name) {
            ACTION_BAR_SERVICE -> return supportActionBar
            OPTIONS_MENU_SERVICE -> return this
            TAG -> return this
        }

        return super.getSystemService(name)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        for (listener in menuListeners) {
            listener.onPrepareOptionsMenu(menuInflater, menu)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun addOptionsMenuListener(listener: OptionsMenuService.Listener) {
        menuListeners.add(listener)
        invalidateOptionsMenu()
    }

    override fun removeOptionsMenuListener(listener: Listener) {
        menuListeners.remove(listener)
        invalidateOptionsMenu()
    }
}




