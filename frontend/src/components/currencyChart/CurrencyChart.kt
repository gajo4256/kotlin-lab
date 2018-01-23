package components.currencyChart

import app.CryptoData
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import kotlinx.html.id
import react.*
import react.dom.canvas
import react.dom.div
import kotlin.js.Date
import kotlin.js.Math

interface CurrencyChartProps : RProps {
    var cryptoDataListProps: List<CryptoData>
}

interface CurrencyChartState : RState {
    var cryptoDataListState: MutableList<CryptoData>
    var rateListState: MutableList<Number>
}

class CurrencyChart(props: CurrencyChartProps) : RComponent<CurrencyChartProps, CurrencyChartState>(props) {

    override fun CurrencyChartState.init(props: CurrencyChartProps) {
        cryptoDataListState = mutableListOf()
        rateListState = mutableListOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0)

    }

    override fun RBuilder.render() {
        div(classes = "chart-container") {

            canvas {
                attrs.id = "cryptoChart"
                key = "cryptoChart"
            }
        }
    }

    override fun componentDidMount() {
        launch {
            while (true) {
                var newList: MutableList<Number> = state.rateListState
                newList.removeAt(0)
                newList.add(Math.random() * 2314)
                setState {
                    cryptoDataListState.add(CryptoData("BTC", Date(), Math.random() * 3004))
                    rateListState = newList
                }

                eval("""
                    chart.data.datasets[0].data = ${state.rateListState};

                     chart.update(0);
                     console.log("Updating chart");
                """)

                console.log("RateList: " + state.rateListState.toString())
                delay(1000)
            }
        }

        eval("""
            window.chart = new Chart(document.getElementById("cryptoChart"), {
                type: 'line',
                data: {
                    labels: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10],
                    datasets: [{
                        data: ${state.rateListState},
                        label: "Africa",
                        borderColor: "#3e95cd",
                        fill: false
                      }
                    ]
                  },
              options: {
                title: {
                  display: true,
                  text: 'World population per region (in millions)'
                }
              }
            });
    """)

    }
}

fun RBuilder.currencyChart(cryptoDataList: List<CryptoData>) = child(CurrencyChart::class) {
    attrs.cryptoDataListProps = cryptoDataList
}
