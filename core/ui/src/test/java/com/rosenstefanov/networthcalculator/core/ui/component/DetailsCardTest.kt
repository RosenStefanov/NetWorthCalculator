package com.rosenstefanov.networthcalculator.core.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rosenstefanov.networthcalculator.core.testing.ScreenSnapshotTest
import org.junit.Test

class DetailsCardTest : ScreenSnapshotTest() {

    @Test
    fun detailsCard_withDescription_light() = captureSnapshot {
        DetailsCard(
            category = "Real Estate",
            type = "Asset",
            added = "Mar 2021",
            description = "Family home in Austin. Primary residence, purchased 2021.",
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
        )
    }

    @Test
    fun detailsCard_noDescription_dark() = captureSnapshot(darkTheme = true) {
        DetailsCard(
            category = "Mortgage",
            type = "Liability",
            added = "Mar 2021",
            description = null,
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
        )
    }
}
