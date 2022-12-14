package co.nimblehq.ic.kmm.suv.data.repository

import co.nimblehq.ic.kmm.suv.data.local.datasource.TokenLocalDataSource
import co.nimblehq.ic.kmm.suv.data.remote.datasource.TokenRemoteDataSource
import co.nimblehq.ic.kmm.suv.data.remote.model.TokenApiModel
import co.nimblehq.ic.kmm.suv.data.remote.model.toToken
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
class TokenRepositoryTest {

    @Mock
    private val mockTokenRemoteDataSource = mock(classOf<TokenRemoteDataSource>())
    @Mock
    private val mockTokenLocalDataSource = mock(classOf<TokenLocalDataSource>())

    private val mockThrowable = Throwable("mock")
    private val mockToken = TokenApiModel(
        "id",
        "type",
        "accessToken",
        "tokenType",
        0,
        "refreshToken",
        0
    )

    private lateinit var repository: TokenRepository

    @BeforeTest
    fun setUp() {
        repository = TokenRepositoryImpl(mockTokenRemoteDataSource, mockTokenLocalDataSource)
    }

    @Test
    fun `when logIn is called - the data source returns token`() = runTest {
        given(mockTokenRemoteDataSource)
            .function(mockTokenRemoteDataSource::logIn)
            .whenInvokedWith(any())
            .thenReturn(
                flow {
                    emit(mockToken)
                }
            )

       repository.logIn("dev@nimblehq.co", "123456").collect {
           it shouldBe mockToken.toToken()
       }
    }

    @Test
    fun `when login is called - the data source returns error`() = runTest {
        given(mockTokenRemoteDataSource)
            .function(mockTokenRemoteDataSource::logIn)
            .whenInvokedWith(any())
            .thenReturn(
                flow {
                    throw mockThrowable
                }
            )

        repository.logIn("dev@nimblehq.co", "123456").catch {
            it.message shouldBe mockThrowable.message
        }.collect()
    }

    @Test
    fun `when save token is called - the token local data source invokes`() = runTest {
        repository.save(mockToken.toToken())
        verify(mockTokenLocalDataSource)
            .function(mockTokenLocalDataSource::save)
            .with(any())
            .wasInvoked(exactly = 1.time)
    }
}
