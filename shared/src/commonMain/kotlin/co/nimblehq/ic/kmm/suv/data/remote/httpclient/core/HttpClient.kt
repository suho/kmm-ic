package co.nimblehq.ic.kmm.suv.data.remote.httpclient.core

import co.nimblehq.ic.kmm.suv.domain.model.AppError
import co.nimblehq.jsonapi.json.JsonApi
import co.nimblehq.jsonapi.model.JsonApiException
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.LogLevel
import io.github.aakira.napier.Napier
import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.plugins.logging.LogLevel.ALL
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json

val json = Json {
    prettyPrint = true
    isLenient = true
    encodeDefaults = true
    ignoreUnknownKeys = true
}

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
        json(json)
    }
}.also {
    Napier.base(DebugAntilog())
}

inline fun <reified T> HttpClient.body(builder: HttpRequestBuilder) : Flow<T> {
    return flow {
        val body = request(
            builder.apply {
                contentType(ContentType.Application.Json)
            }
        ).bodyAsText()
        try {
            val data = JsonApi(json).decodeFromJsonApiString<T>(body)
            emit(data)
        } catch (e: JsonApiException) {
            val message = e.errors.first().detail
            throw AppError(message)
        }
    }
}
