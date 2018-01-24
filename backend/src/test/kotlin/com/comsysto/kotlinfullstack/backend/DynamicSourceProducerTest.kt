package com.comsysto.kotlinfullstack.backend

import assertk.assert
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import kotlinx.coroutines.experimental.reactor.flux
import org.junit.Before
import org.junit.Test
import reactor.test.StepVerifier

class DynamicSourceProducerTest {

    lateinit var testee: DynamicSourceProducer<String>

    val dataSource1 = flux {
        for (i in 1..2) {
            send("ds1 #$i")
        }
    }

    val dataSource2 = flux {
        for (i in 1..2) {
            send("ds2 #${i}")
        }
    }

    @Before
    fun setUp() {
        testee = DynamicSourceProducer(dataSource1)
    }

    @Test
    fun changeSource() {
        StepVerifier.create(testee)
                .consumeNextWith {
                    assert(it).isEqualTo("ds1 #1")
                    testee.changeSource(dataSource2)
                }
                .consumeNextWith {
                    assert(it).isEqualTo("ds1 #2")
                }
                .verifyComplete()

    }

}
