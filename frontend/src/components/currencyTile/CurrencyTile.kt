package components.currencyTile

import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.div
import react.dom.strong

interface CurrencyTileProps : RProps {
    var currencyName: String
    var price: Double
    var cssClass: String
}

interface CurrencyTileState : RState {

}

const val PRICE_NOT_SET: Double = -1.0

class CurrencyTile(props: CurrencyTileProps) : RComponent<CurrencyTileProps, CurrencyTileState>(props) {

    var minPrice: Double = PRICE_NOT_SET
    var maxPrice: Double = PRICE_NOT_SET

    override fun CurrencyTileState.init(props: CurrencyTileProps) {
        minPrice = PRICE_NOT_SET;
        maxPrice = PRICE_NOT_SET;
    }

    override fun RBuilder.render() {
        div(classes = "currency-tile ${props.cssClass}") {
            key = "currencyTile"
            div(classes = "currency-title") {
                +props.currencyName
            }
            div(classes = "prices") {
                div(classes = "price") {
                    +formatCurrency(props.price)
                }
                div {
                    +"min: € "
                    strong(classes="odometer") {
                        +if (minPrice == PRICE_NOT_SET) "-" else minPrice.toString()
                    }
                }
                div {
                    +"max: € "
                    strong(classes="odometer") {
                        +if (maxPrice == PRICE_NOT_SET) "-" else maxPrice.toString()
                    }
                }
            }
        }
    }

    override fun componentDidUpdate(prevProps: CurrencyTileProps, prevState: CurrencyTileState) {
        var newPrice = props.price
        minPrice = if (minPrice == PRICE_NOT_SET) newPrice else minOf(newPrice, minPrice)
        maxPrice = if (maxPrice == PRICE_NOT_SET) newPrice else maxOf(newPrice, maxPrice)
    }
}

fun formatCurrency(amount: Double): String {
    return "€ ${amount.asDynamic().toFixed(2)}"
}

fun RBuilder.currencyTile(currencyName: String, rate: Double, cssClass: String) = child(CurrencyTile::class) {
    attrs.currencyName = currencyName
    attrs.price = rate
    attrs.cssClass = cssClass
}



