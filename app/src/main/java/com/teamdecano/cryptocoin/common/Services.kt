package com.teamdecano.cryptocoin.common.screen_stack

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.app.ActionBar
import android.support.v7.widget.Toolbar
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.teamdecano.cryptocoin.common.OptionsMenuService

import com.uber.rib.core.RibActivity

import java.util.concurrent.atomic.AtomicBoolean

class Services {

    companion object {

        val ACTION_BAR_SERVICE = "com.teamdecano.cryptocoin.action_bar"
        val OPTIONS_MENU_SERVICE = "com.teamdecano.cryptocoin.options_menu"

        private val menuAsUp = AtomicBoolean(false)

        /**
         * Get an instance of the [ActionBar]
         * @param context current caller [Context]
         * @return instance of the app [ActionBar]
         */
        @SuppressLint("WrongConstant")
        fun getSupportActionBar(context: Context): ActionBar? {
            return checkNotNull(context).getSystemService(ACTION_BAR_SERVICE) as ActionBar
        }

        /**
         * Set support [ActionBar]
         * @param context current caller [Context]
         * @param toolbar [Toolbar] instance to be set as [ActionBar]
         */
        fun setSupportActionBar(context: Context, toolbar: Toolbar) {
            (context as RibActivity).setSupportActionBar(toolbar)
        }

        /**
         * @return returns `true` if the menu is configure as UP button
         */
        val isMenuAsUp: Boolean
            get() = menuAsUp.get()

        fun getOptionsMenuService(context: Context): OptionsMenuService {
            return checkNotNull(context).getSystemService(OPTIONS_MENU_SERVICE) as OptionsMenuService
        }

        /**
         * Hides the soft keyboard
         * @param context caller [Context]
         * @param view caller [View]
         */
        @Synchronized
        fun hideSoftKeyboard(context: Context, view: View) {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }

        /**
         * Shows the soft keyboard
         * @param context caller [Context]
         * @param focusedView the currently focused [View]
         */
        @Synchronized
        fun showSoftKeyboard(context: Context, focusedView: View) {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(focusedView, 0)
        }
    }
}
