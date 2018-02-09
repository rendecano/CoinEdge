package com.teamdecano.cryptocoin.root

import android.content.Context
import android.support.annotation.NonNull
import android.support.v7.widget.Toolbar
import android.util.AttributeSet
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.teamdecano.cryptocoin.R
import com.teamdecano.cryptocoin.common.OptionsMenuService
import com.teamdecano.cryptocoin.common.screen_stack.Services
import kotlinx.android.synthetic.main.activity_root.view.*

/**
 * Top level view for [RootBuilder.RootScope].
 */
class RootView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : LinearLayout(context, attrs, defStyle), RootInteractor.RootPresenter {

    private var menuService: OptionsMenuService? = null

    private val menuListener = object : OptionsMenuService.Listener {
        override fun onPrepareOptionsMenu(menuInflater: MenuInflater, menu: Menu) {
            /* no-op */
        }

        override fun onOptionsItemSelected(item: MenuItem): Boolean {
            if (item.itemId == android.R.id.home) {
                return true
            }
            return false
        }
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        setupToolbar()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        menuService = Services.getOptionsMenuService(context)
        menuService?.addOptionsMenuListener(menuListener)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        menuService?.removeOptionsMenuListener(menuListener)
    }


    fun viewContent(): ViewGroup {
        return content
    }

    @NonNull
    fun viewNavigationLayout(): ViewGroup {
        return contentLayout
    }

    fun getToolbar(): Toolbar {
        return this.toolbar
    }

    fun getToolbarTitle(): TextView {
        return this.toolbar.findViewById(R.id.title)
    }

    private fun setupToolbar() {
        Services.setSupportActionBar(context, toolbar)
    }
}
