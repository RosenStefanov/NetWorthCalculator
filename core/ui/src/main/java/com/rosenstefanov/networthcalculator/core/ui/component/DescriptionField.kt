package com.rosenstefanov.networthcalculator.core.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
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
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rosenstefanov.networthcalculator.core.ui.theme.JakartaSans
import com.rosenstefanov.networthcalculator.core.ui.theme.NetWorthCalculatorTheme
import com.rosenstefanov.networthcalculator.core.ui.theme.NetWorthTheme

private val DescriptionShape = RoundedCornerShape(15.dp)

@Composable
fun DescriptionField(
    value: String,
    placeholder: String,
    accentColor: Color,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val focused by interactionSource.collectIsFocusedAsState()

    Column(modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(7.dp),
            modifier = Modifier.padding(bottom = 7.dp),
        ) {
            Text(
                text = "Description",
                fontFamily = JakartaSans,
                fontSize = 12.5.sp,
                fontWeight = FontWeight.SemiBold,
                color = NetWorthTheme.extendedColors.inkSub,
            )
            Text(
                text = "Optional",
                fontFamily = JakartaSans,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                color = NetWorthTheme.extendedColors.chevron,
                modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .background(NetWorthTheme.extendedColors.field)
                    .padding(horizontal = 7.dp, vertical = 1.dp),
            )
        }
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            interactionSource = interactionSource,
            cursorBrush = SolidColor(accentColor),
            textStyle = TextStyle(
                fontFamily = JakartaSans,
                fontSize = 14.5.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface,
                lineHeight = 21.sp,
            ),
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),
            decorationBox = { inner ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 84.dp)
                        .clip(DescriptionShape)
                        .background(MaterialTheme.colorScheme.surface)
                        .border(
                            width = 1.5.dp,
                            color = if (focused) accentColor else NetWorthTheme.extendedColors.line,
                            shape = DescriptionShape,
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
                        .padding(horizontal = 16.dp, vertical = 14.dp),
                    contentAlignment = Alignment.TopStart,
                ) {
                    if (value.isEmpty()) {
                        Text(
                            text = placeholder,
                            fontFamily = JakartaSans,
                            fontSize = 14.5.sp,
                            fontWeight = FontWeight.Medium,
                            color = NetWorthTheme.extendedColors.placeholder,
                            lineHeight = 21.sp,
                        )
                    }
                    inner()
                }
            },
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFEEF0F6)
@Composable
internal fun DescriptionFieldEmptyPreview() {
    NetWorthCalculatorTheme {
        DescriptionField(
            value = "",
            placeholder = "Add a note — account number, where it's held, etc.",
            accentColor = Color(0xFF3B6BFF),
            onValueChange = {},
            modifier = Modifier.padding(16.dp),
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFEEF0F6)
@Composable
internal fun DescriptionFieldFilledPreview() {
    NetWorthCalculatorTheme {
        DescriptionField(
            value = "Held at Vanguard, account ending 4821.",
            placeholder = "Add a note — account number, where it's held, etc.",
            accentColor = Color(0xFF9333EA),
            onValueChange = {},
            modifier = Modifier.padding(16.dp),
        )
    }
}
