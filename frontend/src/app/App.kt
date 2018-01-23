package app

import components.currencyTile.currencyTile
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.*
import service.StockService


class App : RComponent<AppProps, RState>() {

    var testRate: Number = 0

    override fun componentDidMount() {
        val iterator = props.stockService.getStockStream()

        launch {
            for (x in iterator) {
                val price = x?.get("price") as Number?
                testRate = price ?:-1 as Number
                console.log(testRate)
                delay(1000)
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

        div(classes = "currency-tiles") {
            currencyTile("Bitcoin", 9999.34532)
            currencyTile("Ethereum", testRate)
            currencyTile("Monero", 288)
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

interface AppProps : RProps {
    var stockService: StockService
}



