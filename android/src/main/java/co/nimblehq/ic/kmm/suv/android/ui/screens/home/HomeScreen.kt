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
import co.nimblehq.ic.kmm.suv.android.ui.screens.home.views.HomeSurveysUiModel
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
    val uiModel = HomeUiModel(
        currentDate,
        avatarUrlString,
        HomeSurveysUiModel(
            "Working from home Check-In Check-In",
            "We would like to know what are your goals and skills you wanted!",
            "https://dhdbhh0jsld0o.cloudfront.net/m/1ea51560991bcb7d00d0_l",
            3,
            1,
            isLoading
        ),
        isLoading
    )
    HomeScreenContent(
        uiModel
    )
}

private data class HomeUiModel(
    val currentDate: String,
    val avatarUrlString: String,
    val surveysUiModel: HomeSurveysUiModel,
    val isLoading: Boolean
)

@Composable
private fun HomeScreenContent(uiModel: HomeUiModel) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        HomeSurveysView(uiModel.surveysUiModel)
        Column(
            modifier = Modifier
                .statusBarsPadding()
        ) {
            HomeHeaderView(
                uiModel.currentDate,
                uiModel.avatarUrlString,
                uiModel.isLoading
            )
        }
    }
}

@Preview
@Composable
fun HomeScreenContentLoadingPreview() {
    HomeScreenContent(
        HomeUiModel(
            "TUESDAY, NOVEMBER 8",
            "image_url",
            HomeSurveysUiModel(
                "Working from home Check-In",
                "We would like to know what are your goals and skills you wanted",
                "https://dhdbhh0jsld0o.cloudfront.net/m/1ea51560991bcb7d00d0_l",
                3,
                1,
                true
            ),
            true
        )
    )
}

@Preview
@Composable
fun HomeScreenContentPreview() {
    HomeScreenContent(
        HomeUiModel(
            "TUESDAY, NOVEMBER 8",
            "image_url",
            HomeSurveysUiModel(
                "Working from home Check-In",
                "We would like to know what are your goals and skills you wanted",
                "https://dhdbhh0jsld0o.cloudfront.net/m/1ea51560991bcb7d00d0_l",
                3,
                1,
                false
            ),
            false
        )
    )
}
