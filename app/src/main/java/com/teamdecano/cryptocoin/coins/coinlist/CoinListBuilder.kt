package com.teamdecano.cryptocoin.coins.coinlist

import android.view.LayoutInflater
import android.view.ViewGroup
import com.teamdecano.cryptocoin.R
import com.teamdecano.cryptocoin.coins.coinlist.CoinListBuilder.CoinListScope
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
 * Builder for the [CoinListScope].
 *
 * TODO describe this scope's responsibility as a whole.
 */
class CoinListBuilder(dependency: ParentComponent) : ViewBuilder<CoinListView, CoinListRouter, CoinListBuilder.ParentComponent>(dependency) {

    /**
     * Builds a new [CoinListRouter].
     *
     * @param parentViewGroup parent view group that this router's view will be added to.
     * @return a new [CoinListRouter].
     */
    fun build(parentViewGroup: ViewGroup): CoinListRouter {
        val view = createView(parentViewGroup)
        val interactor = CoinListInteractor()
        val component = DaggerCoinListBuilder_Component.builder()
                .parentComponent(dependency)
                .view(view)
                .interactor(interactor)
                .build()
        return component.coinlistRouter()
    }

    override fun inflateView(inflater: LayoutInflater, parentViewGroup: ViewGroup): CoinListView? {
        return inflater.inflate(R.layout.coin_list_rib, parentViewGroup, false) as CoinListView
    }

    interface ParentComponent

    @dagger.Module
    abstract class Module {

        @CoinListScope
        @Binds
        internal abstract fun presenter(view: CoinListView): CoinListInteractor.CoinListPresenter

        @dagger.Module
        companion object {

            @CoinListScope
            @Provides
            @JvmStatic
            internal fun router(
                    component: Component,
                    view: CoinListView,
                    interactor: CoinListInteractor): CoinListRouter {
                return CoinListRouter(view, interactor, component)
            }
        }

        // TODO: Create provider methods for dependencies created by this Rib. These should be static.
    }

    @CoinListScope
    @dagger.Component(modules = arrayOf(Module::class), dependencies = arrayOf(ParentComponent::class))
    interface Component : InteractorBaseComponent<CoinListInteractor>, BuilderComponent {

        @dagger.Component.Builder
        interface Builder {
            @BindsInstance
            fun interactor(interactor: CoinListInteractor): Builder

            @BindsInstance
            fun view(view: CoinListView): Builder

            fun parentComponent(component: ParentComponent): Builder
            fun build(): Component
        }
    }

    interface BuilderComponent {
        fun coinlistRouter(): CoinListRouter
    }

    @Scope
    @Retention(CLASS)
    internal annotation class CoinListScope

    @Qualifier
    @Retention(CLASS)
    internal annotation class CoinListInternal
}
