package service

import org.w3c.dom.EventSource
import org.w3c.dom.events.Event
import kotlin.coroutines.experimental.buildIterator
import kotlin.js.Json


class StockService(eventSource: EventSource = EventSource("http://localhost:9090/stocks")) {

    lateinit var currentStock: Json

    private val iterator: Iterator<Json> by lazy {
        val iterator = buildIterator({
            while (true) {
                yield(currentStock)
            }
        })
        iterator
    }

    init {
        console.log("init stockservice")
        val onMessage = { event: Event ->
            val json: Json = event as Json
            currentStock= JSON.parse(json["data"] as String)
        }
        eventSource.onmessage = onMessage
    }

    fun getStockStream(): Iterator<Json> {
        return iterator
    }
}
