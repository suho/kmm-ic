package co.nimblehq.ic.kmm.suv.data.remote.httpclient.builder

import co.nimblehq.ic.kmm.suv.BuildKonfig
import io.ktor.client.request.*

fun HttpRequestBuilder.path(path: String) {
    url(BuildKonfig.BASE_URL.plus(path))
}
