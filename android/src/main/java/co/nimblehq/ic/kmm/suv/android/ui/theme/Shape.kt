package co.nimblehq.ic.kmm.suv.android.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Shapes
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.dp

fun appShapes(
    small: RoundedCornerShape = RoundedCornerShape(4.dp),
    medium: RoundedCornerShape = RoundedCornerShape(10.dp),
    large: RoundedCornerShape = RoundedCornerShape(12.dp)
): Shapes = Shapes(small, medium, large)

internal val LocalShapes = staticCompositionLocalOf { appShapes() }
