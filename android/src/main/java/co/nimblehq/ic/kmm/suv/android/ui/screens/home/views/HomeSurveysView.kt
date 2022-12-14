package co.nimblehq.ic.kmm.suv.android.ui.screens.home.views

import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import co.nimblehq.ic.kmm.suv.android.R
import co.nimblehq.ic.kmm.suv.android.extension.placeholder
import co.nimblehq.ic.kmm.suv.android.ui.components.DotsIndicator
import co.nimblehq.ic.kmm.suv.android.ui.components.ImageBackground
import co.nimblehq.ic.kmm.suv.android.ui.screens.home.HomeContentDescription
import co.nimblehq.ic.kmm.suv.android.ui.theme.AppTheme
import co.nimblehq.ic.kmm.suv.android.ui.theme.Typography
import co.nimblehq.ic.kmm.suv.android.util.LoadingParameterProvider

data class HomeSurveyUiModel(
    val title: String,
    val description: String,
    val imageUrl: String
)

data class HomeSurveysUiModel(
    val currentSurveyUiModel: HomeSurveyUiModel,
    val totalPages: Int,
    val currentPageIndex: Int
)

data class HomeContentUiModel(
    val isLoading: Boolean,
    val surveysUiModel: HomeSurveysUiModel?
)

@Composable
fun HomeSurveysView(
    uiModel: HomeContentUiModel,
    onSwipe: (SwipeDirection) -> Unit = {},
    onSurveyDetailClick: () -> Unit = {}
) {
    if (uiModel.isLoading) {
        HomeSurveysLoadingContent()
    } else uiModel.surveysUiModel?.let {
        HomeSurveysContent(it, onSwipe, onSurveyDetailClick)
    }
}

enum class SwipeDirection {
    IDLE, LEFT, RIGHT
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalAnimationApi::class)
@Composable
private fun HomeSurveysContent(
    uiModel: HomeSurveysUiModel,
    onSwipe: (SwipeDirection) -> Unit = {},
    onSurveyDetailClick: () -> Unit = {}
) {
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
        onSwipe(swipeableState.currentValue)
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
        ImageBackground(uiModel.currentSurveyUiModel.imageUrl)
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                verticalArrangement = Arrangement.Bottom,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = AppTheme.dimensions.mediumPadding)
            ) {
                DotsIndicator(
                    uiModel.totalPages,
                    uiModel.currentPageIndex,
                    modifier = Modifier
                        .semantics { contentDescription = HomeContentDescription.INDICATOR }
                )
                Spacer(modifier = Modifier.height(AppTheme.dimensions.mediumPadding))
                Crossfade(targetState = uiModel.currentSurveyUiModel.title) {
                    Text(
                        text = it,
                        color = Color.White,
                        style = Typography.h5,
                        maxLines = 4,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .semantics { contentDescription = HomeContentDescription.SURVEY_TITLE }
                    )
                }
                Spacer(modifier = Modifier.width(5.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 54.dp)
                ) {
                    Crossfade(
                        targetState = uiModel.currentSurveyUiModel.description,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = it,
                            color = Color.White.copy(alpha = 0.7f),
                            style = Typography.subtitle1,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier
                                .semantics {
                                    contentDescription = HomeContentDescription.SURVEY_DESCRIPTION
                                }
                        )
                    }
                    Spacer(modifier = Modifier.width(AppTheme.dimensions.mediumPadding))
                    Button(
                        onClick = {
                            onSurveyDetailClick()
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.Transparent
                        ),
                        elevation = null,
                        contentPadding = PaddingValues(0.dp),
                        modifier = Modifier
                            .semantics {
                                contentDescription = HomeContentDescription.SURVEY_DETAIL_BUTTON
                            }
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
        HomeContentUiModel(
            isLoading,
            HomeSurveysUiModel(
                HomeSurveyUiModel(
                    title = "Working from home Check-In!",
                    description = "We would like to know what are your goals and skills you wanted!",
                    imageUrl = "https://dhdbhh0jsld0o.cloudfront.net/m/1ea51560991bcb7d00d0_l",
                ),
                totalPages = 3,
                currentPageIndex = 1
            )
        )
    )
}
