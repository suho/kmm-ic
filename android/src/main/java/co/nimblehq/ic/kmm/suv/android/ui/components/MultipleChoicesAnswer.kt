package co.nimblehq.ic.kmm.suv.android.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import co.nimblehq.ic.kmm.suv.android.R
import co.nimblehq.ic.kmm.suv.android.ui.theme.Typography

@SuppressLint("MutableCollectionMutableState")
@Composable
fun MultipleChoicesAnswer(
    choices: List<String>,
    onIndexesChange: (Set<Int>) -> Unit,
    currentIndexes: Set<Int> = mutableSetOf()
) {
    var selectedIndexes by remember { mutableStateOf(currentIndexes) }
    Column(modifier = Modifier.padding(horizontal = 80.dp)) {
        choices.forEachIndexed { index, choice ->
            val isHighlight = selectedIndexes.contains(index)
            Button(
                onClick = {
                    selectedIndexes = if (selectedIndexes.contains(index)) {
                        selectedIndexes.minus(index)
                    } else {
                        selectedIndexes.plus(index)
                    }
                    onIndexesChange(selectedIndexes)
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Transparent
                ),
                elevation = null,
                contentPadding = PaddingValues(0.dp),
                modifier = Modifier
                    .height(56.dp)
                    .fillMaxWidth()
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentHeight()
                ) {
                    Text(
                        text = choice,
                        color = if (isHighlight) Color.White else Color.White.copy(alpha = 0.5f),
                        style = if (isHighlight) Typography.h6 else Typography.h6.copy(fontWeight = FontWeight.Normal),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                    )
                    val imageId =
                        if (isHighlight) R.drawable.ic_checkbox_selected else R.drawable.ic_checkbox_unselected
                    Image(
                        painter = painterResource(id = imageId),
                        contentDescription = null,
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier
                            .width(28.dp)
                            .height(30.dp)
                    )
                }
            }
            if (index < choices.size - 1) {
                Spacer(
                    modifier = Modifier
                        .height(0.5.dp)
                        .fillMaxWidth()
                        .background(Color.White)
                )
            }
        }
    }
}

@Preview
@Composable
fun MultipleChoicesAnswerPreview() {
    MultipleChoicesAnswer(
        choices = listOf(
            "Choice 1 ",
            "Choice 2",
            "Choice 3",
        ),
        onIndexesChange = {}
    )
}
