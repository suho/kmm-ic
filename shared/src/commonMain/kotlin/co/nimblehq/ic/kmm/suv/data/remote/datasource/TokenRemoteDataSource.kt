package co.nimblehq.ic.kmm.suv.data.remote.datasource

import co.nimblehq.ic.kmm.suv.data.remote.body.LoginApiBody
import co.nimblehq.ic.kmm.suv.data.remote.httpclient.builder.path
import co.nimblehq.ic.kmm.suv.data.remote.httpclient.core.body
import co.nimblehq.ic.kmm.suv.data.remote.model.TokenApiModel
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.flow.Flow

interface TokenRemoteDataSource {
    fun logIn(body: LoginApiBody): Flow<TokenApiModel>
}

class TokenRemoteDataSourceImpl(private val httpClient: HttpClient): TokenRemoteDataSource {

    override fun logIn(body: LoginApiBody): Flow<TokenApiModel> {
        return httpClient.body(
            HttpRequestBuilder().apply {
                path("/v1/oauth/token")
                method = HttpMethod.Post
                contentType(ContentType.Application.Json)
                setBody(body)
            }
        )
    }
}
