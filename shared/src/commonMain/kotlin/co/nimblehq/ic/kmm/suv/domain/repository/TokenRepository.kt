package co.nimblehq.ic.kmm.suv.domain.repository

import co.nimblehq.ic.kmm.suv.domain.model.Token
import kotlinx.coroutines.flow.Flow

interface TokenRepository {
    fun logIn(email: String,  password: String): Flow<Token>
}
