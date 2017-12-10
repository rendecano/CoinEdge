package com.teamdecano.cryptocoin.coins.coinlist

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.bumptech.glide.Glide
import com.jakewharton.rxbinding2.view.RxView
import com.teamdecano.cryptocoin.R
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class CoinListAdapter(coinListViewModels: List<CoinListViewModel>, context: Context) : RecyclerView.Adapter<CoinListAdapter.ViewHolder>(), Filterable {

    private var mCoinListViewModels: List<CoinListViewModel>
    private var mFilteredList: List<CoinListViewModel>
    private var mContext: Context
    private var mOnClickSubject: PublishSubject<CoinListViewModel>

    init {
        mCoinListViewModels = coinListViewModels
        mFilteredList = coinListViewModels
        mContext = context
        mOnClickSubject = PublishSubject.create<CoinListViewModel>()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val coinListViewModels: CoinListViewModel = mFilteredList.get(position)
        holder.txtSymbol.setText(coinListViewModels.name)
        holder.txtName.setText(coinListViewModels.coinName)

        Glide.with(mContext).load(coinListViewModels.imageUrl).into(holder.imageLogo)

        RxView.clicks(holder.cardView)
                .map { coinListViewModels }.subscribe(mOnClickSubject)
    }

    override fun getItemCount(): Int {
        return mFilteredList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.coin_list_item, parent, false)

        return ViewHolder(itemView)
    }

    fun updateList(coinListViewModels: List<CoinListViewModel>) {
        mCoinListViewModels = coinListViewModels
        mFilteredList = coinListViewModels
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var txtSymbol: TextView
        var txtName: TextView
        var imageLogo: ImageView
        var cardView: RelativeLayout

        init {
            txtSymbol = view.findViewById(R.id.txt_symbol)
            txtName = view.findViewById(R.id.txt_name)
            imageLogo = view.findViewById(R.id.coin_logo)
            cardView = view.findViewById(R.id.item_root)
        }
    }

    fun getItemClickSignal(): Observable<CoinListViewModel> {
        return mOnClickSubject
    }

    override fun getFilter(): Filter {

        return (object : Filter() {
            override fun performFiltering(charSequence: CharSequence?): FilterResults {

                val charString = charSequence.toString()

                if (charString.isEmpty()) {

                    mFilteredList = mCoinListViewModels

                } else {

                    val filteredList = ArrayList<CoinListViewModel>()

                    for (coin in mCoinListViewModels) {

                        if (coin.coinName!!.toLowerCase().contains(charString) || coin.name!!.toLowerCase().contains(charString)) {

                            filteredList.add(coin)
                        }
                    }

                    mFilteredList = filteredList
                }

                val filterResults = FilterResults()
                filterResults.values = mFilteredList
                return filterResults
            }

            override fun publishResults(p0: CharSequence?, filterResults: FilterResults) {
                mFilteredList = filterResults.values as List<CoinListViewModel>
                notifyDataSetChanged()
            }
        })
    }
}