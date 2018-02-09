package com.teamdecano.cryptocoin.ico

import android.content.Intent
import android.net.Uri
import com.uber.rib.core.ViewRouter

/**
 * Adds and removes children of {@link IcoBuilder.IcoScope}.
 *
 * TODO describe the possible child configurations of this scope.
 */
class IcoRouter(
        view: IcoView,
        interactor: IcoInteractor,
        component: IcoBuilder.Component) : ViewRouter<IcoView, IcoInteractor, IcoBuilder.Component>(view, interactor, component) {


    fun routeToExternalLink(url: String) {

        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        view.context.startActivity(browserIntent)

    }

}
