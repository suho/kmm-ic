package co.nimblehq.ic.kmm.suv.data.remote.datasource

import co.nimblehq.ic.kmm.suv.data.remote.apiclient.builder.path
import co.nimblehq.ic.kmm.suv.data.remote.apiclient.core.ApiClient
import co.nimblehq.ic.kmm.suv.data.remote.model.UserApiModel
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.flow.Flow

interface UserRemoteDataSource {
    fun getProfile(): Flow<UserApiModel>
}

class UserRemoteDataSourceImpl(private val apiClient: ApiClient): UserRemoteDataSource {

    override fun getProfile(): Flow<UserApiModel> {
        return apiClient.body(
            HttpRequestBuilder().apply {
                path("/v1/me")
                method = HttpMethod.Get
            }
        )
    }
}
