package co.nimblehq.ic.kmm.suv.domain.usecase

import co.nimblehq.ic.kmm.suv.domain.model.User
import co.nimblehq.ic.kmm.suv.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

interface GetProfileUseCase {
    operator fun invoke(): Flow<User>
}

class GetProfileUseCaseImpl(
    private val userRepository: UserRepository
) : GetProfileUseCase {

    override operator fun invoke(): Flow<User> {
        return userRepository
            .getProfile()
    }
}
