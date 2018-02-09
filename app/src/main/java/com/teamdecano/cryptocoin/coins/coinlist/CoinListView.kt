package com.teamdecano.cryptocoin.coins.coinlist

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.support.annotation.ColorInt
import android.support.v4.content.ContextCompat
import android.support.v4.content.res.ResourcesCompat
import android.support.v4.view.MenuItemCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.util.AttributeSet
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import com.jakewharton.rxbinding2.support.v4.widget.RxSwipeRefreshLayout
import com.jakewharton.rxbinding2.view.RxView
import com.shashank.sony.fancytoastlib.FancyToast
import com.teamdecano.cryptocoin.R
import com.teamdecano.cryptocoin.coins.coinlist.presentation.CoinListModel
import com.teamdecano.cryptocoin.common.OptionsMenuService
import com.teamdecano.cryptocoin.common.screen_stack.Services
import io.reactivex.Observable
import kotlinx.android.synthetic.main.coin_list_rib.view.*

/**
 * Top level view for [CoinListBuilder.CoinListScope].
 */
class CoinListView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) :
        LinearLayout(context, attrs, defStyle),
        CoinListInteractor.CoinListPresenter {

    private lateinit var mCoinListAdapter: CoinListAdapter
    private lateinit var mLayoutManager: RecyclerView.LayoutManager
    private lateinit var mCoinModels: List<CoinListModel>

    private lateinit var menuService: OptionsMenuService

    override fun onFinishInflate() {
        super.onFinishInflate()

        // use a linear layout manager
        mLayoutManager = LinearLayoutManager(context)
        rvCoins.setLayoutManager(mLayoutManager)

        mCoinModels = ArrayList<CoinListModel>()

        mCoinListAdapter = CoinListAdapter(mCoinModels, context)
        rvCoins.adapter = mCoinListAdapter

        rvCoins.addItemDecoration(LineItemDecoration(context,
                DividerItemDecoration.VERTICAL,
                ContextCompat.getDrawable(context, R.drawable.line_divider)!!))

        bgAnimationView.speed = 5f
        loadingView.speed = 2f
    }

    override fun onSelectCoin(): Observable<CoinListModel> {
        return mCoinListAdapter.getItemClickSignal()
    }

    override fun showCoinList(coinList: List<CoinListModel>) {
        mCoinListAdapter.updateList(coinList)
    }

    override fun showError(error: String) {

        FancyToast.makeText(context, error, FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show()
    }

    override fun showLoadingProgress() {
        loadingView.visibility = View.VISIBLE
        loadingView.playAnimation()
    }

    override fun hideLoadingProgress() {
        loadingView.visibility = View.GONE
        loadingView.cancelAnimation()
        swipeRefreshLayout.isRefreshing = false
    }

    override fun onRefresh(): Observable<Any> {
        return RxSwipeRefreshLayout.refreshes(swipeRefreshLayout)
    }

    inner class LineItemDecoration : android.support.v7.widget.DividerItemDecoration {

        constructor(context: Context, orientation: Int, @ColorInt color: Int) : super(context, orientation) {

            setDrawable(ColorDrawable(color))
        }

        constructor(context: Context, orientation: Int, drawable: Drawable) : super(context, orientation) {

            setDrawable(drawable)
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        menuService = Services.getOptionsMenuService(context)
        menuService.addOptionsMenuListener(optionsMenuListener)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        menuService.removeOptionsMenuListener(optionsMenuListener)
    }

    private val optionsMenuListener = object : OptionsMenuService.Listener {

        override fun onPrepareOptionsMenu(menuInflater: MenuInflater, menu: Menu) {

            menuInflater.inflate(R.menu.main_menu, menu)

            val searchView = MenuItemCompat.getActionView(menu.findItem(R.id.action_search)) as SearchView

            val editText = searchView.findViewById<EditText>(android.support.v7.appcompat.R.id.search_src_text)
            editText.setHintTextColor(ContextCompat.getColor(context, R.color.colorWhite))
            editText.setTextColor(ContextCompat.getColor(context, R.color.colorWhite))

            searchView.isIconified = false;
            searchView.setIconifiedByDefault(false);

            val searchIcon = searchView.findViewById<ImageView>(android.support.v7.appcompat.R.id.search_mag_icon) as ImageView
            searchIcon.setImageResource(R.drawable.ic_search)

            val typeface = ResourcesCompat.getFont(context, R.font.orbitron);
            editText.typeface = typeface


            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String): Boolean {

                    if (mCoinListAdapter != null) mCoinListAdapter.filter.filter(newText)
                    return true
                }
            })
        }

        override fun onOptionsItemSelected(item: MenuItem): Boolean {

//            if (item.itemId == MENU_REFRESH) {
//                refresh.accept(Notification.INSTANCE)
//                return true
//            } else if (item.itemId == MENU_CLEAR_COMPLETED) {
//                clearCompleted.accept(Notification.INSTANCE)
//                return true
//            } else if (item.itemId == MENU_FILTER_ALL) {
//                filterEvent.accept(TasksInteractor.Filter.ALL)
//                return true
//            } else if (item.itemId == MENU_FILTER_ACTIVE) {
//                filterEvent.accept(TasksInteractor.Filter.ACTIVE)
//                return true
//            } else if (item.itemId == MENU_FILTER_COMPLETED) {
//                filterEvent.accept(TasksInteractor.Filter.COMPLETED)
//                return true
//            }
            return false
        }
    }
}
