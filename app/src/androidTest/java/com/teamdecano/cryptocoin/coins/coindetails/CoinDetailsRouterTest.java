package com.teamdecano.cryptocoin.coins.coindetails;

import com.uber.rib.core.RibTestBasePlaceholder;
import com.uber.rib.core.RouterHelper;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class CoinDetailsRouterTest extends RibTestBasePlaceholder {

    @Mock CoinDetailsBuilder.Component component;
    @Mock CoinDetailsInteractor interactor;
    @Mock CoinDetailsView view;

    private CoinDetailsRouter router;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        router = new CoinDetailsRouter(view, interactor, component);
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
