package com.teamdecano.cryptocoin.root

import com.uber.rib.core.InteractorHelper
import com.uber.rib.core.RibTestBasePlaceholder
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class RootInteractorTest : RibTestBasePlaceholder() {

    @Mock internal lateinit var presenter: RootInteractor.RootPresenter
    @Mock internal lateinit var router: RootRouter

    private lateinit var interactor: RootInteractor

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        interactor = TestRootInteractor.create(presenter)
    }

    @Test
    fun whenBecomeActiveShouldAttachBottomNavigation() {

        InteractorHelper.attach(interactor, presenter, router, null)
        verify(router).attachNavigation()
        InteractorHelper.detach(interactor)
    }
}