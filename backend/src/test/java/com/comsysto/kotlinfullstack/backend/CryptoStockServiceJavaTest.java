package com.comsysto.kotlinfullstack.backend;

import org.junit.Before;
import org.junit.Test;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

public class CryptoStockServiceJavaTest {

    private CryptoStockService testee;

    @Before
    public void setUp() {
        testee = new CryptoStockService();
    }

    @Test
    public void currentPriceStream() {
        StepVerifier.create(testee.currentPriceStream())
                .expectNext(new CryptoStock("ETH", ZonedDateTime.now(), BigDecimal.ONE));
    }
}