package com.teamdecano.cryptocoin.settings

import com.uber.rib.core.Bundle
import com.uber.rib.core.Interactor
import com.uber.rib.core.RibInteractor
import javax.inject.Inject

/**
 * Coordinates Business Logic for [SettingsScope].
 *
 * TODO describe the logic of this scope.
 */
@RibInteractor
class SettingsInteractor : Interactor<SettingsInteractor.SettingsPresenter, SettingsRouter>() {

    @Inject
    lateinit var presenter: SettingsPresenter

    override fun didBecomeActive(savedInstanceState: Bundle?) {
        super.didBecomeActive(savedInstanceState)



    }

    /**
     * Presenter interface implemented by this RIB's view.
     */
    interface SettingsPresenter
}
