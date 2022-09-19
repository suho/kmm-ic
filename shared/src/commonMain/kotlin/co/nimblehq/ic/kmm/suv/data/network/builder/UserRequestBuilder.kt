package co.nimblehq.ic.kmm.suv.data.network.builder

import co.nimblehq.ic.kmm.suv.BuildKonfig
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

class UserRequestBuilder {

    @Serializable
    data class LoginInput(
        @SerialName("grant_type")
        val grantType: String,
        val email: String,
        val password: String,
        @SerialName("client_id")
        val clientId: String,
        @SerialName("client_secret")
        val clientSecret: String,
    )

    companion object Factory {
        fun logIn(email: String, password: String): HttpRequestBuilder {
            val builder = HttpRequestBuilder()
            builder.path("/v1/oauth/token")
            builder.method = HttpMethod.Post
            builder.contentType(ContentType.Application.Json)
            builder.setBody(
                LoginInput(
                    "password",
                    email,
                    password,
                    BuildKonfig.CLIENT_ID,
                    BuildKonfig.CLIENT_SECRET
                )
            )
            return builder
        }
    }
}
