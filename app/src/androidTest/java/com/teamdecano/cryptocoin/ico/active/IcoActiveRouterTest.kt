package com.teamdecano.cryptocoin.ico.active

import com.uber.rib.core.RibTestBasePlaceholder
import com.uber.rib.core.RouterHelper

import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class IcoActiveRouterTest : RibTestBasePlaceholder() {

  @Mock internal lateinit var component: IcoActiveBuilder.Component
  @Mock internal lateinit var interactor: IcoActiveInteractor
  @Mock internal lateinit var view: IcoActiveView

  private var router: IcoActiveRouter? = null

  @Before
  fun setup() {
    MockitoAnnotations.initMocks(this)

    router = IcoActiveRouter(view, interactor, component)
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

