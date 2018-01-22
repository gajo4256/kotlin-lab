package components.currencyTile

import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.div

interface CurrencyTileProps : RProps {
    var currencyName: String
    var rate: Number
}

class CurrencyTile() : RComponent<CurrencyTileProps, RState>() {
    override fun RBuilder.render() {
        div(classes = "currency-tile") {
            key = "currencyTile"
            div(classes = "currency-title") {
                +props.currencyName
            }
            div(classes = "rate") {
                +formatCurrency(props.rate)
            }
        }

    }
}

fun formatCurrency(amount:Number): String {
    return "€ ${amount.asDynamic().toFixed(2)}"
}

fun RBuilder.currencyTile(currencyName: String, rate: Number) = child(CurrencyTile::class) {
    attrs.currencyName = currencyName
    attrs.rate = rate
}



