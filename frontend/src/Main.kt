import kotlinext.js.invoke
import kotlinext.js.require
import app.app
import react.dom.render
import kotlin.browser.document

@JsModule("favicon.ico")
external val favicon: dynamic

fun main(args: Array<String>) {

    require("index.css")
    require("index.html")
    require("manifest.json")

    require("app/App.css")
    require("logo/Logo.css")

    render(document.getElementById("root")) {
        app()
    }
}
