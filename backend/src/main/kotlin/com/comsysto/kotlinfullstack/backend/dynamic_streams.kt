package com.comsysto.kotlinfullstack.backend

import org.reactivestreams.Publisher
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
import reactor.core.publisher.Flux


class DynamicSourceProducer<T>(source: Publisher<T>, private var sourceFlux: Flux<T> = Flux.from(source)) : Publisher<T> by sourceFlux {

    private var subscribers: List<SuspendableSubscriber<in T>> = listOf()

    override fun subscribe(s: Subscriber<in T>?) {
        if (s != null) {
            val suspendableSubscriber = SuspendableSubscriber(s)
            subscribers += suspendableSubscriber
            sourceFlux.subscribe(suspendableSubscriber)
        } else {
            throw RuntimeException("WTF??")
        }
    }

    fun changeSource(source: Publisher<T>) {
        subscribers.forEach {
            it.suspendComplete = true
            it.suspendSubscribe = true
            source.subscribe(it)
            it.suspendComplete = false
            it.suspendSubscribe = false
        }


    }


}

class SuspendableSubscriber<T>(val actual: Subscriber<T>) : Subscriber<T> by actual {
    var suspendComplete = false
    var suspendSubscribe = false
    var currentSubscription: Subscription? = null

    override fun onComplete() {
        if (!suspendComplete) {
            actual.onComplete()
        }
    }

    override fun onSubscribe(s: Subscription?) {
        if (!suspendSubscribe) {
            actual.onSubscribe(s)
        }
        currentSubscription = s
    }
}