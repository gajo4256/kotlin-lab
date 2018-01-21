package ticker


import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import react.*
import react.dom.span

interface TickerProps : RProps {
    var startFrom: Int
}

interface TickerState : RState {
    var secondsElapsed: Int
}

class Ticker(props: TickerProps) : RComponent<TickerProps, TickerState>(props) {
    override fun TickerState.init(props: TickerProps) {
        secondsElapsed = props.startFrom
    }

    var doTick = true

    override fun componentDidMount() {
        tickForEver()
    }

    override fun componentWillUnmount() {
        doTick = false
    }

    override fun RBuilder.render() {
        span { +state.secondsElapsed.toString() }
    }
}

fun RBuilder.ticker(startFrom: Int = 0) = child(Ticker::class) {
    attrs.startFrom = startFrom
}

private fun Ticker.tickForEver() {
    launch {
        if (doTick) {
            setState { secondsElapsed += 1 }
            delay(1000)
            tickForEver()
        }
    }
}
