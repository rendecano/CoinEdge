package com.teamdecano.cryptocoin.coins

import com.teamdecano.cryptocoin.coins.coinlist.CoinListBuilder
import com.teamdecano.cryptocoin.root.RootView
import com.uber.rib.core.Builder
import com.uber.rib.core.EmptyPresenter
import com.uber.rib.core.InteractorBaseComponent
import dagger.BindsInstance
import dagger.Provides
import io.objectbox.BoxStore
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy.CLASS
import javax.inject.Qualifier
import javax.inject.Scope

class CoinBuilder(dependency: ParentComponent) : Builder<CoinRouter, CoinBuilder.ParentComponent>(dependency) {

    /**
     * Builds a new [CoinRouter].
     *
     * @return a new [CoinRouter].
     */
    fun build(): CoinRouter {
        val interactor = CoinInteractor()
        val component = DaggerCoinBuilder_Component.builder()
                .parentComponent(dependency)
                .interactor(interactor)
                .build()

        return component.coinRouter()
    }

    interface ParentComponent {

        fun rootView(): RootView

        fun boxStore(): BoxStore
    }

    @dagger.Module
    object Module {

        @CoinScope
        @Provides
        @JvmStatic
        internal fun presenter(): EmptyPresenter {
            return EmptyPresenter()
        }

        @CoinScope
        @Provides
        @JvmStatic
        internal fun router(rootView: RootView, component: Component, interactor: CoinInteractor): CoinRouter {
            return CoinRouter(rootView, interactor, component, CoinListBuilder(component))
        }
    }

    @CoinScope
    @dagger.Component(modules = arrayOf(Module::class), dependencies = arrayOf(ParentComponent::class))
    interface Component : InteractorBaseComponent<CoinInteractor>, BuilderComponent,
            CoinListBuilder.ParentComponent {

        @dagger.Component.Builder
        interface Builder {
            @BindsInstance
            fun interactor(interactor: CoinInteractor): Builder

            fun parentComponent(component: ParentComponent): Builder
            fun build(): Component
        }

    }

    interface BuilderComponent {
        fun coinRouter(): CoinRouter
    }

    @Scope
    @Retention(CLASS)
    internal annotation class CoinScope


    @Qualifier
    @Retention(CLASS)
    internal annotation class CoinInternal
}
