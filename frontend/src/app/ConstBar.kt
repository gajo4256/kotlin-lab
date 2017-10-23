package app

import kotlinx.html.js.onClickFunction
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.button
import react.dom.p

class ConstProps(val message: String) : RProps
class ConstState(val counter: Int) : RState

class ConstBar(props : ConstProps) : RComponent<ConstProps, ConstState>(props) {

    init {
        state = ConstState(0)
    }

    override fun RBuilder.render() {
        p {
            +props.message
        }

        p {
            +"times clicked: ${state.counter}"
        }

        button {
            +"click me to inc"

            attrs {
                onClickFunction = { event ->
                    setState(ConstState(state.counter + 1))
                }
            }
        }
    }
}

fun RBuilder.constBar(msg: String) = child(::ConstBar, ConstProps(msg)) {}