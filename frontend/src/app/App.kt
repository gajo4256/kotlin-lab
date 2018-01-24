package app

import com.comsysto.kotlinfullstack.CryptoStock
import com.comsysto.kotlinfullstack.Date
import components.currencyChart.currencyChart
import components.currencyTile.currencyTile
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import react.*
import react.dom.*
import service.StockService
import kotlin.js.Json

interface AppProps : RProps {
    var stockService: StockService
}

interface AppState : RState {
    var etcStocks: MutableList<CryptoStock>
    var ltcStocks: MutableList<CryptoStock>
}

class App(props: AppProps) : RComponent<AppProps, AppState>(props) {

    override fun AppState.init(props: AppProps) {
        etcStocks = mutableListOf(*emptyCryptoStockData("ETH"))
        ltcStocks = mutableListOf(*emptyCryptoStockData("LTC"))
    }

    fun emptyCryptoStockData(currency: String): Array<CryptoStock> {
        return (1..20).map { CryptoStock(currency, Date(), 0.0) }.toTypedArray()
    }

    override fun componentDidMount() {
        val iterator = props.stockService.getStockStream()

        launch {
            for (data in iterator) {
                if (data != null) {
                    updateExchangeRates(data)
                }
                delay(1000)
            }
        }
    }

    fun MutableList<CryptoStock>.appendAndTrim(element: CryptoStock) {
        this.removeAt(0)
        this.add(element)
    }

    private fun updateExchangeRates(data: Json) {
        var currency: String = data["currency"] as String
        var date: Date = Date() // TODO: figure out how we can create a date from String
        var price: Double = data["price"] as Double

        var cryptoData = CryptoStock(currency, date, price)
        when (currency) {
            "ETH" -> setState {
                etcStocks.appendAndTrim(cryptoData)
                ltcStocks.appendAndTrim(ltcStocks.last())
            }
            "LTC" -> setState {
                ltcStocks.appendAndTrim(cryptoData)
                etcStocks.appendAndTrim(etcStocks.last())
            }
        }
    }

    override fun RBuilder.render() {
        nav(classes = "navbar navbar-dark sticky-top bg-dark flex-md-nowrap p-0") {
            key = "header"
            a(classes = "navbar-brand col-sm-3 col-md-2 mr-0", href = "#") {
                +"comSysto Kotlin Lab"
            }
        }

        div(classes="jumbotron") {
            div(classes="container") {
                h1(classes="display-3") {
                    +"Cryptocurrency Stocks"
                }
            }
        }

        div(classes="container") {
            div(classes = "currency-tiles") {
                currencyTile("Ethereum", state.etcStocks.last().price, "currency-tile--eth")
                currencyTile("Litecoin", state.ltcStocks.last().price, "currency-tile--ltc")
//            currencyTile("Monero", 288)
            }

            div {
                currencyChart(state.etcStocks, state.ltcStocks)
            }
        }

        footer(classes = "footer") {
            key = "footer"
            div(classes = "container text-center") {
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