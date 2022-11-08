package co.nimblehq.ic.kmm.suv.android.ui.screens.home

import androidx.activity.compose.setContent
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import co.nimblehq.ic.kmm.suv.android.MainActivity
import co.nimblehq.ic.kmm.suv.android.R
import co.nimblehq.ic.kmm.suv.android.ui.screens.login.LoginScreen
import co.nimblehq.ic.kmm.suv.android.ui.screens.login.LoginViewModel
import co.nimblehq.ic.kmm.suv.domain.model.AppError
import co.nimblehq.ic.kmm.suv.domain.model.User
import co.nimblehq.ic.kmm.suv.domain.usecase.GetProfileUseCase
import co.nimblehq.ic.kmm.suv.domain.usecase.LogInUseCase
import co.nimblehq.ic.kmm.suv.helper.date.DateTime
import co.nimblehq.ic.kmm.suv.helper.date.DateTimeFormatterImpl
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.datetime.LocalDate
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalTestApi
class HomeScreenTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    private lateinit var viewModel: HomeViewModel
    private val mockGetProfileUseCase: GetProfileUseCase = mockk()
    private val mockDateTime: DateTime = mockk()
    private val user = User("email", "name", "avatarUrl")

    @Before
    fun setup() {
        every { mockGetProfileUseCase() } returns flowOf(user)
        every { mockDateTime.today() } returns LocalDate(2022, 11, 8)
        viewModel = HomeViewModel(mockGetProfileUseCase, mockDateTime, DateTimeFormatterImpl())
        composeRule.activity.setContent {
            HomeScreen(viewModel = viewModel)
        }
    }

    @Test
    fun when_enter_the_home_screen__it_shows_home_header_view_components() {
        composeRule.apply {
            onNodeWithContentDescription(HomeContentDescription.CURRENT_DATE.value).assertIsDisplayed()
            onNodeWithContentDescription(HomeContentDescription.TODAY_TEXT.value).assertIsDisplayed()
            onNodeWithContentDescription(HomeContentDescription.AVATAR.value).assertIsDisplayed()
        }
    }
}
