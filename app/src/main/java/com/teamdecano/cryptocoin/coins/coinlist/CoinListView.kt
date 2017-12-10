package com.teamdecano.cryptocoin.coins.coinlist

import android.content.Context
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.airbnb.lottie.LottieAnimationView
import com.teamdecano.cryptocoin.R
import io.reactivex.Observable

/**
 * Top level view for [CoinListBuilder.CoinListScope].
 */
class CoinListView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) :
        LinearLayout(context, attrs, defStyle),
        CoinListInteractor.CoinListPresenter {

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mCoinListAdapter: CoinListAdapter
    private lateinit var mLayoutManager: RecyclerView.LayoutManager
    private lateinit var mCoinViewModels: List<CoinListViewModel>

    private lateinit var mBgAnimation: LottieAnimationView
    private lateinit var mLoadingView: LottieAnimationView

    private lateinit var mSearchView: SearchView

    override fun onFinishInflate() {
        super.onFinishInflate()

        mRecyclerView = findViewById(R.id.rv_coins)

        mBgAnimation = findViewById(R.id.animation_bg_view)
        mLoadingView = findViewById(R.id.animation_view)

        mSearchView = findViewById(R.id.search_view)

        // use a linear layout manager
        mLayoutManager = LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mCoinViewModels = ArrayList<CoinListViewModel>()

        mCoinListAdapter = CoinListAdapter(mCoinViewModels, context);
        mRecyclerView.setAdapter(mCoinListAdapter);

        mBgAnimation.speed = 1.5f
        mLoadingView.speed = 2f

        mSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {

                if (mCoinListAdapter != null) mCoinListAdapter.getFilter().filter(newText)
                return true
            }
        })
    }

    override fun onSelectCoin(): Observable<CoinListViewModel> {
        return mCoinListAdapter.getItemClickSignal()
    }

    override fun showCoinList(coinList: List<CoinListViewModel>) {
        mCoinListAdapter.updateList(coinList)
    }

    override fun showError(error: String) {
        Snackbar.make(this, error, Snackbar.LENGTH_SHORT).show()
    }

    override fun showLoadingProgress() {
        mLoadingView.visibility = View.VISIBLE
        mLoadingView.playAnimation()
    }

    override fun hideLoadingProgress() {
        mLoadingView.visibility = View.GONE
        mLoadingView.cancelAnimation()
    }
}
