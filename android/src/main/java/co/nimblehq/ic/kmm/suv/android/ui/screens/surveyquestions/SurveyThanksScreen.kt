package co.nimblehq.ic.kmm.suv.android.ui.screens.surveyquestions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import co.nimblehq.ic.kmm.suv.android.R
import co.nimblehq.ic.kmm.suv.android.ui.theme.AppTheme
import co.nimblehq.ic.kmm.suv.android.ui.theme.Typography
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import kotlinx.coroutines.delay

@Composable
fun SurveyThanksScreen(onFinishedAnimation: () -> Unit) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.confetti))
    val progress by animateLottieCompositionAsState(composition)

    LaunchedEffect(progress) {
        if (progress == 1.0f) {
            delay(500L)
            onFinishedAnimation()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .statusBarsPadding()
                .align(Alignment.Center)
                .padding(horizontal = AppTheme.dimensions.mediumPadding)
        ) {
            LottieAnimation(
                composition, modifier = Modifier
                    .size(200.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Text(
                text = stringResource(R.string.thanks_message),
                color = Color.White,
                style = Typography.h5,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(horizontal = AppTheme.dimensions.mediumPadding)
            )
        }
    }
}

@Preview
@Composable
fun SurveyThanksScreenPreview() {
    SurveyThanksScreen(onFinishedAnimation = {})
}
