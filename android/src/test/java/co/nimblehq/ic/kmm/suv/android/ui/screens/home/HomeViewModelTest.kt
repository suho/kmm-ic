package co.nimblehq.ic.kmm.suv.android.ui.screens.home

import co.nimblehq.ic.kmm.suv.android.rule.MainCoroutinesRule
import co.nimblehq.ic.kmm.suv.domain.model.AppError
import co.nimblehq.ic.kmm.suv.domain.model.User
import co.nimblehq.ic.kmm.suv.domain.usecase.GetProfileUseCase
import co.nimblehq.ic.kmm.suv.helper.date.DateTime
import co.nimblehq.ic.kmm.suv.helper.date.DateTimeFormatterImpl
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.datetime.LocalDate
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    private lateinit var viewModel: HomeViewModel

    private val mockGetProfileUseCase: GetProfileUseCase = mockk()
    private val mockDateTime: DateTime = mockk()
    private val mockUser = User("email", "name", "avatarUrl")

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutinesRule = MainCoroutinesRule()

    @Before
    fun setup() {
        every { mockDateTime.today() } returns LocalDate(2022, 11, 8)
        every { mockGetProfileUseCase() } returns flowOf(mockUser)
        viewModel = HomeViewModel(mockGetProfileUseCase, mockDateTime, DateTimeFormatterImpl())
    }

    @Test
    fun `When its init, today should be right`() {
        viewModel.currentDate.value shouldBe "TUESDAY, NOVEMBER 8"
    }

    @Test
    fun `When load profile successfully, the avatar url should not be null`() = runTest {
        viewModel.loadProfile()
        advanceUntilIdle()

        viewModel.avatarUrlString.value shouldBe mockUser.avatarUrl
    }

    @Test
    fun `When load profile failed, error message should not be null`() = runTest {
        val expectedError = AppError("Load profile failed!")
        every { mockGetProfileUseCase() } returns flow { throw expectedError }
        viewModel.loadProfile()
        advanceUntilIdle()

        viewModel.errorMessage.value shouldBe expectedError.message
    }

    @Test
    fun `When load profile, isLoading should change from false to true`() = runTest {
        Dispatchers.setMain(StandardTestDispatcher())
        viewModel.loadProfile()

        viewModel.isLoading.value shouldBe true
        advanceUntilIdle()
        viewModel.isLoading.value shouldBe false
    }
}
