package app

import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.h1
import react.dom.p

class StatusBar : RComponent<StatusBarProps, RState>() {
    override fun RBuilder.render() {
        h1 {
            p {
                + props.message
            }
        }
    }
}

class StatusBarProps(var message: String) : RProps

fun RBuilder.statusBar(msg: String) = child(StatusBar::class) { attrs.message = msg }