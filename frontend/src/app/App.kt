package app

import components.currencyTile.currencyTile
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import react.*
import react.dom.*
import service.StockService

interface AppProps : RProps {
    var stockService: StockService
}

interface AppState : RState {
    var ethRate: Double
    var minEthRate: Double
    var maxEthRate: Double
}

class App(props: AppProps) : RComponent<AppProps, AppState>(props) {

    override fun AppState.init(props: AppProps) {
        ethRate = 0.0
        minEthRate = 9999999999.0
        maxEthRate = 0.0
    }

    override fun componentDidMount() {
        val iterator = props.stockService.getStockStream()

        launch {
            for (x in iterator) {
                val price = x?.get("price") as Double?
                val rate = price ?: -1.0
                updateRate(rate)
                delay(1000)

            }
        }
    }

    fun updateRate(rate: Double) {
        if (rate != -1.0) {
            setState {
                ethRate = rate
                minEthRate = minOf(rate, minEthRate)
                maxEthRate = maxOf(rate, maxEthRate)
            }

        }
    }

    override fun RBuilder.render() {
        console.log(state.ethRate)
        nav(classes = "navbar navbar-dark sticky-top bg-dark flex-md-nowrap p-0") {
            key = "header"
            a(classes = "navbar-brand col-sm-3 col-md-2 mr-0", href = "#") {
                +"comSysto Kotlin Lab"
            }
        }

        div(classes = "currency-tiles") {
//            currencyTile("Bitcoin", 9999.34532, 123.0, 540.0)
            currencyTile("Ethereum", state.ethRate, state.minEthRate, state.maxEthRate)
//            currencyTile("Monero", 288)
        }

        footer(classes = "footer") {
            key = "footer"
            div(classes = "container") {
                span(classes = "text-muted") {
                    +"comSysto 2018"
                }
            }
        }
    }
}

fun RBuilder.app() = child(App::class) {
    attrs.stockService = StockService()
}