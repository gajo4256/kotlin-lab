package components.currencyChart

import kotlinx.html.id
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.canvas
import react.dom.div

interface CurrencyChartProps : RProps {
    var rateListProp: List<Double>
}

interface CurrencyChartState : RState {
    var rateListState: MutableList<Double>
}

class CurrencyChart(props: CurrencyChartProps) : RComponent<CurrencyChartProps, CurrencyChartState>(props) {

    override fun CurrencyChartState.init(props: CurrencyChartProps) {
        rateListState = props.rateListProp as MutableList<Double>
    }

    override fun RBuilder.render() {
        div(classes = "chart-container") {

            canvas {
                attrs.id = "cryptoChart"
                key = "cryptoChart"
            }
        }
    }

    override fun componentDidUpdate(prevProps: CurrencyChartProps, prevState: CurrencyChartState) {
        eval("""
            chart.data.datasets[0].data = ${state.rateListState};
            chart.update(0);
            """)
    }

    override fun componentDidMount() {


        eval("""
            window.chart = new Chart(document.getElementById("cryptoChart"), {
                type: 'line',
                data: {
                    labels: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10],
                    datasets: [{
                        data: ${state.rateListState},
                        label: "#currentlySelectedCrypto",
                        borderColor: "#3e95cd",
                        fill: false
                      }
                    ]
                  },
              options: {
                title: {
                  display: true,
                  text: '#insertCurrency Price Chart Euro (#currency/EUR)'
                }
              }
            });
    """)

    }
}

fun RBuilder.currencyChart(cryptoDataList: List<Double>) = child(CurrencyChart::class) {
    attrs.rateListProp = cryptoDataList
}
