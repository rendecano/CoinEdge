package com.teamdecano.cryptocoin.ico.presentation

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.widget.LinearLayout
import com.teamdecano.cryptocoin.ico.data.model.IcoItem
import io.reactivex.Observable
import kotlinx.android.synthetic.main.ico_active.view.*

/**
 * Top level view for {@link IcoBuilder.IcoScope}.
 */
class IcoActiveView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) :
        LinearLayout(context, attrs, defStyle) {


    private lateinit var mIcoListAdapter: IcoListAdapter
    private lateinit var mLayoutManager: RecyclerView.LayoutManager
    private lateinit var mIcoModels: List<IcoItem>

    override fun onFinishInflate() {
        super.onFinishInflate()

        // use a linear layout manager
        mLayoutManager = LinearLayoutManager(context)
        rvIcoActive.setLayoutManager(mLayoutManager)

        mIcoModels = ArrayList<IcoItem>()

        mIcoListAdapter = IcoListAdapter(mIcoModels, context);
        rvIcoActive.adapter = mIcoListAdapter
    }

    fun setItems(icoList: List<IcoItem>) {

        mIcoListAdapter.updateList(icoList)
    }

    fun onSelectItem(): Observable<IcoItem> {
        return mIcoListAdapter.getItemClickSignal()
    }

}
