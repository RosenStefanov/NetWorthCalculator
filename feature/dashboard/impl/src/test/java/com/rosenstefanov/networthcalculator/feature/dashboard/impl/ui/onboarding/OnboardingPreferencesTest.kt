package com.rosenstefanov.networthcalculator.feature.dashboard.impl.ui.onboarding

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class OnboardingPreferencesTest {

    private val context: Context get() = ApplicationProvider.getApplicationContext()

    @Before
    fun clear() {
        context.getSharedPreferences("networth_onboarding", Context.MODE_PRIVATE)
            .edit().clear().commit()
    }

    @Test
    fun defaultsToNotOnboarded() {
        assertThat(OnboardingPreferences.isOnboarded(context)).isFalse()
    }

    @Test
    fun setOnboarded_persistsTheFlag() {
        OnboardingPreferences.setOnboarded(context)
        assertThat(OnboardingPreferences.isOnboarded(context)).isTrue()
    }
}
