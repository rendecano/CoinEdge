package com.teamdecano.cryptocoin.ico.active

import com.uber.rib.core.RibTestBasePlaceholder
import com.uber.rib.core.InteractorHelper

import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class IcoActiveInteractorTest : RibTestBasePlaceholder() {

  @Mock internal lateinit var presenter: IcoActiveInteractor.IcoActivePresenter
  @Mock internal lateinit var router: IcoActiveRouter

  private var interactor: IcoActiveInteractor? = null

  @Before
  fun setup() {
    MockitoAnnotations.initMocks(this)

    interactor = TestIcoActiveInteractor.create(presenter)
  }

  /**
   * TODO: Delete this example and add real tests.
   */
  @Test
  fun anExampleTest_withSomeConditions_shouldPass() {
    // Use InteractorHelper to drive your interactor's lifecycle.
    InteractorHelper.attach<IcoActiveInteractor.IcoActivePresenter, IcoActiveRouter>(interactor!!, presenter, router, null)
    InteractorHelper.detach(interactor!!)

    throw RuntimeException("Remove this test and add real tests.")
  }
}