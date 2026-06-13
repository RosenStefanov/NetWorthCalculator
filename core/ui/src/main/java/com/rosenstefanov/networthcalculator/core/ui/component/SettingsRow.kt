package com.rosenstefanov.networthcalculator.core.ui.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rosenstefanov.networthcalculator.core.ui.icon.NetWorthIcons
import com.rosenstefanov.networthcalculator.core.ui.theme.JakartaSans
import com.rosenstefanov.networthcalculator.core.ui.theme.NetWorthTheme

@Composable
fun SettingsRow(
    @DrawableRes icon: Int,
    name: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    desc: String? = null,
    value: String? = null,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 14.dp),
        horizontalArrangement = Arrangement.spacedBy(13.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(RoundedCornerShape(11.dp))
                .background(NetWorthTheme.extendedColors.accentTint),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                painter = painterResource(icon),
                contentDescription = null,
                tint = NetWorthTheme.extendedColors.accent,
                modifier = Modifier.size(19.dp),
            )
        }

        Column(Modifier.weight(1f)) {
            Text(
                text = name,
                fontFamily = JakartaSans,
                fontSize = 14.5.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface,
            )
            if (desc != null) {
                Text(
                    text = desc,
                    fontFamily = JakartaSans,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = NetWorthTheme.extendedColors.inkSub,
                    modifier = Modifier.padding(top = 1.dp),
                )
            }
        }

        if (value != null) {
            Text(
                text = value,
                fontFamily = JakartaSans,
                fontSize = 13.5.sp,
                fontWeight = FontWeight.SemiBold,
                color = NetWorthTheme.extendedColors.inkSub,
            )
        }

        Icon(
            painter = painterResource(NetWorthIcons.ChevronRight),
            contentDescription = null,
            tint = NetWorthTheme.extendedColors.chevron,
            modifier = Modifier.size(18.dp),
        )
    }
}
