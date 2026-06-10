package com.rosenstefanov.networthcalculator.core.navigation

import androidx.navigation3.runtime.NavKey
import com.google.common.truth.Truth.assertThat
import kotlinx.serialization.Serializable
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class NavigatorTest {

    @Serializable
    private data object TabA : NavKey

    @Serializable
    private data object TabB : NavKey

    @Serializable
    private data class Detail(val id: Long) : NavKey

    private val tabs = listOf<NavKey>(TabA, TabB)

    @Test
    fun `starts on first top-level route`() {
        // Given
        val navigator = Navigator(tabs)

        // When

        // Then
        assertThat(navigator.currentTab).isEqualTo(TabA)
        assertThat(navigator.currentBackStack.toList()).containsExactly(TabA)
    }

    @Test
    fun `navigate to top-level route switches tab`() {
        // Given
        val navigator = Navigator(tabs)

        // When
        navigator.navigate(TabB)

        // Then
        assertThat(navigator.currentTab).isEqualTo(TabB)
        assertThat(navigator.currentBackStack.toList()).containsExactly(TabB)
    }

    @Test
    fun `currentTabIndex tracks the selected tab and round-trips`() {
        // Given
        val navigator = Navigator(tabs)
        assertThat(navigator.currentTabIndex).isEqualTo(0)

        // When
        navigator.navigate(TabB)

        // Then - index reflects selection, and restoring from it reselects the same tab
        assertThat(navigator.currentTabIndex).isEqualTo(1)
        val restored = Navigator(tabs).apply { navigate(tabs[navigator.currentTabIndex]) }
        assertThat(restored.currentTab).isEqualTo(TabB)
    }

    @Test
    fun `navigate to non-top-level route pushes onto current tab`() {
        // Given
        val navigator = Navigator(tabs)

        // When
        navigator.navigate(Detail(42))

        // Then
        assertThat(navigator.currentTab).isEqualTo(TabA)
        assertThat(navigator.currentBackStack.toList())
            .containsExactly(TabA, Detail(42))
            .inOrder()
    }

    @Test
    fun `goBack pops from current tab`() {
        // Given
        val navigator = Navigator(tabs)
        navigator.navigate(Detail(1))

        // When
        navigator.goBack()

        // Then
        assertThat(navigator.currentBackStack.toList()).containsExactly(TabA)
    }

    @Test
    fun `goBack does nothing at tab root`() {
        // Given
        val navigator = Navigator(tabs)

        // When
        navigator.goBack()

        // Then
        assertThat(navigator.currentTab).isEqualTo(TabA)
        assertThat(navigator.currentBackStack.toList()).containsExactly(TabA)
    }

    @Test
    fun `each tab has independent back stack`() {
        // Given
        val navigator = Navigator(tabs)
        navigator.navigate(Detail(1))

        // When
        navigator.navigate(TabB)
        navigator.navigate(TabA)

        // Then
        assertThat(navigator.currentBackStack.toList())
            .containsExactly(TabA, Detail(1))
            .inOrder()
    }

    @Test
    fun `constructor rejects empty top-level route list`() {
        // Given

        // When

        // Then
        assertThrows<IllegalArgumentException> {
            Navigator(emptyList())
        }
    }
}
