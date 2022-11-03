package co.nimblehq.ic.kmm.suv.domain.usecase

import co.nimblehq.ic.kmm.suv.domain.model.Token
import co.nimblehq.ic.kmm.suv.domain.repository.TokenRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach

interface LogInUseCase {
    operator fun invoke(email: String, password: String): Flow<Token>
}

class LogInUseCaseImpl(private val repository: TokenRepository): LogInUseCase {

    override operator fun invoke(email: String, password: String): Flow<Token> {
        return repository
            .logIn(email, password)
            .onEach { token -> repository.save(token) }
    }
}
