package app

import components.currencyChart.currencyChart
import components.currencyTile.currencyTile
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import react.*
import react.dom.*
import service.StockService
import kotlin.js.Date
import kotlin.js.Json

interface AppProps : RProps {
    var stockService: StockService
}

data class CryptoStock(val currency: String, val date: Double, val price: Double)

interface AppState : RState {
    var ethRate: Double
    var minEthRate: Double
    var maxEthRate: Double
    var rateList: MutableList<Double>
    var ethRates: MutableList<CryptoStock>
    var ltcRates: MutableList<CryptoStock>
}

class App(props: AppProps) : RComponent<AppProps, AppState>(props) {

    override fun AppState.init(props: AppProps) {
        ethRate = 0.0
        minEthRate = 9999999999.0
        maxEthRate = 0.0
        rateList = mutableListOf(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0)
        ethRates = mutableListOf(*emptyCryptoStock("ETH"))
        ltcRates = mutableListOf(*emptyCryptoStock("LTC"))
    }

    fun emptyCryptoStock(currency: String): Array<CryptoStock> {
        return (1..10).map { CryptoStock(currency, Date().getTime(), 0.0) }.toTypedArray()
    }

    override fun componentDidMount() {
        val iterator = props.stockService.getStockStream()

        launch {
            for (data in iterator) {
                if (data != null) {
                    updateExchangeRates(data)
                }
                val price = data?.get("price") as Double?
                val rate = price ?: -1.0
                if (rate != -1.0) {
                    updateRate(rate)

                }
                delay(3000)

            }
        }
    }

    fun MutableList<CryptoStock>.appendAndTrim(element: CryptoStock) {
        this.removeAt(0)
        this.add(element)
    }

    private fun updateExchangeRates(data: Json) {
        var currency: String = data["currency"] as String
        var date: Double = Date().getTime() // TODO: figure out how we can create a date from String
        var price: Double = data["price"] as Double

        var cryptoData = CryptoStock(currency, date, price)
        console.log(currency)
        when (currency) {
            "ETH" -> setState {
                ethRates.appendAndTrim(cryptoData)
                console.log("Updated ethRates:", ethRates)
            }
            "LTC" -> setState {
                ltcRates.appendAndTrim(cryptoData)
            }
        }

    }

    private fun updateRate(rate: Double) {
        val newList: MutableList<Double> = state.rateList
        newList.removeAt(0)
        newList.add(rate)
        setState {
            ethRate = rate
            minEthRate = minOf(rate, minEthRate)
            maxEthRate = maxOf(rate, maxEthRate)
            rateList = newList
        }
    }

    override fun RBuilder.render() {
        nav(classes = "navbar navbar-dark sticky-top bg-dark flex-md-nowrap p-0") {
            key = "header"
            a(classes = "navbar-brand col-sm-3 col-md-2 mr-0", href = "#") {
                +"comSysto Kotlin Lab"
            }
        }

        div(classes = "currency-tiles") {
            currencyTile("Ethereum", state.ethRates.last().price)
            currencyTile("Litecoin", state.ltcRates.last().price)
//            currencyTile("Monero", 288)
        }

        div {
            currencyChart(state.rateList)
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