package co.nimblehq.ic.kmm.suv.android.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun DotsIndicator(
    totalDots: Int,
    selectedIndex: Int
) {
    LazyRow(
        modifier = Modifier
            .wrapContentWidth()
            .wrapContentHeight()
    ) {
        val boxModifier = Modifier
            .size(8.dp)
            .clip(CircleShape)
        items(totalDots) { index ->
            if (index == selectedIndex) {
                Box(
                    modifier = boxModifier
                        .background(Color.White)
                )
            } else {
                Box(
                    modifier = boxModifier
                        .background(Color.White.copy(alpha = 0.2f))
                )
            }
            if (index != totalDots - 1) {
                Spacer(modifier = Modifier.width(10.dp))
            }
        }
    }
}
