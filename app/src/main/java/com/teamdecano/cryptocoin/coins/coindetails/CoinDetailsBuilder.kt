package com.teamdecano.cryptocoin.coins.coindetails

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.teamdecano.cryptocoin.R
import com.teamdecano.cryptocoin.coins.coindetails.CoinDetailsBuilder.CoinDetailsScope
import com.teamdecano.cryptocoin.coins.coinlist.data.network.CoinService
import com.teamdecano.cryptocoin.coins.coinlist.data.network.IcoService
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
 * Builder for the [CoinDetailsScope].
 *
 * TODO describe this scope's responsibility as a whole.
 */
class CoinDetailsBuilder(dependency: ParentComponent) : ViewBuilder<CoinDetailsView, CoinDetailsRouter, CoinDetailsBuilder.ParentComponent>(dependency) {

    /**
     * Builds a new [CoinDetailsRouter].
     *
     * @param parentViewGroup parent view group that this router's view will be added to.
     * @return a new [CoinDetailsRouter].
     */
    fun build(parentViewGroup: ViewGroup, coinId: String, context: Context): CoinDetailsRouter {
        val view = createView(parentViewGroup)
        val interactor = CoinDetailsInteractor()
        val component = DaggerCoinDetailsBuilder_Component.builder()
                .parentComponent(dependency)
                .view(view)
                .interactor(interactor)
                .coinId(coinId)
                .provideContext(context)
                .build()
        return component.coindetailsRouter()
    }

    override fun inflateView(inflater: LayoutInflater, parentViewGroup: ViewGroup): CoinDetailsView? {
        return inflater.inflate(R.layout.coin_details_rib, parentViewGroup, false) as CoinDetailsView
    }

    interface ParentComponent

    @dagger.Module
    abstract class Module {

        @CoinDetailsScope
        @Binds
        internal abstract fun presenter(view: CoinDetailsView): CoinDetailsInteractor.CoinDetailsPresenter

        @dagger.Module
        companion object {

            @CoinDetailsScope
            @Provides
            @JvmStatic
            internal fun provideCoinService(): CoinService {
                return CoinService()
            }

            @CoinDetailsScope
            @Provides
            @JvmStatic
            internal fun router(
                    component: Component,
                    view: CoinDetailsView,
                    interactor: CoinDetailsInteractor): CoinDetailsRouter {
                return CoinDetailsRouter(view, interactor, component)
            }
        }
    }

    @CoinDetailsScope
    @dagger.Component(modules = arrayOf(Module::class), dependencies = arrayOf(ParentComponent::class))
    interface Component : InteractorBaseComponent<CoinDetailsInteractor>, BuilderComponent {

        @dagger.Component.Builder
        interface Builder {
            @BindsInstance
            fun interactor(interactor: CoinDetailsInteractor): Builder

            @BindsInstance
            fun view(view: CoinDetailsView): Builder

            fun parentComponent(component: ParentComponent): Builder

            @BindsInstance
            fun coinId(coinId: String): Builder

            @BindsInstance
            fun provideContext(context: Context): Builder

            fun build(): Component
        }
    }

    interface BuilderComponent {
        fun coindetailsRouter(): CoinDetailsRouter
    }

    @Scope
    @Retention(CLASS)
    internal annotation class CoinDetailsScope

    @Qualifier
    @Retention(CLASS)
    internal annotation class CoinDetailsInternal
}
