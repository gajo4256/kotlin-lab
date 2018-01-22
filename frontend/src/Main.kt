import app.app
import kotlinext.js.invoke
import kotlinext.js.require
import react.dom.render
import kotlin.browser.document

fun main(args: Array<String>) {

    require("index.css")
    require("index.html")
    require("favicon.ico")
    require("app/App.css")

    render(document.getElementById("root")) {
        app()
    }
}
