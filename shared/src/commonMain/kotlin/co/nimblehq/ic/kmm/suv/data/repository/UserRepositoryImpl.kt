package co.nimblehq.ic.kmm.suv.data.repository

import co.nimblehq.ic.kmm.suv.data.model.toToken
import co.nimblehq.ic.kmm.suv.data.network.service.UserService
import co.nimblehq.ic.kmm.suv.domain.model.Token
import co.nimblehq.ic.kmm.suv.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserRepositoryImpl(private val userService: UserService): UserRepository {

    override fun logIn(email: String, password: String): Flow<Token> {
        return userService.logIn(email, password).map { it.toToken() }
    }
}
