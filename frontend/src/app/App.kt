package app

import logo.logo
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.code
import react.dom.div
import react.dom.h2
import react.dom.p
import ticker.ticker

class App : RComponent<RProps, RState>() {
    override fun RBuilder.render() {
        div("App-header") {
            key = "header"
            logo()
            h2 {
                +"Welcome to React with Kotlin what up"
            }
        }
        p("App-intro") {
            key = "intro"
            +"To get started, edit "
            code { +"app/App.kt" }
            +" and save to reload."
        }
        p("App-ticker.ticker") {
            key = "ticker"
            +"This app has been running for "
            ticker()
            +" seconds."
        }

        div {
            key = "statusbar"
            statusBar("hello")
        }

        div {
            key = "constbar"
            constBar("Hello")
        }

    }
}

fun RBuilder.app() = child(App::class) {}



