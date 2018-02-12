package com.teamdecano.cryptocoin.coins.coindetails

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.teamdecano.cryptocoin.R
import com.teamdecano.cryptocoin.coins.coindetails.CoinDetailsBuilder.CoinDetailsScope
import com.teamdecano.cryptocoin.coins.coindetails.data.repository.source.CoinDetailsLocalRepository
import com.teamdecano.cryptocoin.coins.coindetails.data.repository.source.CoinDetailsNetworkRepository
import com.teamdecano.cryptocoin.coins.coinlist.CoinListBuilder
import com.teamdecano.cryptocoin.coins.coinlist.data.network.CoinService
import com.uber.rib.core.InteractorBaseComponent
import com.uber.rib.core.ViewBuilder
import dagger.Binds
import dagger.BindsInstance
import dagger.Provides
import io.realm.Realm
import io.realm.RealmConfiguration
import okhttp3.Cache
import okhttp3.OkHttpClient
import java.io.File
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
            fun provideHttpClient(context: Context): OkHttpClient {

                val cacheSize = 10 * 1024 * 1024L // 10 MB
                val cacheDirectory = File(context.cacheDir.absolutePath, "CoinEdgeCache")
                val cache = Cache(cacheDirectory, cacheSize)

                return OkHttpClient.Builder()
                        .cache(cache)
                        .build()
            }

            @CoinDetailsScope
            @Provides
            @JvmStatic
            internal fun provideCoinService(okHttpClient: OkHttpClient): CoinService {
                return CoinService(okHttpClient)
            }

            @CoinDetailsScope
            @Provides
            @JvmStatic
            fun provideCoinDetailsNetworkRepository(coinService: CoinService): CoinDetailsNetworkRepository {
                return CoinDetailsNetworkRepository(coinService)
            }

            @CoinDetailsScope
            @Provides
            @JvmStatic
            fun provideCoinDetailsLocalRepository(realm: Realm): CoinDetailsLocalRepository {
                return CoinDetailsLocalRepository(realm)
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

            @CoinDetailsScope
            @Provides
            @JvmStatic
            internal fun realm(): Realm {

                // TODO: Find ways to implement this properly
                val stringKey = "d2345678e012345678901c3456789012a456789012n45678901234o678901227"

                val realmConfiguration = RealmConfiguration.Builder()
                        .encryptionKey(stringKey.toByteArray())
                        .name("coinedge.realm")
                        .deleteRealmIfMigrationNeeded()
                        .build()
                return Realm.getInstance(realmConfiguration)
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
