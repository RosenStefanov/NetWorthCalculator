package com.rosenstefanov.networthcalculator.feature.dashboard.impl.ui.onboarding

import android.content.Context

object OnboardingPreferences {
    private const val PREFS = "networth_onboarding"
    private const val KEY_ONBOARDED = "onboarded"

    fun isOnboarded(context: Context): Boolean =
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE).getBoolean(KEY_ONBOARDED, false)

    fun setOnboarded(context: Context) {
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .edit()
            .putBoolean(KEY_ONBOARDED, true)
            .apply()
    }
}
