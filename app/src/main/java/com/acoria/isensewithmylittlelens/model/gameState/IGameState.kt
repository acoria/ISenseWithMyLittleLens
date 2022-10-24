package com.acoria.isensewithmylittlelens.model.gameState

import com.acoria.isensewithmylittlelens.model.Item

interface IGameState {
    val itemStates: List<IItemState>
    fun wasItemFound(item: Item): Boolean
    fun isGameFinished(): Boolean
    fun registerOnGameFinished(listener: () -> Unit)
}