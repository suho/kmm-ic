package co.nimblehq.ic.kmm.suv.domain.usecase

import co.nimblehq.ic.kmm.suv.domain.model.User
import co.nimblehq.ic.kmm.suv.domain.repository.UserRepository
import io.kotest.matchers.shouldBe
import io.mockative.Mock
import io.mockative.classOf
import io.mockative.given
import io.mockative.mock
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
    fun `when the use case is succeeded - it returns user`() = runTest {
        given(mockUserRepository)
            .function(mockUserRepository::getProfile)
            .whenInvoked()
            .thenReturn(flow { emit(dummyUser) })

        useCase().collect { it shouldBe dummyUser }
    }

    @Test
    fun `when the use case is failed - it returns error`() = runTest {
        given(mockUserRepository)
            .function(mockUserRepository::getProfile)
            .whenInvoked()
            .thenReturn(flow { throw mockThrowable })

        useCase().catch { it.message shouldBe mockThrowable.message }
    }
}
