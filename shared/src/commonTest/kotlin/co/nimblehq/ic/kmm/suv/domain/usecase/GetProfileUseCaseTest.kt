package co.nimblehq.ic.kmm.suv.domain.usecase

import co.nimblehq.ic.kmm.suv.domain.model.User
import co.nimblehq.ic.kmm.suv.domain.repository.UserRepository
import io.kotest.matchers.shouldBe
import io.mockative.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test

@ExperimentalCoroutinesApi
class GetProfileUseCaseTest {

    @Mock
    private val mockUserRepository = mock(classOf<UserRepository>())

    private val mockThrowable = Throwable("mock")
    private val dummyUser = User("email", "name", "avatarUrl")

    private lateinit var useCase: GetProfileUseCase

    @BeforeTest
    fun setUp() {
        useCase = GetProfileUseCaseImpl(mockUserRepository)
    }

    @Test
    fun `when invoke is called - the repositories return user and today`() = runTest {
        given(mockUserRepository)
            .function(mockUserRepository::getProfile)
            .whenInvoked()
            .thenReturn(flow { emit(dummyUser) })

        useCase().collect { it shouldBe dummyUser }
    }

    @Test
    fun `when getProfile is called - the repository returns error`() = runTest {
        given(mockUserRepository)
            .function(mockUserRepository::getProfile)
            .whenInvoked()
            .thenReturn(flow { throw mockThrowable })

        useCase().catch { it.message shouldBe mockThrowable.message }
    }
}
