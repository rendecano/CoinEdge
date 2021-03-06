package com.teamdecano.cryptocoin.coins.coinlist

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.teamdecano.cryptocoin.R
import com.teamdecano.cryptocoin.coins.coinlist.CoinListBuilder.CoinListScope
import com.teamdecano.cryptocoin.coins.coinlist.data.network.CoinService
import com.teamdecano.cryptocoin.coins.coinlist.data.repository.source.CoinListLocalRepository
import com.teamdecano.cryptocoin.coins.coinlist.data.repository.source.CoinListNetworkRepository
import com.uber.rib.core.InteractorBaseComponent
import com.uber.rib.core.ViewBuilder
import dagger.Binds
import dagger.BindsInstance
import dagger.Provides
import io.objectbox.BoxStore
import okhttp3.Cache
import okhttp3.OkHttpClient
import java.io.File
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

    interface ParentComponent {

        fun boxStore(): BoxStore

        fun context(): Context
    }

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
            fun provideHttpClient(context: Context): OkHttpClient {

                var cacheSize = 10 * 1024 * 1024L // 10 MB
                var cacheDirectory = File(context.cacheDir.absolutePath, "CoinEdgeCache")
                var cache = Cache(cacheDirectory, cacheSize)

                return OkHttpClient.Builder()
                        .cache(cache)
                        .build()
            }

            @CoinListScope
            @Provides
            @JvmStatic
            fun provideCoinService(okHttpClient: OkHttpClient): CoinService {
                return CoinService(okHttpClient)
            }

            @CoinListScope
            @Provides
            @JvmStatic
            fun provideCoinListNetworkRepository(coinService: CoinService): CoinListNetworkRepository {
                return CoinListNetworkRepository(coinService)
            }

            @CoinListScope
            @Provides
            @JvmStatic
            fun provideCoinListLocalRepository(boxStore: BoxStore): CoinListLocalRepository {
                return CoinListLocalRepository(boxStore)
            }

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
