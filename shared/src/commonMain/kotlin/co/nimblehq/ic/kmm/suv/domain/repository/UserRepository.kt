package co.nimblehq.ic.kmm.suv.domain.repository

import co.nimblehq.ic.kmm.suv.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getProfile(): Flow<User>
}
