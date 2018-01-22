package components.currencyTile

import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.div
import react.dom.h2
import react.dom.p

class CurrencyTileProps(var currencyName: String, var rate: Int) : RProps

class CurrencyTile() : RComponent<CurrencyTileProps, RState>() {
    override fun RBuilder.render() {
        div() {
            key = "currencyTile"
            h2() {
                +props.currencyName
            }
            p() {
                + "€ "
                +props.rate.toString()
            }
        }

    }
}

fun RBuilder.currencyTile(currencyName: String, rate: Int) = child(CurrencyTile::class) {
    attrs.currencyName = currencyName
    attrs.rate = rate
}



