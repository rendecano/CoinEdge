package com.teamdecano.cryptocoin.ico

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.shashank.sony.fancytoastlib.FancyToast
import com.teamdecano.cryptocoin.R
import com.teamdecano.cryptocoin.ico.data.model.IcoItem
import com.teamdecano.cryptocoin.ico.presentation.IcoActiveView
import kotlinx.android.synthetic.main.ico_rib.view.*

/**
 * Top level view for {@link IcoBuilder.IcoScope}.
 */
class IcoView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) :
        LinearLayout(context, attrs, defStyle),
        IcoInteractor.IcoPresenter {

    private lateinit var adapter: CustomPagerAdapter

    override fun onFinishInflate() {
        super.onFinishInflate()

        adapter = CustomPagerAdapter()
        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)

        bgAnimationView.speed = 5f
        loadingView.speed = 2f
    }

    inner class CustomPagerAdapter : PagerAdapter() {

        private var listIcoItems: List<List<IcoItem>> = ArrayList<ArrayList<IcoItem>>()

        fun setItems(ico: List<List<IcoItem>>) {
            listIcoItems = ico
            notifyDataSetChanged()
        }

        override fun instantiateItem(collection: ViewGroup, position: Int): Any {

            val inflater = LayoutInflater.from(context)
            val layout = inflater.inflate(R.layout.ico_active, collection, false) as IcoActiveView
            layout.setItems(listIcoItems[position])

            layout.onSelectItem().subscribe({

                iconItem ->

                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(iconItem.website))
                context.startActivity(browserIntent)

            })

            collection.addView(layout)
            return layout
        }

        override fun isViewFromObject(view: View, obj: Any): Boolean {
            return view == obj
        }

        override fun getCount(): Int {
            return listIcoItems.size
        }

        override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
            (container as ViewPager).removeView(obj as View)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            if (position == 0) {
                return "ACTIVE"
            } else {
                return "UPCOMING"
            }
        }
    }

    override fun loadActiveIco(icoList: List<List<IcoItem>>) {
        adapter.setItems(icoList)
        viewPager.adapter = adapter
    }

    override fun showLoadingProgress() {
        loadingView.visibility = View.VISIBLE
        loadingView.playAnimation()
    }

    override fun hideLoadingProgress() {
        loadingView.visibility = View.GONE
        loadingView.cancelAnimation()
    }

    override fun showError(error: String) {
        FancyToast.makeText(context, error, FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show()
    }
}
