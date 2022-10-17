package co.nimblehq.ic.kmm.suv.data.remote.body

import co.nimblehq.ic.kmm.suv.BuildKonfig
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

private const val GRANT_TYPE_PASSWORD = "password"

@Serializable
data class LoginApiBody(
    @SerialName("grant_type")
    val grantType: String = GRANT_TYPE_PASSWORD,
    val email: String,
    val password: String,
    @SerialName("client_id")
    val clientId: String = BuildKonfig.CLIENT_ID,
    @SerialName("client_secret")
    val clientSecret: String = BuildKonfig.CLIENT_SECRET,
)
