package co.nimblehq.ic.kmm.suv.data.model

import co.nimblehq.ic.kmm.suv.domain.model.Token
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiToken(
    val id: String,
    val type: String,
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

fun ApiToken.toToken(): Token {
    return Token(
        accessToken,
        tokenType,
        expiresIn,
        refreshToken,
        createdAt,
    )
}