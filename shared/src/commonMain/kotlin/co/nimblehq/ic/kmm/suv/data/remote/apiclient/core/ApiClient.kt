package co.nimblehq.ic.kmm.suv.data.remote.apiclient.core

import co.nimblehq.ic.kmm.suv.BuildKonfig
import co.nimblehq.ic.kmm.suv.data.local.datasource.TokenLocalDataSource
import co.nimblehq.ic.kmm.suv.data.remote.body.RefreshTokenApiBody
import co.nimblehq.ic.kmm.suv.data.remote.datasource.TokenRemoteDataSource
import co.nimblehq.ic.kmm.suv.domain.model.AppError
import co.nimblehq.jsonapi.json.JsonApi
import co.nimblehq.jsonapi.model.JsonApiException
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.LogLevel
import io.github.aakira.napier.Napier
import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.plugins.logging.LogLevel.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.last
import kotlinx.serialization.json.Json

class ApiClient(
    engine: HttpClientEngine,
    tokenLocalDataSource: TokenLocalDataSource? = null,
    tokenRemoteDataSource: TokenRemoteDataSource? = null
) {

    val httpClient: HttpClient
    val json = Json {
        prettyPrint = true
        isLenient = true
        encodeDefaults = true
        ignoreUnknownKeys = true
    }

    private val tokenLocalDataSource: TokenLocalDataSource?

    init {
        this.tokenLocalDataSource = tokenLocalDataSource
        Napier.takeLogarithm()
        Napier.base(DebugAntilog())
        httpClient = HttpClient(engine = engine) {
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

            if (tokenLocalDataSource != null && tokenRemoteDataSource != null) {
                install(Auth) {
                    bearer {
                        loadTokens {
                            tokenLocalDataSource.getToken()?.run {
                                BearerTokens(accessToken, refreshToken)
                            }
                        }

                        sendWithoutRequest { request ->
                            request.url.host == Url(BuildKonfig.BASE_URL).host
                        }

                        refreshTokens {
                            tokenRemoteDataSource
                                .refreshToken(
                                    RefreshTokenApiBody(
                                        refreshToken = oldTokens?.refreshToken ?: ""
                                    )
                                )
                                .last()
                                .run {
                                    tokenLocalDataSource.save(this)
                                    BearerTokens(accessToken, refreshToken)
                                }
                        }
                    }
                }
            }
        }
    }

    inline fun <reified T> responseBody(builder: HttpRequestBuilder): Flow<T> {
        return flow {
            val body = httpClient.request(
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

    fun emptyResponseBody(builder: HttpRequestBuilder): Flow<Unit> {
        return flow {
            val body = httpClient.request(
                builder.apply {
                    contentType(ContentType.Application.Json)
                }
            ).bodyAsText()
            try {
                if (body != "{}") JsonApi(json).decodeFromJsonApiString<Unit>(body)
                emit(Unit)
            } catch (e: JsonApiException) {
                val message = e.errors.first().detail
                throw AppError(message)
            }
        }
    }
}
