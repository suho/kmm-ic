package co.nimblehq.ic.kmm.suv.data.model

import co.nimblehq.ic.kmm.suv.domain.model.Token
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// TODO: Find a JsonApi to support this

@Serializable
data class ApiToken(
    @SerialName("data")
    val data: Data
) {
    @Serializable
    data class Data(
        @SerialName("id")
        val id: String,
        @SerialName("type")
        val type: String,
        @SerialName("attributes")
        val attributes: Attributes
    )

    @Serializable
    data class Attributes(
        @SerialName("access_token")
        val accessToken: String,
        @SerialName("token_type")
        val tokenType: String,
        @SerialName("expires_in")
        val expiresIn: Int,
        @SerialName("refresh_token")
        val refreshToken: String,
        @SerialName("created_at")
        val createdAt: Int
    )
}

fun ApiToken.toToken(): Token {
    return Token(
        data.attributes.accessToken,
        data.attributes.tokenType,
        data.attributes.expiresIn,
        data.attributes.refreshToken,
        data.attributes.createdAt,
    )
}
