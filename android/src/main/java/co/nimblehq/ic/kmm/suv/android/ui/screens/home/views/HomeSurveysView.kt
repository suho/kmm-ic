package co.nimblehq.ic.kmm.suv.android.ui.screens.home.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import co.nimblehq.ic.kmm.suv.android.R
import co.nimblehq.ic.kmm.suv.android.extension.placeholder
import co.nimblehq.ic.kmm.suv.android.ui.components.DotsIndicator
import co.nimblehq.ic.kmm.suv.android.ui.theme.AppTheme
import co.nimblehq.ic.kmm.suv.android.ui.theme.Typography
import co.nimblehq.ic.kmm.suv.android.util.LoadingParameterProvider
import coil.compose.AsyncImage
import timber.log.Timber

data class HomeSurveyUiModel(
    val title: String,
    val description: String,
    val imageUrl: String
)

data class HomeSurveysUiModel(
    val surveys: List<HomeSurveyUiModel>,
    val currentPageIndex: Int,
    val isLoading: Boolean
) {
    val totalPages: Int
        get() = surveys.size
    val currentSurveyUiModel: HomeSurveyUiModel
        get() = surveys[currentPageIndex]
}

@Composable
fun HomeSurveysView(uiModel: HomeSurveysUiModel) {
    if (uiModel.isLoading) {
        HomeSurveysLoadingContent()
    } else {
        HomeSurveysContent(uiModel)
    }
}

private enum class SwipeDirection {
    IDLE, LEFT, RIGHT
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun HomeSurveysContent(uiModel: HomeSurveysUiModel) {
    val swipeableState = rememberSwipeableState(initialValue = SwipeDirection.IDLE)
    val endAnchor = LocalDensity
        .current
        .run {
            LocalConfiguration.current.screenWidthDp.toDp().toPx()
        }
    val anchors = mapOf(
        endAnchor / 2 to SwipeDirection.IDLE,
        0f to SwipeDirection.LEFT,
        endAnchor to SwipeDirection.RIGHT
    )

    LaunchedEffect(swipeableState.currentValue) {
        Timber.d("${swipeableState.currentValue}")
        swipeableState.snapTo(SwipeDirection.IDLE)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .swipeable(
                state = swipeableState,
                anchors = anchors,
                orientation = Orientation.Horizontal
            )
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = uiModel.currentSurveyUiModel.imageUrl,
                contentDescription = null,
                contentScale = ContentScale.FillHeight,
                modifier = Modifier.matchParentSize()
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.6f)
                        )
                    )
                )
        ) {
            Column(
                verticalArrangement = Arrangement.Bottom,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = AppTheme.dimensions.mediumPadding)
            ) {
                DotsIndicator(uiModel.totalPages, uiModel.currentPageIndex)
                Spacer(modifier = Modifier.height(AppTheme.dimensions.mediumPadding))
                Text(
                    text = uiModel.currentSurveyUiModel.title,
                    color = Color.White,
                    style = Typography.h5,
                    maxLines = 4
                )
                Spacer(modifier = Modifier.width(5.dp))
                Row(
                    verticalAlignment = Alignment.Bottom,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 54.dp)
                ) {
                    Text(
                        text = uiModel.currentSurveyUiModel.description,
                        color = Color.White.copy(alpha = 0.7f),
                        style = Typography.subtitle1,
                        maxLines = 2,
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(AppTheme.dimensions.mediumPadding))
                    Button(
                        onClick = {
                            /*TODO: Open survey detail screen - will handle this action in another story*/
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.Transparent
                        ),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_survey_detail_arrow),
                            contentDescription = null,
                            contentScale = ContentScale.FillWidth,
                            modifier = Modifier.size(56.dp),
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun HomeSurveysLoadingContent() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .systemBarsPadding()
    ) {
        Column(
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = AppTheme.dimensions.mediumPadding)
        ) {
            Spacer(
                modifier = Modifier
                    .size(50.dp, 16.dp)
                    .placeholder(true, 8.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Spacer(
                modifier = Modifier
                    .size(250.dp, 18.dp)
                    .placeholder(true, 9.dp)
            )
            Spacer(modifier = Modifier.height(5.dp))
            Spacer(
                modifier = Modifier
                    .size(200.dp, 18.dp)
                    .placeholder(true, 9.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Spacer(
                modifier = Modifier
                    .size(320.dp, 18.dp)
                    .placeholder(true, 9.dp)
            )
            Spacer(modifier = Modifier.height(5.dp))
            Spacer(
                modifier = Modifier
                    .size(250.dp, 18.dp)
                    .placeholder(true, 9.dp)
            )
            Spacer(modifier = Modifier.height(54.dp))
        }
    }
}

@Preview
@Composable
fun HomeSurveysViewLoadingPreview(
    @PreviewParameter(LoadingParameterProvider::class) isLoading: Boolean
) {
    HomeSurveysView(
        HomeSurveysUiModel(
            surveys = List(3) {
                HomeSurveyUiModel(
                    title = "Working from home Check-In!",
                    description = "We would like to know what are your goals and skills you wanted",
                    imageUrl = "https://dhdbhh0jsld0o.cloudfront.net/m/1ea51560991bcb7d00d0_l",
                )
            },
            currentPageIndex = 1,
            isLoading
        )
    )
}
