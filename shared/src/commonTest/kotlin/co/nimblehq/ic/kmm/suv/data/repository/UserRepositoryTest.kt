package co.nimblehq.ic.kmm.suv.data.repository

import co.nimblehq.ic.kmm.suv.data.model.ApiToken
import co.nimblehq.ic.kmm.suv.data.model.toToken
import co.nimblehq.ic.kmm.suv.data.network.service.UserService
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
class UserRepositoryTest {

    @Mock
    private val mockUserService =mock(classOf<UserService>())

    private val mockThrowable = Throwable("mock")
    private val mockToken = ApiToken(
        "id",
        "type",
        "accessToken",
        "tokenType",
        0,
        "refreshToken",
        0
    )

    private lateinit var repository: UserRepository

    @BeforeTest
    fun setUp() {
        repository = UserRepositoryImpl(mockUserService)
    }

    @Test
    fun when_logIn_is_called__the_service_returns_token() = runTest {
        given(mockUserService)
            .function(mockUserService::logIn)
            .whenInvokedWith(any(), any())
            .thenReturn(
                flow {
                    emit(mockToken)
                }
            )

       repository.logIn("dev@nimblehq.co", "123456").collect {
           assertEquals(it, mockToken.toToken())
       }
    }

    @Test
    fun when_login_is_called__the_service_returns_error() = runTest {
        given(mockUserService)
            .function(mockUserService::logIn)
            .whenInvokedWith(any(), any())
            .thenReturn(
                flow {
                    throw mockThrowable
                }
            )

        repository.logIn("dev@nimblehq.co", "123456").catch {
            assertEquals(mockThrowable.message, it.message)
        }.collect()
    }
}
