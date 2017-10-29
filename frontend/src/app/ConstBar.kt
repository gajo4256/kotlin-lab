package app

import coroutines.async
import coroutines.await
import coroutines.launch
import kotlinx.html.js.onClickFunction
import org.w3c.fetch.*
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.button
import react.dom.p
import rest.fromJson
import kotlin.js.json

class ConstProps(val message: String) : RProps
class ConstState(val counter: Int, val msg: String) : RState

class ConstBar(props: ConstProps) : RComponent<ConstProps, ConstState>(props) {

    init {
        state = ConstState(0, "no message yet from backend")
    }

    override fun RBuilder.render() {
        p {
            +props.message
        }

        p {
            +"times clicked: ${state.counter} message: ${state.msg}"
        }

        button {
            +"click me to inc"

            attrs {
                onClickFunction = { event ->
                    //setState(ConstState(state.counter + 1, "no message yet"))
                    launch {
                        val message = async {
                            rest.get("http://localhost:9090/hello?count=${state.counter}") {
                                headers = json("Accept" to "application/json")
                                mode = RequestMode.CORS
                            }.fromJson {
                                Message(get("message") as String)
                            }
                        }.await()
                        setState(ConstState(state.counter + 1, message.message))
                    }
                }
            }
        }
    }
}

fun RBuilder.constBar(msg: String) = child(::ConstBar, ConstProps(msg)) {}