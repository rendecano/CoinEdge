package com.teamdecano.cryptocoin.ico

import com.uber.rib.core.RibTestBasePlaceholder
import com.uber.rib.core.RouterHelper

import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class IcoRouterTest : RibTestBasePlaceholder() {

  @Mock internal lateinit var component: IcoBuilder.Component
  @Mock internal lateinit var interactor: IcoInteractor
  @Mock internal lateinit var view: IcoView

  private var router: IcoRouter? = null

  @Before
  fun setup() {
    MockitoAnnotations.initMocks(this)

    router = IcoRouter(view, interactor, component)
  }

  /**
   * TODO: Delete this example and add real tests.
   */
  @Test
  fun anExampleTest_withSomeConditions_shouldPass() {
    // Use RouterHelper to drive your router's lifecycle.
    RouterHelper.attach(router!!)
    RouterHelper.detach(router!!)

    throw RuntimeException("Remove this test and add real tests.")
  }

}

