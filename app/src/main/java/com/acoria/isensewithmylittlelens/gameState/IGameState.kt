package com.acoria.isensewithmylittlelens.gameState

import com.acoria.isensewithmylittlelens.gameState.IItemState
import com.acoria.isensewithmylittlelens.items.Item

interface IGameState {
    val itemStates: List<IItemState>
    fun wasItemFound(item: Item): Boolean
    fun isGameFinished(): Boolean
    fun registerOnGameFinished(listener: () -> Unit)
}