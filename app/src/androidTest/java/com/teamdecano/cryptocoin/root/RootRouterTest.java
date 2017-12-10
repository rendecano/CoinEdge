package com.teamdecano.cryptocoin.root;

import com.uber.rib.core.RibTestBasePlaceholder;
import com.uber.rib.core.RouterHelper;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class RootRouterTest extends RibTestBasePlaceholder {

    @Mock RootBuilder.Component component;
    @Mock RootInteractor interactor;
    @Mock RootView view;

    private RootRouter router;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        router = new RootRouter(view, interactor, component);
    }

    /**
     * TODO: Delete this example and add real tests.
     */
    @Test
    public void anExampleTest_withSomeConditions_shouldPass() {
        // Use RouterHelper to drive your router's lifecycle.
        RouterHelper.attach(router);
        RouterHelper.detach(router);

        throw new RuntimeException("Remove this test and add real tests.");
    }

}
