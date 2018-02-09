package com.teamdecano.cryptocoin.navigation

import com.uber.rib.core.ViewRouter

/**
 * Adds and removes children of {@link NavigationBuilder.NavigationScope}.
 *
 * TODO describe the possible child configurations of this scope.
 */
class NavigationRouter(
        view: NavigationView,
        interactor: NavigationInteractor,
        component: NavigationBuilder.Component) : ViewRouter<NavigationView, NavigationInteractor, NavigationBuilder.Component>(view, interactor, component)
