package co.nimblehq.ic.kmm.suv.data.remote.model

import co.nimblehq.ic.kmm.suv.domain.model.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserApiModel(
    val id: String,
    val type: String,
    val email: String,
    val name: String,
    @SerialName("avatar_url")
    val avatarUrl: String
)

fun UserApiModel.toUser() = User(
    email,
    name,
    avatarUrl
)
