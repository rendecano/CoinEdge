package com.teamdecano.cryptocoin.ico.presentation

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.jakewharton.rxbinding2.view.RxView
import com.teamdecano.cryptocoin.R
import com.teamdecano.cryptocoin.ico.data.model.IcoItem
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class IcoListAdapter(coinListModels: List<IcoItem>, context: Context) : RecyclerView.Adapter<IcoListAdapter.ViewHolder>() {

    private var mIcoListModels: List<IcoItem>
    private var mContext: Context
    private var mOnClickSubject: PublishSubject<IcoItem>

    init {
        mIcoListModels = coinListModels
        mContext = context
        mOnClickSubject = PublishSubject.create<IcoItem>()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val icoListModels: IcoItem = mIcoListModels.get(position)
        holder.txtSymbol.text = icoListModels.name
        holder.txtName.text = icoListModels.description

        holder.txtStartDate.text = "START: " + icoListModels.startTime
        holder.txtEndDate.text = "END: " + icoListModels.endTime

        Glide.with(mContext).load(icoListModels.image).into(holder.imageLogo)

        RxView.clicks(holder.btnWebsite)
                .map { icoListModels }.subscribe(mOnClickSubject)
    }

    override fun getItemCount(): Int {
        return mIcoListModels.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.ico_list_item, parent, false)

        return ViewHolder(itemView)
    }

    fun setUpdatedList(coinListModels: List<IcoItem>) {
        mIcoListModels = coinListModels
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var txtSymbol: TextView = view.findViewById(R.id.txt_symbol)
        var txtName: TextView = view.findViewById(R.id.txt_description)
        var txtStartDate: TextView = view.findViewById(R.id.txt_start_date)
        var txtEndDate: TextView = view.findViewById(R.id.txt_end_date)
        var imageLogo: ImageView = view.findViewById(R.id.coin_logo)
        var btnWebsite: Button = view.findViewById(R.id.btn_website)

    }

    fun getItemClickSignal(): Observable<IcoItem> {
        return mOnClickSubject
    }


}