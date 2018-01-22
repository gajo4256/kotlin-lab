package app

import components.currencyTile.currencyTile
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.*

class App : RComponent<RProps, RState>() {
    override fun RBuilder.render() {
        nav(classes = "navbar navbar-dark sticky-top bg-dark flex-md-nowrap p-0") {
            key = "header"
            a(classes = "navbar-brand col-sm-3 col-md-2 mr-0", href = "#") {
                +"comSysto Kotlin Lab"
            }

            ul(classes = "navbar-nav px-3") {
                li(classes = "nav-item text-nowrap") {
                    a(classes = "nav-link", href = "#") {
                        +"Sign Out"
                    }
                }
            }
        }

        header() {
            key = "title"
            h2 {
                +"Kotlin Lab"
            }
        }

        div(classes = "currency-tiles") {
            currencyTile("Bitcoin", 9999)
            currencyTile("Ethereum", 700)
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

fun RBuilder.app() = child(App::class) {}



