package co.nimblehq.ic.kmm.suv.data.repository

import co.nimblehq.ic.kmm.suv.data.remote.datasource.UserRemoteDataSource
import co.nimblehq.ic.kmm.suv.data.remote.model.toUser
import co.nimblehq.ic.kmm.suv.domain.model.User
import co.nimblehq.ic.kmm.suv.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserRepositoryImpl(
    private val userRemoteDataSource: UserRemoteDataSource
): UserRepository {

    override fun getProfile(): Flow<User> {
        return userRemoteDataSource
            .getProfile()
            .map { it.toUser() }
    }
}
