package com.comsysto.kotlinfullstack.backend;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import reactor.test.StepVerifier;

public class CryptoStockServiceJavaTest {

    private CryptoStockService testee;

    @Before
    public void setUp() {
        testee = new CryptoStockService();
    }

//    @Test
//    public void currentPriceStream() {
//        StepVerifier.create(testee.currentPriceStream())
//                .consumeNextWith(it -> {
//                    Assert.assertEquals("ETH", it.getCurrency());
//                }).verifyComplete();
//    }
//
//    @Test
//    public void currentPriceStream2() {
//        StepVerifier.create(testee.currentPriceStream()).consumeNextWith(System.out::println);
//    }
}