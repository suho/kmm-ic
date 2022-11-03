package co.nimblehq.ic.kmm.suv.domain.model

data class Token(
    val accessToken: String,
    val tokenType: String,
    val expiresIn: Int,
    val refreshToken: String,
    val createdAt: Int
)
