package com.rosenstefanov.networthcalculator.core.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rosenstefanov.networthcalculator.core.ui.theme.JakartaSans
import com.rosenstefanov.networthcalculator.core.ui.theme.NetWorthCalculatorTheme
import com.rosenstefanov.networthcalculator.core.ui.theme.NetWorthTheme

private val FieldShape = RoundedCornerShape(15.dp)
private val PlaceholderColor = Color(0xFFB7BACB)

@Composable
fun InputField(
    value: String,
    placeholder: String,
    accentColor: Color,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    height: Dp = 54.dp,
    textStyle: TextStyle = TextStyle(
        fontFamily = JakartaSans,
        fontSize = 15.sp,
        fontWeight = FontWeight.SemiBold,
        color = MaterialTheme.colorScheme.onSurface,
    ),
    placeholderStyle: TextStyle = TextStyle(
        fontFamily = JakartaSans,
        fontSize = 15.sp,
        fontWeight = FontWeight.Medium,
        color = PlaceholderColor,
    ),
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    leading: (@Composable () -> Unit)? = null,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val focused by interactionSource.collectIsFocusedAsState()

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        singleLine = true,
        interactionSource = interactionSource,
        cursorBrush = SolidColor(accentColor),
        textStyle = textStyle,
        keyboardOptions = keyboardOptions,
        visualTransformation = visualTransformation,
        decorationBox = { inner ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height)
                    .clip(FieldShape)
                    .background(MaterialTheme.colorScheme.surface)
                    .border(
                        width = 1.5.dp,
                        color = if (focused) accentColor else NetWorthTheme.extendedColors.line,
                        shape = FieldShape,
                    )
                    .then(
                        if (focused) {
                            Modifier.drawBehind {
                                drawRoundRect(
                                    color = accentColor.copy(alpha = 0.12f),
                                    style = Stroke(4.dp.toPx()),
                                    cornerRadius = CornerRadius(15.dp.toPx()),
                                )
                            }
                        } else {
                            Modifier
                        },
                    )
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                leading?.invoke()
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.CenterStart,
                ) {
                    if (value.isEmpty()) {
                        Text(text = placeholder, style = placeholderStyle)
                    }
                    inner()
                }
            }
        },
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFEEF0F6)
@Composable
internal fun InputFieldEmptyPreview() {
    NetWorthCalculatorTheme {
        InputField(
            value = "",
            placeholder = "e.g. Brokerage, Savings…",
            accentColor = Color(0xFF3B6BFF),
            onValueChange = {},
            modifier = Modifier.padding(16.dp),
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFEEF0F6)
@Composable
internal fun InputFieldFilledPreview() {
    NetWorthCalculatorTheme {
        InputField(
            value = "Brokerage",
            placeholder = "e.g. Brokerage, Savings…",
            accentColor = Color(0xFF3B6BFF),
            onValueChange = {},
            modifier = Modifier.padding(16.dp),
        )
    }
}
