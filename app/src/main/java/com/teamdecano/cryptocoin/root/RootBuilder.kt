package com.teamdecano.cryptocoin.root

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.teamdecano.cryptocoin.R
import com.teamdecano.cryptocoin.coins.CoinBuilder
import com.teamdecano.cryptocoin.common.screen_stack.ScreenStack
import com.teamdecano.cryptocoin.ico.IcoBuilder
import com.teamdecano.cryptocoin.navigation.NavigationBuilder
import com.teamdecano.cryptocoin.navigation.NavigationInteractor
import com.teamdecano.cryptocoin.root.RootBuilder.RootScope
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
 * Builder for the [RootScope].
 *
 * TODO describe this scope's responsibility as a whole.
 */
class RootBuilder(dependency: ParentComponent) : ViewBuilder<RootView, RootRouter, RootBuilder.ParentComponent>(dependency) {

    /**
     * Builds a new [RootRouter].
     *
     * @param parentViewGroup parent view group that this router's view will be added to.
     * @return a new [RootRouter].
     */
    fun build(context: Context, parentViewGroup: ViewGroup, boxStore: BoxStore): RootRouter {
        val view = createView(parentViewGroup)
        val interactor = RootInteractor()
        val component = DaggerRootBuilder_Component.builder()
                .parentComponent(dependency)
                .view(view)
                .interactor(interactor)
                .provideContext(context)
                .provideBoxstore(boxStore)
                .build()
        return component.rootRouter()
    }

    override fun inflateView(inflater: LayoutInflater, parentViewGroup: ViewGroup): RootView? {
        return inflater.inflate(R.layout.activity_root, parentViewGroup, false) as RootView
    }

    interface ParentComponent// TODO: Define dependencies required from your parent interactor here.

    @dagger.Module
    abstract class Module {

        @RootScope
        @Binds
        internal abstract fun presenter(view: RootView): RootInteractor.RootPresenter

        @dagger.Module
        companion object {

            @RootScope
            @Provides
            @JvmStatic
            internal fun provideNavigationListener(rootInteractor: RootInteractor): NavigationInteractor.Listener {
                return rootInteractor.NavigationListener()
            }

            @RootScope
            @Provides
            @JvmStatic
            internal fun screenStack(rootView: RootView): ScreenStack {
                return ScreenStack(rootView.viewContent())
            }

            @RootScope
            @Provides
            @JvmStatic
            internal fun router(
                    component: Component,
                    view: RootView,
                    interactor: RootInteractor,
                    stack: ScreenStack): RootRouter {
                return RootRouter(view, stack, interactor, component, NavigationBuilder(component), CoinBuilder(component), IcoBuilder(component))
            }
        }
    }

    @RootScope
    @dagger.Component(modules = arrayOf(Module::class), dependencies = arrayOf(ParentComponent::class))
    interface Component : InteractorBaseComponent<RootInteractor>, BuilderComponent,
            NavigationBuilder.ParentComponent,
            CoinBuilder.ParentComponent,
            IcoBuilder.ParentComponent {

        @dagger.Component.Builder
        interface Builder {
            @BindsInstance
            fun interactor(interactor: RootInteractor): Builder

            @BindsInstance
            fun view(view: RootView): Builder

            fun parentComponent(component: ParentComponent): Builder

            @BindsInstance
            fun provideContext(context: Context): Builder

            @BindsInstance
            fun provideBoxstore(boxStore: BoxStore): Builder

            fun build(): Component
        }
    }

    interface BuilderComponent {
        fun rootRouter(): RootRouter
    }

    @Scope
    @Retention(CLASS)
    internal annotation class RootScope

    @Qualifier
    @Retention(CLASS)
    internal annotation class RootInternal
}
