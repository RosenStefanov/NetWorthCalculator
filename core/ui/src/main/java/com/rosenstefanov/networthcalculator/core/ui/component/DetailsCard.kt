package com.rosenstefanov.networthcalculator.core.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.rosenstefanov.networthcalculator.core.ui.theme.JakartaSans
import com.rosenstefanov.networthcalculator.core.ui.theme.NetWorthCalculatorTheme
import com.rosenstefanov.networthcalculator.core.ui.theme.NetWorthTheme


@Composable
fun DetailsCard(
    category: String,
    type: String,
    added: String,
    description: String?,
    modifier: Modifier = Modifier,
) {
    NetWorthSurfaceCard(
        modifier = modifier,
        cornerRadius = 20.dp,
        contentPadding = PaddingValues(18.dp),
    ) {
        Text(
            text = "Details",
            fontFamily = JakartaSans,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = (-0.01).em,
            color = MaterialTheme.colorScheme.onSurface,
        )
        Spacer(Modifier.height(14.dp))

        val rows = listOf("Category" to category, "Type" to type, "Added" to added)
        rows.forEachIndexed { index, (key, value) ->
            InfoRow(key = key, value = value)
            if (index != rows.lastIndex) {
                HorizontalDivider(thickness = 1.dp, color = NetWorthTheme.extendedColors.lineSoft)
            }
        }

        if (!description.isNullOrBlank()) {
            Column(Modifier.padding(top = 13.dp, bottom = 2.dp)) {
                Text(
                    text = "Description",
                    fontFamily = JakartaSans,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = NetWorthTheme.extendedColors.inkSub,
                )
                Spacer(Modifier.height(6.dp))
                Text(
                    text = description,
                    fontFamily = JakartaSans,
                    fontSize = 13.5.sp,
                    fontWeight = FontWeight.Medium,
                    color = NetWorthTheme.extendedColors.inkSub,
                    lineHeight = 20.25.sp,
                )
            }
        }
    }
}

@Composable
private fun InfoRow(key: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 13.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.Top,
    ) {
        Text(
            text = key,
            fontFamily = JakartaSans,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            color = NetWorthTheme.extendedColors.inkSub,
        )
        Text(
            text = value,
            modifier = Modifier.weight(1f),
            fontFamily = JakartaSans,
            fontSize = 13.5.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.End,
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFEEF0F6)
@Composable
internal fun DetailsCardWithDescriptionPreview() {
    NetWorthCalculatorTheme {
        DetailsCard(
            category = "Real Estate",
            type = "Asset",
            added = "Mar 2021",
            description = "Family home in Austin. Primary residence, purchased 2021.",
            modifier = Modifier.padding(16.dp),
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFEEF0F6)
@Composable
internal fun DetailsCardNoDescriptionPreview() {
    NetWorthCalculatorTheme {
        DetailsCard(
            category = "Mortgage",
            type = "Liability",
            added = "Mar 2021",
            description = null,
            modifier = Modifier.padding(16.dp),
        )
    }
}
