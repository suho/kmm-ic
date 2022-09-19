package co.nimblehq.ic.kmm.suv.data.network.service

import co.nimblehq.ic.kmm.suv.data.model.ApiToken
import co.nimblehq.ic.kmm.suv.data.network.builder.UserRequestBuilder
import co.nimblehq.ic.kmm.suv.data.network.core.body
import io.ktor.client.*
import kotlinx.coroutines.flow.Flow

interface UserService {
    fun logIn(email: String, password: String): Flow<ApiToken>
}

class UserServiceImpl(private val httpClient: HttpClient) : UserService {

    override fun logIn(email: String, password: String): Flow<ApiToken> {
        return httpClient.body(UserRequestBuilder.logIn(email, password))
    }
}
