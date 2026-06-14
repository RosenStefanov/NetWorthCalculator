package com.rosenstefanov.networthcalculator.feature.dashboard.impl.ui.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.rosenstefanov.networthcalculator.core.ui.icon.NetWorthIcons
import com.rosenstefanov.networthcalculator.core.ui.theme.JakartaSans
import com.rosenstefanov.networthcalculator.core.ui.theme.NetWorthColors

@Composable
fun WelcomeScreen(onGetStarted: () -> Unit) {
    val gradient = Brush.linearGradient(
        colorStops = arrayOf(
            0f to NetWorthColors.Blue,
            0.5f to NetWorthColors.Indigo,
            1f to NetWorthColors.Purple,
        ),
        start = Offset.Zero,
        end = Offset(0f, Float.POSITIVE_INFINITY),
    )
    Box(
        Modifier
            .fillMaxSize()
            .background(gradient),
    ) {
        Box(
            Modifier
                .align(Alignment.TopEnd)
                .offset(x = 70.dp, y = 90.dp)
                .size(240.dp)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.08f)),
        )
        Box(
            Modifier
                .align(Alignment.BottomStart)
                .offset(x = (-60).dp, y = (-120).dp)
                .size(180.dp)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.06f)),
        )

        Column(
            Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(horizontal = 28.dp)
                .padding(bottom = 30.dp),
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Box(
                    modifier = Modifier
                        .size(96.dp)
                        .shadow(20.dp, RoundedCornerShape(28.dp), spotColor = Color.Black.copy(alpha = 0.3f))
                        .clip(RoundedCornerShape(28.dp))
                        .background(Color.White.copy(alpha = 0.18f)),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        painter = painterResource(NetWorthIcons.Calculator),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(54.dp),
                    )
                }
                Spacer(Modifier.height(30.dp))
                Text(
                    text = "Know exactly what you're worth",
                    fontFamily = JakartaSans,
                    fontSize = 31.sp,
                    fontWeight = FontWeight.ExtraBold,
                    lineHeight = 36.5.sp,
                    letterSpacing = (-0.025).em,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.widthIn(max = 300.dp),
                )
                Spacer(Modifier.height(16.dp))
                Text(
                    text = "Track every asset and debt in one place, and watch your net worth grow over time.",
                    fontFamily = JakartaSans,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Normal,
                    lineHeight = 23.sp,
                    color = Color.White.copy(alpha = 0.85f),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.widthIn(max = 280.dp),
                )
            }

            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Button(
                    onClick = onGetStarted,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                ) {
                    Text(
                        text = "Get started",
                        fontFamily = JakartaSans,
                        fontSize = 15.5.sp,
                        fontWeight = FontWeight.Bold,
                        color = NetWorthColors.Indigo,
                    )
                    Spacer(Modifier.width(9.dp))
                    Icon(
                        painter = painterResource(NetWorthIcons.ArrowRight),
                        contentDescription = null,
                        tint = NetWorthColors.Indigo,
                        modifier = Modifier.size(18.dp),
                    )
                }
                Text(
                    text = "No account needed · Your data stays on your device",
                    fontFamily = JakartaSans,
                    fontSize = 11.5.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.White.copy(alpha = 0.6f),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
internal fun WelcomeScreenPreview() {
    WelcomeScreen(onGetStarted = {})
}
