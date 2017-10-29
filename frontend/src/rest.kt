package rest

import coroutines.await
import org.w3c.fetch.*
import kotlin.browser.window
import kotlin.js.Json
import kotlin.js.Promise



suspend fun get(url: String, init: RequestInit.() -> Unit): Response {
    val request = HttpRequest.Get(url)
    val requestInit = request.requestInit(init)
    requestInit.method = HttpRequest.Get.METHOD

    return request.fetch(requestInit).await()
}

sealed class HttpRequest(val url: String) {

    fun fetch(requestInit: RequestInit): Promise<Response> = window.fetch(url, requestInit)

    class Get(url: String): HttpRequest(url) {
        companion object {
            val METHOD: String = "GET"
        }
    }
}

suspend fun <T> Response.fromJson(parse: Json.() -> T): T {
    val response = json().await() as Json
    return response.parse()
}

fun HttpRequest.requestInit(init: RequestInit.() -> Unit): RequestInit {
    val requestInit = object : RequestInit {}
    requestInit.init()
    return requestInit
}
