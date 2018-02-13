package com.teamdecano.cryptocoin.settings

import android.view.LayoutInflater
import android.view.ViewGroup
import com.teamdecano.cryptocoin.R
import com.uber.rib.core.InteractorBaseComponent
import com.uber.rib.core.ViewBuilder
import dagger.Binds
import dagger.BindsInstance
import dagger.Provides
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy.CLASS
import javax.inject.Qualifier
import javax.inject.Scope

/**
 * Builder for the {@link SettingsScope}.
 *
 * TODO describe this scope's responsibility as a whole.
 */
class SettingsBuilder(dependency: ParentComponent) : ViewBuilder<SettingsView, SettingsRouter, SettingsBuilder.ParentComponent>(dependency) {

    /**
     * Builds a new [SettingsRouter].
     *
     * @param parentViewGroup parent view group that this router's view will be added to.
     * @return a new [SettingsRouter].
     */
    fun build(parentViewGroup: ViewGroup): SettingsRouter {
        val view = createView(parentViewGroup)
        val interactor = SettingsInteractor()
        val component = DaggerSettingsBuilder_Component.builder()
                .parentComponent(dependency)
                .view(view)
                .interactor(interactor)
                .build()
        return component.settingsRouter()
    }

    override fun inflateView(inflater: LayoutInflater, parentViewGroup: ViewGroup): SettingsView? {

        return inflater.inflate(R.layout.settings_rib, parentViewGroup, false) as SettingsView
    }

    interface ParentComponent {

    }

    @dagger.Module
    abstract class Module {

        @SettingsScope
        @Binds
        internal abstract fun presenter(view: SettingsView): SettingsInteractor.SettingsPresenter

        @dagger.Module
        companion object {

            @SettingsScope
            @Provides
            @JvmStatic
            internal fun router(
                    component: Component,
                    view: SettingsView,
                    interactor: SettingsInteractor): SettingsRouter {
                return SettingsRouter(view, interactor, component)
            }
        }
    }

    @SettingsScope
    @dagger.Component(modules = arrayOf(Module::class), dependencies = arrayOf(ParentComponent::class))
    interface Component : InteractorBaseComponent<SettingsInteractor>, BuilderComponent {

        @dagger.Component.Builder
        interface Builder {
            @BindsInstance
            fun interactor(interactor: SettingsInteractor): Builder

            @BindsInstance
            fun view(view: SettingsView): Builder

            fun parentComponent(component: ParentComponent): Builder
            fun build(): Component
        }
    }

    interface BuilderComponent {
        fun settingsRouter(): SettingsRouter
    }

    @Scope
    @Retention(CLASS)
    internal annotation class SettingsScope

    @Qualifier
    @Retention(CLASS)
    internal annotation class SettingsInternal
}
