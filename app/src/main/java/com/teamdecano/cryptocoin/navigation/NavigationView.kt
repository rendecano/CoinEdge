package com.teamdecano.cryptocoin.navigation

import android.content.Context
import android.support.design.widget.BottomNavigationView
import android.util.AttributeSet
import com.jakewharton.rxrelay2.BehaviorRelay
import com.teamdecano.cryptocoin.R
import io.reactivex.Observable

/**
 * Top level view for {@link NavigationBuilder.NavigationScope}.
 */
class NavigationView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : BottomNavigationView(context, attrs, defStyle), NavigationInteractor.NavigationPresenter {

    private val behaviorRelay = BehaviorRelay.createDefault(MenuEvent.LIST)
    private val menuRelay = behaviorRelay.toSerialized()

    override fun onFinishInflate() {
        super.onFinishInflate()

        setOnNavigationItemSelectedListener { item ->

            when (item.itemId) {

                R.id.navigation_list -> menuRelay.accept(MenuEvent.LIST)
                R.id.navigation_ico -> menuRelay.accept(MenuEvent.ICO)
                R.id.navigation_convert -> menuRelay.accept(MenuEvent.CONVERT)
                R.id.navigation_settings -> menuRelay.accept(MenuEvent.SETTINGS)
            }

            true
        }
    }

    override fun menuEvents(): Observable<MenuEvent> {
        return menuRelay.distinctUntilChanged()
    }
}
