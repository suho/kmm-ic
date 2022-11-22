package co.nimblehq.ic.kmm.suv.android.ui.screens.home.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import co.nimblehq.ic.kmm.suv.android.R
import co.nimblehq.ic.kmm.suv.android.ui.screens.home.HomeContentDescription
import co.nimblehq.ic.kmm.suv.android.ui.theme.Typography
import coil.compose.AsyncImage
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer

@Composable
fun HomeHeaderView(title: String, imageUrlString: String, isLoading: Boolean) {
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
                style = Typography.caption,
                modifier = Modifier
                    .placeholder(
                        visible = isLoading,
                        color = Color.Gray,
                        shape = RoundedCornerShape(6.5.dp),
                        highlight = PlaceholderHighlight.shimmer(
                            highlightColor = Color.White,
                        ),
                    )
                    .semantics { contentDescription = HomeContentDescription.CURRENT_DATE },
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = stringResource(id = R.string.home_today),
                color = Color.White,
                style = Typography.h4,
                modifier = Modifier
                    .placeholder(
                        visible = isLoading,
                        color = Color.Gray,
                        shape = RoundedCornerShape(6.5.dp),
                        highlight = PlaceholderHighlight.shimmer(
                            highlightColor = Color.White,
                        ),
                    )
                    .semantics { contentDescription = HomeContentDescription.TODAY_TEXT }
            )
        }
        AsyncImage(
            model = imageUrlString,
            contentDescription = null,
            modifier = Modifier
                .padding(top = 19.dp)
                .size(36.dp)
                .clip(CircleShape)
                .placeholder(
                    visible = isLoading,
                    color = Color.Gray,
                    shape = RoundedCornerShape(4.dp),
                    highlight = PlaceholderHighlight.shimmer(
                        highlightColor = Color.White,
                    ),
                )
                .semantics {
                    contentDescription = HomeContentDescription.AVATAR
                }
        )
    }
}
