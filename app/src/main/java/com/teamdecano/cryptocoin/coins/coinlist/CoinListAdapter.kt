package com.teamdecano.cryptocoin.coins.coinlist

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.bumptech.glide.Glide
import com.jakewharton.rxbinding2.view.RxView
import com.teamdecano.cryptocoin.R
import com.teamdecano.cryptocoin.coins.coinlist.presentation.CoinListModel
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class CoinListAdapter(coinListModels: List<CoinListModel>, context: Context) : RecyclerView.Adapter<CoinListAdapter.ViewHolder>(), Filterable {

    private var mCoinListModels: List<CoinListModel>
    private var mFilteredList: List<CoinListModel>
    private var mContext: Context
    private var mOnClickSubject: PublishSubject<CoinListModel>

    init {
        mCoinListModels = coinListModels
        mFilteredList = coinListModels
        mContext = context
        mOnClickSubject = PublishSubject.create<CoinListModel>()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val coinListModels: CoinListModel = mFilteredList.get(position)
        holder.txtSymbol.text = coinListModels.name
        holder.txtName.text = coinListModels.coinName
        holder.txtPercentage.text = coinListModels.percentage + "%"
        holder.txtPrice.text = "$" + coinListModels.price
        holder.txtVolume.text = "VOL: " + coinListModels.volume

        holder.txtPercentage.setTextColor(ContextCompat.getColor(mContext, R.color.colorPercentage))
        if (holder.txtPercentage.text.contains("-")) {
            holder.txtPercentage.setTextColor(ContextCompat.getColor(mContext, R.color.colorPercentageNegative))
        }

        Glide.with(mContext).load(coinListModels.imageUrl).into(holder.imageLogo)

        RxView.clicks(holder.cardView)
                .map { coinListModels }.subscribe(mOnClickSubject)
    }

    override fun getItemCount(): Int {
        return mFilteredList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.coin_list_item, parent, false)

        return ViewHolder(itemView)
    }

    fun updateList(coinListModels: List<CoinListModel>) {
        mCoinListModels = coinListModels
        mFilteredList = coinListModels
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var txtSymbol: TextView = view.findViewById(R.id.txt_symbol)
        var txtName: TextView = view.findViewById(R.id.txt_name)
        var txtPercentage: TextView = view.findViewById(R.id.txt_percent)
        var txtPrice: TextView = view.findViewById(R.id.txt_price)
        var txtVolume: TextView = view.findViewById(R.id.txt_volume)
        var imageLogo: ImageView = view.findViewById(R.id.coin_logo)
        var cardView: RelativeLayout = view.findViewById(R.id.item_root)

    }

    fun getItemClickSignal(): Observable<CoinListModel> {
        return mOnClickSubject
    }

    override fun getFilter(): Filter {

        return (object : Filter() {
            override fun performFiltering(charSequence: CharSequence?): FilterResults {

                val charString = charSequence.toString().toLowerCase()

                if (charString.isEmpty()) {

                    mFilteredList = mCoinListModels

                } else {

                    val filteredList = ArrayList<CoinListModel>()

                    for (coin in mCoinListModels) {

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
                mFilteredList = filterResults.values as List<CoinListModel>
                notifyDataSetChanged()
            }
        })
    }
}