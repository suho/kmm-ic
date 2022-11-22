package co.nimblehq.ic.kmm.suv.android.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import co.nimblehq.ic.kmm.suv.android.ui.components.ErrorAlertDialog
import co.nimblehq.ic.kmm.suv.android.ui.screens.home.views.HomeHeaderView
import co.nimblehq.ic.kmm.suv.android.ui.screens.home.views.HomeSurveysView
import org.koin.androidx.compose.getViewModel

@Composable
fun HomeScreen(viewModel: HomeViewModel = getViewModel()) {
    val currentDate by viewModel.currentDate.collectAsState()
    val avatarUrlString by viewModel.avatarUrlString.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadProfile()
    }

    errorMessage?.let {
        ErrorAlertDialog(
            message = it,
            onButtonClick = viewModel::dismissError
        )
    }

    // TODO: Update survey data later
    HomeScreenContent(
        currentDate,
        avatarUrlString,
        "Working from home Check-In Check-In",
        "We would like to know what are your goals and skills you wanted!",
        isLoading
    )
}

@Composable
private fun HomeScreenContent(
    currentDate: String,
    imageUrlString: String,
    surveyTitle: String,
    surveyDescription: String,
    isLoading: Boolean
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        HomeSurveysView(
            surveyTitle,
            surveyDescription,
            isLoading
        )
        Column(
            modifier = Modifier
                .statusBarsPadding()
        ) {
            HomeHeaderView(
                currentDate,
                imageUrlString,
                isLoading
            )
        }
    }
}

@Preview
@Composable
fun HomeScreenContentLoadingPreview() {
    HomeScreenContent(
        "TUESDAY, NOVEMBER 8",
        "image_url",
        "Working from home Check-In",
        "We would like to know what are your goals and skills you wanted",
        true
    )
}

@Preview
@Composable
fun HomeScreenContentPreview() {
    HomeScreenContent(
        "TUESDAY, NOVEMBER 8",
        "image_url",
        "Working from home Check-In",
        "We would like to know what are your goals and skills you wanted",
        false
    )
}
