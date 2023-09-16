package com.example.calculator.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview
@Composable
fun PreviewCalculatorButton() = CalculatorButton(
    onClick = {
        Log.i("TEST_BUTTON", "Click")
    },
)

@Composable
fun CalculatorButton(
    modifier: Modifier = Modifier,
    label: String = "Button",
    color: Color = Color.Black,
    textStyle: TextStyle = TextStyle(),
    onClick: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .clip(RoundedCornerShape(15.dp))
            .background(color)
            .clickable(onClick = onClick)
            .then(modifier)
    ) {
        Text(
            text = label,
            style = textStyle,
            fontSize = 36.sp,
            color = Color.White
        )
    }
}