package co.nimblehq.ic.kmm.suv.domain.usecase

import co.nimblehq.ic.kmm.suv.domain.model.Token
import co.nimblehq.ic.kmm.suv.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class LogInUseCase(private val repository: UserRepository) {

    operator fun invoke(email: String, password: String): Flow<Token> {
        return repository.logIn(email, password)
    }
}
