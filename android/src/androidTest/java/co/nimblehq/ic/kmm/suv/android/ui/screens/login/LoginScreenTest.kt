package co.nimblehq.ic.kmm.suv.android.ui.screens.login

import androidx.activity.compose.setContent
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import co.nimblehq.ic.kmm.suv.android.MainActivity
import co.nimblehq.ic.kmm.suv.android.R
import co.nimblehq.ic.kmm.suv.domain.model.AppError
import co.nimblehq.ic.kmm.suv.domain.usecase.LogInUseCase
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalTestApi
class LoginScreenTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    private lateinit var viewModel: LoginViewModel
    private val mockLogInUseCase = mockk<LogInUseCase>()

    @Before
    fun setup() {
        viewModel = LoginViewModel(mockLogInUseCase)
        composeRule.activity.setContent {
            LoginScreen({}, viewModel = viewModel)
        }
    }

    @Test
    fun when_enter_the_login_screen__it_shows_email_password_and_login_button() {
        composeRule.apply {
            mainClock.advanceTimeBy(4000) // After animation
            onNodeWithText(activity.getString(R.string.login_email)).assertIsDisplayed()
            onNodeWithText(activity.getString(R.string.login_password)).assertIsDisplayed()
            onNodeWithText(activity.getString(R.string.login_button)).assertIsDisplayed()
        }
    }

    @Test
    fun when_enter_valid_credentials__is_log_in_success_is_true() {
        every { mockLogInUseCase(any(), any()) } returns flowOf(mockk())
        composeRule.apply {
            mainClock.advanceTimeBy(4000) // After animation
            onNodeWithText(activity.getString(R.string.login_email)).performTextInput("dev@nimblehq.co")
            onNodeWithText(activity.getString(R.string.login_password)).performTextInput("12345678")
            onNodeWithText(activity.getString(R.string.login_button)).performClick()
            assertTrue(viewModel.uiState.value.isLogInSuccess)
        }
    }

    @Test
    fun when_enter_invalid_credentials__error_toast_is_shown() {
        val expectedError = AppError("Log in Failed")
        every { mockLogInUseCase(any(), any()) } returns flow { throw expectedError }
        composeRule.apply {
            mainClock.advanceTimeBy(4000) // After animation
            onNodeWithText(activity.getString(R.string.login_email)).performTextInput("invalid@nimblehq.co")
            onNodeWithText(activity.getString(R.string.login_password)).performTextInput("invalid")
            onNodeWithText(activity.getString(R.string.login_button)).performClick()
            waitForIdle()
            onNodeWithText(expectedError.message ?: "").assertIsDisplayed()
        }
    }
}
