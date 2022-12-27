package co.nimblehq.ic.kmm.suv.android.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import co.nimblehq.ic.kmm.suv.android.ui.components.ErrorAlertDialog
import co.nimblehq.ic.kmm.suv.android.ui.screens.home.views.*
import co.nimblehq.ic.kmm.suv.android.util.LoadingParameterProvider
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import org.koin.androidx.compose.getViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = getViewModel(),
    onSurveyDetailClick: (SurveyArgument) -> Unit = {}
) {
    val currentDate by viewModel.currentDate.collectAsState()
    val avatarUrl by viewModel.avatarUrlString.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val surveysUiModel by viewModel.surveysUiModel.collectAsState()

    LaunchedEffect(Unit) {
        // TODO: Improve re-call API logic later
        viewModel.loadProfileAndSurveys()
    }

    errorMessage?.let {
        ErrorAlertDialog(
            message = it,
            onButtonClick = viewModel::dismissError
        )
    }

    val uiModel = HomeUiModel(
        isRefreshing = isLoading,
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
        },
        onSurveyDetailClick = {
            onSurveyDetailClick(viewModel.getCurrentSurveyArgument())
        },
        onRefresh = {
            viewModel.loadProfileAndSurveys(isRefresh = true)
        }
    )
}

private data class HomeUiModel(
    val isRefreshing: Boolean,
    val headerUiModel: HomeHeaderUiModel,
    val contentUiModel: HomeContentUiModel
)

@Composable
private fun HomeScreenContent(
    uiModel: HomeUiModel,
    onSwipe: (SwipeDirection) -> Unit = {},
    onSurveyDetailClick: () -> Unit = {},
    onRefresh: () -> Unit = {}
) {
    SwipeRefresh(
        state = rememberSwipeRefreshState(uiModel.isRefreshing),
        onRefresh = onRefresh,
        indicator = { _, _ -> Unit },
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(Modifier.fillMaxHeight()) {
            item {
                Box(
                    modifier = Modifier
                        .fillParentMaxHeight()
                        .background(Color.Black)
                ) {
                    HomeSurveysView(
                        uiModel.contentUiModel,
                        onSwipe,
                        onSurveyDetailClick
                    )
                    Column(
                        modifier = Modifier
                            .statusBarsPadding()
                    ) {
                        HomeHeaderView(uiModel.headerUiModel)
                    }
                }
            }
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
            isRefreshing = false,
            headerUiModel = HomeHeaderUiModel(
                "TUESDAY, NOVEMBER 8",
                "image_url",
                isLoading
            ),
            HomeContentUiModel(
                isLoading,
                HomeSurveysUiModel(
                    HomeSurveyUiModel(
                        "Working from home Check-In",
                        "We would like to know what are your goals and skills you wanted",
                        "https://dhdbhh0jsld0o.cloudfront.net/m/1ea51560991bcb7d00d0_l",
                    ),
                    totalPages = 3,
                    currentPageIndex = 1
                )
            )
        )
    )
}
