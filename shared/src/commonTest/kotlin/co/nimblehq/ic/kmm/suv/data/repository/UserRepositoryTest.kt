package co.nimblehq.ic.kmm.suv.data.repository

import co.nimblehq.ic.kmm.suv.data.remote.datasource.UserRemoteDataSource
import co.nimblehq.ic.kmm.suv.data.remote.model.UserApiModel
import co.nimblehq.ic.kmm.suv.data.remote.model.toUser
import co.nimblehq.ic.kmm.suv.domain.repository.UserRepository
import io.kotest.matchers.shouldBe
import io.mockative.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test

@ExperimentalCoroutinesApi
class UserRepositoryTest {

    @Mock
    private val mockUserRemoteDataSource = mock(classOf<UserRemoteDataSource>())
    private val mockThrowable = Throwable("mock")
    private val mockUser = UserApiModel(
        "id",
        "type",
        "dev@nimblehq.co",
        "Nimble Team",
        "https://www.example.com/image.png"
    )

    private lateinit var repository: UserRepository

    @BeforeTest
    fun setUp() {
        repository = UserRepositoryImpl(mockUserRemoteDataSource)
    }

    @Test
    fun `when getProfile is called - the data source returns user`() = runTest {
        given(mockUserRemoteDataSource)
            .function(mockUserRemoteDataSource::getProfile)
            .whenInvoked()
            .thenReturn(
                flow {
                    emit(mockUser)
                }
            )

        repository.getProfile().collect {
            it shouldBe mockUser.toUser()
        }
    }

    @Test
    fun `when getProfile is called - the data source returns error`() = runTest {
        given(mockUserRemoteDataSource)
            .function(mockUserRemoteDataSource::getProfile)
            .whenInvoked()
            .thenReturn(
                flow {
                    throw mockThrowable
                }
            )

        repository.getProfile().catch {
            it.message shouldBe mockThrowable.message
        }.collect()
    }
}
