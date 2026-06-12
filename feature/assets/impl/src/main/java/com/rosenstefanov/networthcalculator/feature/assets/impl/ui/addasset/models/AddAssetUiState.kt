package com.rosenstefanov.networthcalculator.feature.assets.impl.ui.addasset.models

sealed interface AddAssetUiState {

    // Placeholder form state — only the name field exists so far; more inputs come in a later chunk.
    data class Content(val name: String = "") : AddAssetUiState
}
