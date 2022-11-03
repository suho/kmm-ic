package co.nimblehq.ic.kmm.suv.domain.usecase

import co.nimblehq.ic.kmm.suv.domain.model.Token
import co.nimblehq.ic.kmm.suv.domain.repository.TokenRepository
import io.kotest.matchers.shouldBe
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
    private val mockRepository = mock(classOf<TokenRepository>())

    private val mockThrowable = Throwable("mock")
    private val mockToken = Token("", "", 0, "", 0)

    private lateinit var useCase: LogInUseCase

    @BeforeTest
    fun setUp() {
        useCase = LogInUseCaseImpl(mockRepository)
    }

    @Test
    fun `when logIn is called - the repository saves and returns token`() = runTest {
        given(mockRepository)
            .function(mockRepository::logIn)
            .whenInvokedWith(any(), any())
            .thenReturn(
                flow {
                    emit(mockToken)
                }
            )

        useCase("dev@nimblehq.co", "123456").collect {
            it shouldBe mockToken
        }

        verify(mockRepository)
            .function(mockRepository::save)
            .with(any())
            .wasInvoked(exactly = 1.time)
    }

    @Test
    fun `when login is called - the repository returns error`() = runTest {
        given(mockRepository)
            .function(mockRepository::logIn)
            .whenInvokedWith(any(), any())
            .thenReturn(
                flow {
                    throw mockThrowable
                }
            )

        useCase("dev@nimblehq.co", "123456").catch {
            it.message shouldBe mockThrowable.message
        }.collect()
    }
}
