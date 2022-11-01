package co.nimblehq.ic.kmm.suv.data.repository

import co.nimblehq.ic.kmm.suv.data.local.datasource.TokenLocalDataSource
import co.nimblehq.ic.kmm.suv.data.remote.body.LoginApiBody
import co.nimblehq.ic.kmm.suv.data.remote.datasource.TokenRemoteDataSource
import co.nimblehq.ic.kmm.suv.data.remote.model.TokenApiModel
import co.nimblehq.ic.kmm.suv.data.remote.model.toToken
import co.nimblehq.ic.kmm.suv.domain.model.Token
import co.nimblehq.ic.kmm.suv.domain.repository.TokenRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TokenRepositoryImpl(
    private val tokenRemoteDataSource: TokenRemoteDataSource,
    private val tokenLocalDataSource: TokenLocalDataSource
    ): TokenRepository {

    override fun logIn(email: String, password: String): Flow<Token> {
        return tokenRemoteDataSource
            .logIn(LoginApiBody(email = email, password = password))
            .map { it.toToken() }
    }

    override fun save(token: Token) {
        tokenLocalDataSource.save(TokenApiModel(token))
    }
}
