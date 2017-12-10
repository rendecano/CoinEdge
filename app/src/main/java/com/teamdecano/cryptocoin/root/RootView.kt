package com.teamdecano.cryptocoin.root

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout

/**
 * Top level view for [RootBuilder.RootScope].
 */
class RootView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : LinearLayout(context, attrs, defStyle), RootInteractor.RootPresenter
