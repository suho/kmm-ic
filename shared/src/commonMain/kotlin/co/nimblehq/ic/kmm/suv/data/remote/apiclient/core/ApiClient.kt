package co.nimblehq.ic.kmm.suv.data.remote.apiclient.core

import co.nimblehq.ic.kmm.suv.BuildKonfig
import co.nimblehq.ic.kmm.suv.data.local.datasource.TokenLocalDataSource
import co.nimblehq.ic.kmm.suv.data.remote.body.RefreshTokenApiBody
import co.nimblehq.ic.kmm.suv.data.remote.apiclient.builder.path
import co.nimblehq.ic.kmm.suv.data.remote.model.TokenApiModel
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
import io.ktor.client.plugins.logging.LogLevel.ALL
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.flow.*
import kotlinx.serialization.json.Json

class ApiClient(engine: HttpClientEngine, tokenLocalDataSource: TokenLocalDataSource? = null) {

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

            tokenLocalDataSource?.let {
                install(Auth) {
                    bearer {
                        loadTokens {
                            val token = it.getToken()
                            token?.let {
                                BearerTokens(it.accessToken, it.refreshToken)
                            }
                        }

                        sendWithoutRequest { request ->
                            request.url.host == Url(BuildKonfig.BASE_URL).host
                        }

                        refreshTokens {
                            val token = refreshToken(oldTokens?.refreshToken ?: "").last()
                            tokenLocalDataSource.save(token)
                            tokenLocalDataSource.getToken()?.let {
                                BearerTokens(it.accessToken, it.refreshToken)
                            }
                        }
                    }
                }
            }
        }
    }

    inline fun <reified T> body(builder: HttpRequestBuilder): Flow<T> {
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

    private fun refreshToken(token: String): Flow<TokenApiModel> {
        val body = RefreshTokenApiBody(refreshToken = token)
        return body(HttpRequestBuilder().apply {
            path("/v1/oauth/token")
            method = HttpMethod.Post
            setBody(body)
        })
    }
}
