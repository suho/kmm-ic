package co.nimblehq.ic.kmm.suv.android.ui.screens.surveydetail

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import co.nimblehq.ic.kmm.suv.android.R
import co.nimblehq.ic.kmm.suv.android.ui.components.BackButton
import co.nimblehq.ic.kmm.suv.android.ui.screens.home.SurveyArgument
import co.nimblehq.ic.kmm.suv.android.ui.theme.AppTheme
import co.nimblehq.ic.kmm.suv.android.ui.theme.Shapes
import co.nimblehq.ic.kmm.suv.android.ui.theme.Typography
import coil.compose.AsyncImage
import org.koin.androidx.compose.getViewModel

@Composable
fun SurveyDetailScreen(
    surveyArgument: SurveyArgument?,
    viewModel: SurveyDetailViewModel = getViewModel(),
    onBackClick: () -> Unit = {},
    onStartSurveyClick: () -> Unit = {}
) {
    val contentUiModel by viewModel.contentUiModel.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.set(surveyArgument)
    }
    contentUiModel?.let {
        SurveyDetailScreenContent(
            it,
            onBackClick,
            onStartSurveyClick
        )
    }

}

data class SurveyDetailContentUiModel(
    val title: String,
    val description: String,
    val imageUrl: String
)

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
private fun SurveyDetailScreenContent(
    uiModel: SurveyDetailContentUiModel,
    onBackClick: () -> Unit = {},
    onStartSurveyClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = uiModel.imageUrl,
                contentDescription = null,
                contentScale = ContentScale.FillHeight,
                modifier = Modifier.matchParentSize()
            )
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
            )
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = Color.Transparent,
                elevation = 0.dp,
                modifier = Modifier
                    .statusBarsPadding()

            ) {
                BackButton(onBackClick)
            }
        },
        drawerBackgroundColor = Color.Transparent,
        contentColor = Color.Transparent,
        backgroundColor = Color.Transparent
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(horizontal = AppTheme.dimensions.mediumPadding, vertical = 12.dp)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = uiModel.title,
                    color = Color.White,
                    style = Typography.h5,
                    modifier = Modifier
                        .semantics {
                            contentDescription = SurveyDetailContentDescription.TITLE
                        }
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = uiModel.description,
                    color = Color.White.copy(alpha = 0.7f),
                    style = Typography.subtitle1,
                    modifier = Modifier
                        .semantics {
                            contentDescription = SurveyDetailContentDescription.DESCRIPTION
                        }
                )
            }
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 54.dp)
            ) {
                Button(
                    shape = Shapes.medium,
                    onClick = onStartSurveyClick,
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.White
                    ),
                    modifier = Modifier
                        .width(140.dp)
                        .height(56.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.start_survey),
                        color = Color.Black,
                        style = Typography.subtitle2
                    )
                }
            }
        }

    }
}

@Preview
@Composable
fun SurveyDetailScreenPreview() {
    SurveyDetailScreenContent(
        SurveyDetailContentUiModel(
            "Working from Home Check-in",
            "We would like to know how you feel about our work from Home",
            "https://dhdbhh0jsld0o.cloudfront.net/m/1ea51560991bcb7d00d0_l"
        )
    )
}
