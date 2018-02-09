package com.teamdecano.cryptocoin.ico

import android.view.LayoutInflater
import android.view.ViewGroup
import com.teamdecano.cryptocoin.R
import com.teamdecano.cryptocoin.coins.coinlist.data.network.IcoService
import com.teamdecano.cryptocoin.ico.data.repository.source.IcoListLocalRepository
import com.teamdecano.cryptocoin.ico.data.repository.source.IcoListNetworkRepository
import com.uber.rib.core.InteractorBaseComponent
import com.uber.rib.core.ViewBuilder
import dagger.Binds
import dagger.BindsInstance
import dagger.Provides
import io.objectbox.BoxStore
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy.CLASS
import javax.inject.Qualifier
import javax.inject.Scope

/**
 * Builder for the {@link IcoScope}.
 *
 * TODO describe this scope's responsibility as a whole.
 */
class IcoBuilder(dependency: ParentComponent) : ViewBuilder<IcoView, IcoRouter, IcoBuilder.ParentComponent>(dependency) {

    /**
     * Builds a new [IcoRouter].
     *
     * @param parentViewGroup parent view group that this router's view will be added to.
     * @return a new [IcoRouter].
     */
    fun build(parentViewGroup: ViewGroup): IcoRouter {
        val view = createView(parentViewGroup)
        val interactor = IcoInteractor()
        val component = DaggerIcoBuilder_Component.builder()
                .parentComponent(dependency)
                .view(view)
                .interactor(interactor)
                .build()
        return component.icoRouter()
    }

    override fun inflateView(inflater: LayoutInflater, parentViewGroup: ViewGroup): IcoView? {
        return inflater.inflate(R.layout.ico_rib, parentViewGroup, false) as IcoView
    }

    interface ParentComponent {
        fun boxStore(): BoxStore
    }

    @dagger.Module
    abstract class Module {

        @IcoScope
        @Binds
        internal abstract fun presenter(view: IcoView): IcoInteractor.IcoPresenter

        @dagger.Module
        companion object {

            @IcoScope
            @Provides
            @JvmStatic
            internal fun provideIcoService(): IcoService {
                return IcoService()
            }

            @IcoScope
            @Provides
            @JvmStatic
            internal fun provideIcoListNetworkRepository(icoService: IcoService): IcoListNetworkRepository {
                return IcoListNetworkRepository(icoService)
            }

            @IcoScope
            @Provides
            @JvmStatic
            internal fun provideIcoListLocalRepository(boxStore: BoxStore): IcoListLocalRepository {
                return IcoListLocalRepository(boxStore)
            }

            @IcoScope
            @Provides
            @JvmStatic
            internal fun router(
                    component: Component,
                    view: IcoView,
                    interactor: IcoInteractor): IcoRouter {
                return IcoRouter(view, interactor, component)
            }
        }

        // TODO: Create provider methods for dependencies created by this Rib. These should be static.
    }

    @IcoScope
    @dagger.Component(modules = arrayOf(Module::class), dependencies = arrayOf(ParentComponent::class))
    interface Component : InteractorBaseComponent<IcoInteractor>, BuilderComponent {

        @dagger.Component.Builder
        interface Builder {
            @BindsInstance
            fun interactor(interactor: IcoInteractor): Builder

            @BindsInstance
            fun view(view: IcoView): Builder

            fun parentComponent(component: ParentComponent): Builder
            fun build(): Component
        }
    }

    interface BuilderComponent {
        fun icoRouter(): IcoRouter
    }

    @Scope
    @Retention(CLASS)
    internal annotation class IcoScope

    @Qualifier
    @Retention(CLASS)
    internal annotation class IcoInternal
}
