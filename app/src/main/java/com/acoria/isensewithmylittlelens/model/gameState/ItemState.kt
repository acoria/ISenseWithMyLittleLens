package com.acoria.isensewithmylittlelens.model.gameState

import com.acoria.isensewithmylittlelens.model.Item

class ItemState(override val item: Item) : IItemState {

    private val itemStateChangeListener by lazy { mutableListOf<(ItemState) -> Unit>() }
    override var found: Boolean = false
        set(value) {
            field = value
            itemStateChangeListener.forEach {
                it(this)
            }
        }

    override fun registerOnItemStateChange(listener: (IItemState) -> Unit) {
        itemStateChangeListener.add(listener)
    }

    override fun unregisterFromItemStateChange(listener: (IItemState) -> Unit) {
        itemStateChangeListener.remove(listener)
    }
}