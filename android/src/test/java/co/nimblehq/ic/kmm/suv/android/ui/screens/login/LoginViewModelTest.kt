package co.nimblehq.ic.kmm.suv.android.ui.screens.login

import co.nimblehq.ic.kmm.suv.android.rule.MainCoroutinesRule
import co.nimblehq.ic.kmm.suv.domain.model.AppError
import co.nimblehq.ic.kmm.suv.domain.usecase.LogInUseCase
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
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class LoginViewModelTest {

    private lateinit var loginViewModel: LoginViewModel

    private val mockLogInUseCase: LogInUseCase = mockk()

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutinesRule = MainCoroutinesRule()

    @Test
    fun `When log in successfully, isLogInSuccess should be true`() = runTest {
        every { mockLogInUseCase(any(), any()) } returns flowOf(mockk())
        loginViewModel = LoginViewModel(mockLogInUseCase)
        loginViewModel.logIn()
        advanceUntilIdle()

        loginViewModel.isLoginSuccess.value shouldBe true
    }

    @Test
    fun `When log in failed, errorMessage should not be null`() = runTest {
        val expectedError = AppError("Log in Failed")
        every { mockLogInUseCase(any(), any()) } returns flow { throw expectedError }
        loginViewModel = LoginViewModel(mockLogInUseCase)
        loginViewModel.logIn()
        advanceUntilIdle()

        loginViewModel.errorMessage.value shouldBe expectedError.message
    }

    @Test
    fun `When log in, isLoading should change from false to true`() = runTest {
        Dispatchers.setMain(StandardTestDispatcher()) // Do not run coroutines eagerly
        every { mockLogInUseCase(any(), any()) } returns flowOf(mockk())
        loginViewModel = LoginViewModel(mockLogInUseCase)
        loginViewModel.logIn()

        loginViewModel.isLoading.value shouldBe true
        advanceUntilIdle()
        loginViewModel.isLoading.value shouldBe false
    }

    @Test
    fun `When update email, the email should be changed`() = runTest {
        val expectedEmail = "new@nimblehq.co"
        loginViewModel = LoginViewModel(mockLogInUseCase)
        loginViewModel.updateEmail(expectedEmail)

        loginViewModel.email.value shouldBe expectedEmail
    }

    @Test
    fun `When update password, the password should be changed`() = runTest {
        val expectedPassword = "newPassword"
        loginViewModel = LoginViewModel(mockLogInUseCase)
        loginViewModel.updatePassword(expectedPassword)

        loginViewModel.password.value shouldBe expectedPassword
    }

    @Test
    fun `When the error alert is shown, the errorMessage should be null`() = runTest {
        loginViewModel = LoginViewModel(mockLogInUseCase)
        loginViewModel.dismissError()

        loginViewModel.errorMessage.value shouldBe null
    }
}
