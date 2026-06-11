package com.rosenstefanov.networthcalculator.core.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rosenstefanov.networthcalculator.core.ui.models.AllocItem
import com.rosenstefanov.networthcalculator.core.ui.theme.JakartaSans
import com.rosenstefanov.networthcalculator.core.ui.theme.NetWorthCalculatorTheme
import com.rosenstefanov.networthcalculator.core.ui.theme.NetWorthTheme
import com.rosenstefanov.networthcalculator.core.ui.theme.SpaceGrotesk
import java.util.Locale
import kotlin.math.roundToInt

@Composable
fun AllocationCard(
    items: List<AllocItem>,
    total: Long,
    modifier: Modifier = Modifier,
) {
    NetWorthSurfaceCard(
        modifier = modifier,
        cornerRadius = 20.dp,
        contentPadding = PaddingValues(18.dp),
    ) {
        Text(
            text = "Allocation",
            fontFamily = JakartaSans,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
        )
        Spacer(Modifier.height(14.dp))

        items.forEachIndexed { index, item ->
            val fraction = if (total > 0L) (item.amount.toFloat() / total).coerceIn(0f, 1f) else 0f
            AllocationRow(item = item, fraction = fraction)
            if (index != items.lastIndex) Spacer(Modifier.height(13.dp))
        }
    }
}

@Composable
private fun AllocationRow(item: AllocItem, fraction: Float) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(7.dp),
            ) {
                Box(
                    Modifier
                        .size(9.dp)
                        .clip(RoundedCornerShape(3.dp))
                        .background(item.color),
                )
                Text(
                    text = item.name,
                    fontFamily = JakartaSans,
                    fontSize = 12.5.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }
            Text(
                text = "$%,d · %d%%".format(Locale.US, item.amount, (fraction * 100).roundToInt()),
                fontFamily = SpaceGrotesk,
                fontSize = 12.5.sp,
                fontWeight = FontWeight.SemiBold,
                color = NetWorthTheme.extendedColors.inkSub,
                maxLines = 1,
            )
        }

        Box(
            Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(5.dp))
                .background(NetWorthTheme.extendedColors.field),
        ) {
            Box(
                Modifier
                    .fillMaxWidth(fraction)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(5.dp))
                    .background(item.color),
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFEEF0F6)
@Composable
internal fun AllocationCardAssetsPreview() {
    NetWorthCalculatorTheme {
        AllocationCard(
            total = 412_300,
            items = listOf(
                AllocItem("Real Estate", Color(0xFF3B6BFF), 312_000),
                AllocItem("Investments", Color(0xFF5B45F5), 58_400),
                AllocItem("Retirement", Color(0xFF7C3AED), 24_500),
                AllocItem("Cash", Color(0xFF8A2EE8), 12_400),
                AllocItem("Vehicles", Color(0xFF9333EA), 5_000),
            ),
            modifier = Modifier.padding(16.dp),
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFEEF0F6)
@Composable
internal fun AllocationCardLiabilitiesPreview() {
    NetWorthCalculatorTheme {
        AllocationCard(
            total = 127_550,
            items = listOf(
                AllocItem("Property", Color(0xFF7C3AED), 108_200),
                AllocItem("Vehicle", Color(0xFF9333EA), 12_300),
                AllocItem("Revolving", Color(0xFFA855F7), 4_850),
                AllocItem("Education", Color(0xFFC026D3), 2_200),
            ),
            modifier = Modifier.padding(16.dp),
        )
    }
}
