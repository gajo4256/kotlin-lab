package components.currencyTile

import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.div

interface CurrencyTileProps : RProps {
    var currencyName: String
    var rate: Int
}

class CurrencyTile() : RComponent<CurrencyTileProps, RState>() {
    override fun RBuilder.render() {
        div(classes = "currency-tile") {
            key = "currencyTile"
            div(classes = "currency-title") {
                +props.currencyName
            }
            div(classes = "rate") {
                + "€ "
                +props.rate.toString()
            }
        }

    }
}

fun formatCurrency(amount: Int) {

}

fun RBuilder.currencyTile(currencyName: String, rate: Int) = child(CurrencyTile::class) {
    attrs.currencyName = currencyName
    attrs.rate = rate
}



