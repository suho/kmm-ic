package co.nimblehq.ic.kmm.suv.data.remote.model

import co.nimblehq.ic.kmm.suv.domain.model.Token
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TokenApiModel(
    @SerialName("id")
    val id: String,
    @SerialName("type")
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
) {
    constructor(token: Token) : this(
        "-",
        "-",
        token.accessToken,
        token.tokenType,
        token.expiresIn,
        token.refreshToken,
        token.createdAt
    )
}

fun TokenApiModel.toToken(): Token =
    Token(
        accessToken,
        tokenType,
        expiresIn,
        refreshToken,
        createdAt,
    )
