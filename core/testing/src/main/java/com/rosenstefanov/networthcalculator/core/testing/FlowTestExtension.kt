package com.rosenstefanov.networthcalculator.core.testing

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.Flow

suspend fun <T> Flow<T>.assertEmits(vararg values: T) {
    test {
        values.forEach { expected ->
            assertThat(awaitItem()).isEqualTo(expected)
        }
        cancelAndConsumeRemainingEvents()
    }
}

suspend fun <T> Flow<T>.assertFirst(predicate: (T) -> Boolean) {
    test {
        assertThat(predicate(awaitItem())).isTrue()
        cancelAndConsumeRemainingEvents()
    }
}

suspend fun <T> Flow<T>.assertFirstEquals(value: T) {
    test {
        assertThat(awaitItem()).isEqualTo(value)
        cancelAndConsumeRemainingEvents()
    }
}
