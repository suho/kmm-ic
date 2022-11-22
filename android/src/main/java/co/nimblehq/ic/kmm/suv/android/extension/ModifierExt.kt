package co.nimblehq.ic.kmm.suv.android.extension

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer

fun Modifier.placeholder(
    visible: Boolean,
    shapeValue: Dp
): Modifier {
    return placeholder(
        visible = visible,
        color = Color.Gray,
        shape = RoundedCornerShape(shapeValue),
        highlight = PlaceholderHighlight.shimmer(
            highlightColor = Color.White,
        ),
    )
}
