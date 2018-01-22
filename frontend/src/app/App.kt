package app

import components.currencyTile.currencyTile
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.div
import react.dom.h2
import react.dom.header

class App : RComponent<RProps, RState>() {
    override fun RBuilder.render() {
        header() {
            key = "header"
            h2 {
                +"Kotlin Lab"
            }
        }
        div(classes = "currency-tiles") {
            currencyTile("Bitcoin", 9999)
            currencyTile("Ethereum", 700)
            currencyTile("Monero", 288)

        }
    }
}

fun RBuilder.app() = child(App::class) {}



