package com.comsysto.kotlinfullstack.backend;

import com.comsysto.kotlinfullstack.backend.inbound.EthereumStubDataRepository;
import org.junit.Test;
import reactor.test.StepVerifier;

public class DataRepositoryTest {

    EthereumStubDataRepository testee = new EthereumStubDataRepository();

//    @Test
//    public void currentPriceStream() {
//        StepVerifier.create(testee.dataStream()).consumeNextWith( it -> {
//            System.out.println(it);
//        }).verifyComplete();
//    }
}
