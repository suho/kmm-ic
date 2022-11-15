package co.nimblehq.ic.kmm.suv.data.remote.apiclient.builder

import co.nimblehq.ic.kmm.suv.BuildKonfig
import io.ktor.client.request.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement
import kotlinx.serialization.json.jsonObject

fun HttpRequestBuilder.path(path: String) {
    url(BuildKonfig.BASE_URL.plus(path))
}

inline fun <reified T> HttpRequestBuilder.setQueryParameters(parameters: T) {
    val queryParameters = Json.encodeToJsonElement(parameters).jsonObject
    for ((key, value) in queryParameters) {
        parameter(key, value)
    }
}
