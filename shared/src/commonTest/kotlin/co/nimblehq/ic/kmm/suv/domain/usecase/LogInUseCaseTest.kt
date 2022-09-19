package co.nimblehq.ic.kmm.suv.domain.usecase

import co.nimblehq.ic.kmm.suv.domain.model.Token
import co.nimblehq.ic.kmm.suv.domain.repository.UserRepository
import io.mockative.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class LogInUseCaseTest {

    @Mock
    private val mockRepository = mock(classOf<UserRepository>())

    private val mockThrowable = Throwable("mock")
    private val mockToken = Token("", "", 0, "", 0)

    private lateinit var useCase: LogInUseCase

    @BeforeTest
    fun setUp() {
        useCase = LogInUseCase(mockRepository)
    }

    @Test
    fun when_logIn_is_called__the_repository_returns_token() = runTest {
        given(mockRepository)
            .function(mockRepository::logIn)
            .whenInvokedWith(any(), any())
            .thenReturn(
                flow {
                    emit(mockToken)
                }
            )

        useCase("dev@nimblehq.co", "123456").collect {
            assertEquals(it, mockToken)
        }
    }

    @Test
    fun when_login_is_called__the_repository_returns_error() = runTest {
        given(mockRepository)
            .function(mockRepository::logIn)
            .whenInvokedWith(any(), any())
            .thenReturn(
                flow {
                    throw mockThrowable
                }
            )

        useCase("dev@nimblehq.co", "123456").catch {
            assertEquals(mockThrowable.message, it.message)
        }.collect()
    }
}
