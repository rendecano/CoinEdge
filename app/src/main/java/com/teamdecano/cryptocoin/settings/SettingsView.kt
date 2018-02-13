package com.teamdecano.cryptocoin.settings

import android.app.Dialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.util.AttributeSet
import android.widget.Button
import android.widget.LinearLayout
import com.jakewharton.rxbinding2.view.RxView
import com.shashank.sony.fancytoastlib.FancyToast
import com.teamdecano.cryptocoin.R
import io.reactivex.Observable
import kotlinx.android.synthetic.main.settings_rib.view.*


/**
 * Top level view for {@link SettingsBuilder.SettingsScope}.
 */
class SettingsView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) :
        LinearLayout(context, attrs, defStyle),
        SettingsInteractor.SettingsPresenter {

    private val TRX_ADDRESS = "0x513914eed201217a56e2217fc5ca9a71b35c3dec"

    override fun onFinishInflate() {
        super.onFinishInflate()

        btnDonate.setOnClickListener {
            showDonateDialog()
        }
    }

    override fun selectCryptoCompare(): Observable<Any> {
        return RxView.clicks(btnCryptoCompare)
    }

    override fun selectCoinMarketCap(): Observable<Any> {
        return RxView.clicks(btnCoinMarketCap)
    }

    override fun selectChasingCoins(): Observable<Any> {
        return RxView.clicks(btnChasingCoins)
    }

    private fun showDonateDialog() {

        val dialog = Dialog(context)
        dialog.setContentView(R.layout.coin_address_layout)

        val btnCopy = dialog.findViewById<Button>(R.id.btnCopyAddress)
        val btnClose = dialog.findViewById<Button>(R.id.btnClose)

        btnCopy.setOnClickListener {

            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("Tron Address", TRX_ADDRESS)
            clipboard.primaryClip = clip

            FancyToast.makeText(context, "Address copied to clipboard!", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show()

        }

        btnClose.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

}

