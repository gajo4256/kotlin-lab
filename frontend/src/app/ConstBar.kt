package app

import kotlinx.coroutines.experimental.*
import kotlinx.html.js.onClickFunction
import org.w3c.fetch.CORS
import org.w3c.fetch.RequestMode
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
        state = ConstState(0, "no message yet from backend. So sad.")
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
                    launch {
                        val message = fetchMessage(state.counter)
                        setState(ConstState(state.counter + 1, message.message))
                    }
                }
            }
        }
    }
}

private suspend fun fetchMessage(count: Int): Message {
    val response = rest.fetch("http://localhost:9090/hello?count=$count") {
        headers = json("Accept" to "application/json")
        mode = RequestMode.CORS
    }.await()
    return response.fromJson { Message(get("message") as String) }
}



fun RBuilder.constBar(msg: String) = child(::ConstBar, ConstProps(msg)) {}