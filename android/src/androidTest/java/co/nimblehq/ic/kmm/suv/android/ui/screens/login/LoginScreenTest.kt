package co.nimblehq.ic.kmm.suv.android.ui.screens.login

import co.nimblehq.ic.kmm.suv.android.R
import androidx.activity.compose.setContent
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import co.nimblehq.ic.kmm.suv.android.MainActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalTestApi
class LoginScreenTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() {
        composeRule.activity.setContent {
            LoginScreen()
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
}
