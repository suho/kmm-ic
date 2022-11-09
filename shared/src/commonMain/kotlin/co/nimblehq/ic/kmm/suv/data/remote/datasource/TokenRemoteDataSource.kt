package co.nimblehq.ic.kmm.suv.data.remote.datasource

import co.nimblehq.ic.kmm.suv.data.remote.body.LoginApiBody
import co.nimblehq.ic.kmm.suv.data.remote.apiclient.builder.path
import co.nimblehq.ic.kmm.suv.data.remote.apiclient.core.ApiClient
import co.nimblehq.ic.kmm.suv.data.remote.body.RefreshTokenApiBody
import co.nimblehq.ic.kmm.suv.data.remote.model.TokenApiModel
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.flow.Flow

interface TokenRemoteDataSource {
    fun logIn(body: LoginApiBody): Flow<TokenApiModel>
    fun refreshToken(body: RefreshTokenApiBody): Flow<TokenApiModel>
}

class TokenRemoteDataSourceImpl(private val apiClient: ApiClient): TokenRemoteDataSource {

    override fun logIn(body: LoginApiBody): Flow<TokenApiModel> {
        return apiClient.body(
            HttpRequestBuilder().apply {
                path("/v1/oauth/token")
                method = HttpMethod.Post
                setBody(body)
            }
        )
    }

    override fun refreshToken(body: RefreshTokenApiBody): Flow<TokenApiModel> {
        return apiClient.body(
            HttpRequestBuilder().apply {
                path("/v1/oauth/token")
                method = HttpMethod.Post
                setBody(body)
            }
        )
    }
}
