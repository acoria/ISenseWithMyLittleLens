package com.acoria.isensewithmylittlelens.gameState

import com.acoria.isensewithmylittlelens.items.Item

interface IItemState {
    val item: Item
    var found: Boolean
    fun registerOnItemStateChange(listener: (IItemState) -> Unit)
    fun unregisterFromItemStateChange(listener: (IItemState) -> Unit)
}