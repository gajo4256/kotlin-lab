package com.comsysto.kotlinfullstack.backend

import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.binder.MeterBinder

class MeterService(private val monitoringStockService: MonitoringStockServiceDecorator): MeterBinder {
    override fun bindTo(registry: MeterRegistry?) {
        registry?.gauge("currency.throughput", monitoringStockService, { monitoringStockService.throughput().toDouble() })
    }
}