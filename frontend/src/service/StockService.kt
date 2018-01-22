package service

import org.w3c.dom.EventSource
import org.w3c.dom.events.Event
import kotlin.coroutines.experimental.buildIterator
import kotlin.js.Json


class StockService(eventSource: EventSource = EventSource("http://localhost:9090/stocks")){

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
        val onMessage = { event: Event ->
            val json: Json = event as Json
            val any = json["data"]
            currentStock = any!! as Json

        }
        eventSource.onmessage = onMessage
    }

    fun getStockStream(): Iterator<Json> {
        return iterator
    }
}
