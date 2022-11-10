package co.nimblehq.ic.kmm.suv.android.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import co.nimblehq.ic.kmm.suv.android.R
import co.nimblehq.ic.kmm.suv.android.ui.theme.Typography
import coil.compose.AsyncImage

@Composable
fun HomeScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black) // TODO: Remove this setup later
            .statusBarsPadding()
            .systemBarsPadding()
    ) {
        Column {
            // TODO: Remove these dummy data later
            HomeHeaderView(
                "FRIDAY, NOVEMBER 4",
                "https://cdn.pixabay.com/photo/2016/11/18/23/38/child-1837375__340.png"
            )
        }
    }
}

@Composable
private fun HomeHeaderView(title: String, imageUrlString: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, top = 16.dp, end = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = title,
                color = Color.White,
                style = Typography.caption
            )
            Text(
                text = stringResource(id = R.string.home_today),
                color = Color.White,
                style = Typography.h4
            )
        }
        AsyncImage(
            model = imageUrlString,
            contentDescription = null,
            modifier = Modifier
                .padding(top = 19.dp)
                .size(36.dp)
                .clip(CircleShape)
        )
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}
