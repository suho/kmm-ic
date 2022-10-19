package co.nimblehq.ic.kmm.suv.data.remote.httpclient.core

import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.LogLevel
import io.github.aakira.napier.Napier
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.plugins.logging.LogLevel.ALL
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json

fun provideHttpClient(engine: HttpClientEngine) = HttpClient(engine = engine) {
    install(Logging) {
        level = ALL
        logger = object : Logger {
            override fun log(message: String) {
                Napier.log(LogLevel.DEBUG, message = message)
            }
        }
    }

    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
            encodeDefaults = true
        })
    }
}.also {
    Napier.base(DebugAntilog())
}

inline fun <reified T> HttpClient.body(builder: HttpRequestBuilder) : Flow<T> {
    return flow {
        val data = request(
            builder.apply {
                contentType(ContentType.Application.Json)
            }
        ).body<T>()
        emit(data)
    }
}
