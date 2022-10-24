package com.acoria.isensewithmylittlelens.model.gameState

import com.acoria.isensewithmylittlelens.model.Item

interface IItemState {
    val item: Item
    var found: Boolean
    fun registerOnItemStateChange(listener: (IItemState) -> Unit)
    fun unregisterFromItemStateChange(listener: (IItemState) -> Unit)
}