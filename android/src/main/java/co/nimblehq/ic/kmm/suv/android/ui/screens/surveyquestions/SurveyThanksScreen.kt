package co.nimblehq.ic.kmm.suv.android.ui.screens.surveyquestions

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.delay

@Composable
fun SurveyThanksScreen(onFinishedAnimation: () -> Unit) {
    // TODO: Implement this screen in another story #73
    LaunchedEffect(Unit) {
        delay(3000)
        onFinishedAnimation()
    }
    Text(text = "Thanks for taking the survey")
}
