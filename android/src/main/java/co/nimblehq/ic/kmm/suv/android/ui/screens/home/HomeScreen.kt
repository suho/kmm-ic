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
import androidx.compose.ui.tooling.preview.PreviewParameter
import co.nimblehq.ic.kmm.suv.android.ui.components.ErrorAlertDialog
import co.nimblehq.ic.kmm.suv.android.ui.screens.home.views.*
import co.nimblehq.ic.kmm.suv.android.util.LoadingParameterProvider
import org.koin.androidx.compose.getViewModel

@Composable
fun HomeScreen(viewModel: HomeViewModel = getViewModel()) {
    val currentDate by viewModel.currentDate.collectAsState()
    val avatarUrl by viewModel.avatarUrlString.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val surveysUiModel by viewModel.surveysUiModel.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadProfileAndSurveys()
    }

    errorMessage?.let {
        ErrorAlertDialog(
            message = it,
            onButtonClick = viewModel::dismissError
        )
    }

    val uiModel = HomeUiModel(
        HomeHeaderUiModel(
            currentDate,
            avatarUrl,
            isLoading
        ),
        HomeContentUiModel(
            isLoading,
            surveysUiModel
        )
    )
    HomeScreenContent(
        uiModel,
        onSwipe = {
            when (it) {
                SwipeDirection.LEFT -> {
                    viewModel.showNextSurvey()

                }
                SwipeDirection.RIGHT -> {
                    viewModel.showPreviousSurvey()
                }
                else -> {}
            }
        }
    )
}

private data class HomeUiModel(
    val headerUiModel: HomeHeaderUiModel,
    val contentUiModel: HomeContentUiModel
)

@Composable
private fun HomeScreenContent(uiModel: HomeUiModel, onSwipe: (SwipeDirection) -> Unit = {}) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        HomeSurveysView(
            uiModel.contentUiModel.isLoading,
            uiModel.contentUiModel.surveysUiModel,
            onSwipe
        )
        Column(
            modifier = Modifier
                .statusBarsPadding()
        ) {
            HomeHeaderView(uiModel.headerUiModel)
        }
    }
}

@Preview
@Composable
fun HomeScreenContentPreview(
    @PreviewParameter(LoadingParameterProvider::class) isLoading: Boolean
) {
    HomeScreenContent(
        HomeUiModel(
            headerUiModel = HomeHeaderUiModel(
                "TUESDAY, NOVEMBER 8",
                "image_url",
                isLoading
            ),
            HomeContentUiModel(
                isLoading,
                HomeSurveysUiModel(
                    surveys = List(3) {
                        HomeSurveyUiModel(
                            "Working from home Check-In",
                            "We would like to know what are your goals and skills you wanted",
                            "https://dhdbhh0jsld0o.cloudfront.net/m/1ea51560991bcb7d00d0_l",
                        )
                    },
                    currentPageIndex = 1
                )
            )
        )
    )
}
