package org.example.fullstack.frontend.index

import kotlinext.js.invoke
import kotlinext.js.require
import org.example.fullstack.frontend.app.app
import react.dom.render
import kotlin.browser.document



fun main(args: Array<String>) {

    val baseModule = "org/example/fullstack/frontend/index"
    require("${baseModule}/index.css")
    require("${baseModule}/../app/App.css")
    require("${baseModule}/../logo/Logo.css")
    require("${baseModule}/index.html")

    render(document.getElementById("root")) {
        app()
    }
}
