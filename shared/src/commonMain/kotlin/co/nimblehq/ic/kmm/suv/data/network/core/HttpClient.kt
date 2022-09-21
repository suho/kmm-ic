package co.nimblehq.ic.kmm.suv.data.network.core

import co.nimblehq.jsonapi.json.JsonApi
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.LogLevel
import io.github.aakira.napier.Napier
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json

val json = Json {
    prettyPrint = true
    isLenient = true
    ignoreUnknownKeys = true
}

fun provideHttpClient(engine: HttpClientEngine) = HttpClient(engine = engine) {
    install(Logging) {
        level = io.ktor.client.plugins.logging.LogLevel.ALL
        logger = object : Logger {
            override fun log(message: String) {
                Napier.log(LogLevel.DEBUG, message = message)
            }
        }
    }

    install(ContentNegotiation) {
        json(json)
    }
}.also {
    Napier.base(DebugAntilog())
}

inline fun <reified T> HttpClient.body(builder: HttpRequestBuilder) : Flow<T> {
    return flow {
        val body = request(builder).bodyAsText()
        val data = JsonApi(json).decodeFromJsonApiString<T>(body)
        emit(data)
    }
}