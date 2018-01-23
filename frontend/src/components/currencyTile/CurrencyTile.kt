package components.currencyTile

import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.div
import react.dom.strong

interface CurrencyTileProps : RProps {
    var currencyName: String
    var rate: Double
    var minRate: Double
    var maxRate: Double
}

class CurrencyTile() : RComponent<CurrencyTileProps, RState>() {
    override fun RBuilder.render() {
        div(classes = "currency-tile") {
            key = "currencyTile"
            div(classes = "currency-title") {
                +props.currencyName
            }
            div(classes = "rates") {
                div(classes = "rate") {
                    +formatCurrency(props.rate)
                }
                div {
                    +"min: "
                    strong {
                        +formatCurrency(props.minRate)
                    }
                }
                div {
                    +"max: "
                    strong {
                        +formatCurrency(props.maxRate)
                    }
                }
            }
        }

    }
}

fun formatCurrency(amount: Double): String {
    return "€ ${amount.asDynamic().toFixed(2)}"
}

fun RBuilder.currencyTile(currencyName: String, rate: Double, minRate: Double, maxRate: Double) = child(CurrencyTile::class) {
    attrs.currencyName = currencyName
    attrs.rate = rate
    attrs.minRate = minRate
    attrs.maxRate = maxRate
}



