package components.currencyChart

import app.CryptoStock
import kotlinx.html.id
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.canvas
import react.dom.div

interface CurrencyChartProps : RProps {
    var ethStocks: MutableList<CryptoStock>
    var ltcStocks: MutableList<CryptoStock>
}

fun MutableList<CryptoStock>.prices() = map { it -> it.price }

class CurrencyChart() : RComponent<CurrencyChartProps, RState>() {

    override fun RBuilder.render() {
        div(classes = "chart-container") {
            canvas {
                attrs.id = "cryptoChart"
                key = "cryptoChart"
            }
        }
    }

    override fun componentDidUpdate(prevProps: CurrencyChartProps, prevState: RState) {
        eval("""
            chart.data.datasets[0].data = ${props.ethStocks.prices()};
            chart.data.datasets[1].data = ${props.ltcStocks.prices()};
            chart.update(0);
            """)
    }

    override fun componentDidMount() {
        eval("""
            window.chart = new Chart(document.getElementById("cryptoChart"), {
                type: 'line',
                data: {
                    labels: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20],
                    datasets: [{
                        data: ${props.ethStocks.prices()},
                        label: "ETH",
                        lineTension: 0,
                        borderColor: "#3e95cd",
                        fill: false
                      }, {
                      data: ${props.ltcStocks.prices()},
                        label: "LTC",
                        lineTension: 0,
                        borderColor: "#EE0000",
                        fill: false
                      }
                    ]
                  },
              options: {
                title: {
                  display: true,
                  text: 'Cryptocurrency Stock Trend'
                }
              }
            });
    """)

    }
}

fun RBuilder.currencyChart(ethStocks: MutableList<CryptoStock>, ltcStocks: MutableList<CryptoStock>) = child(CurrencyChart::class) {
    attrs.ethStocks = ethStocks
    attrs.ltcStocks = ltcStocks
}
