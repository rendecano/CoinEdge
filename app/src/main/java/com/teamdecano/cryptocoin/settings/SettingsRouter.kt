package com.teamdecano.cryptocoin.settings

import com.uber.rib.core.ViewRouter

/**
 * Adds and removes children of {@link SettingsBuilder.SettingsScope}.
 *
 * TODO describe the possible child configurations of this scope.
 */
class SettingsRouter(
        view: SettingsView,
        interactor: SettingsInteractor,
        component: SettingsBuilder.Component) : ViewRouter<SettingsView, SettingsInteractor, SettingsBuilder.Component>(view, interactor, component)
