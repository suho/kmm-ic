package co.nimblehq.ic.kmm.suv.android.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import co.nimblehq.ic.kmm.suv.android.ui.components.ErrorAlertDialog
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
            onButtonClick = { viewModel.dismissError() }
        )
    }

    HomeScreenContent(currentDate, avatarUrlString, isLoading)
}

@Composable
private fun HomeScreenContent(
    currentDate: String,
    imageUrlString: String,
    isLoading: Boolean
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black) // TODO: Remove this setup later
            .statusBarsPadding()
            .systemBarsPadding()
    ) {
        Column {
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
    HomeScreenContent("TUESDAY, NOVEMBER 8", "image_url", true)
}

@Preview
@Composable
fun HomeScreenContentPreview() {
    HomeScreenContent("TUESDAY, NOVEMBER 8", "image_url", false)
}
