package rest

import coroutines.await
import org.w3c.fetch.RequestInit
import org.w3c.fetch.Response
import kotlin.browser.window
import kotlin.js.Json
import kotlin.js.Promise

fun fetch(url: String, init: RequestInit.() -> Unit): Promise<Response> {
    val requestInit = object : RequestInit {}
    requestInit.init()
    return window.fetch(url, requestInit)
}

suspend fun <T> Response.fromJson(parse: Json.() -> T): T {
    val response = json().await() as Json
    return response.parse()
}
