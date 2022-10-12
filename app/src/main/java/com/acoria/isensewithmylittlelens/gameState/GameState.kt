package com.acoria.isensewithmylittlelens.gameState

import com.acoria.isensewithmylittlelens.gameState.IGameState
import com.acoria.isensewithmylittlelens.gameState.ItemState
import com.acoria.isensewithmylittlelens.items.Item

class GameState(items: List<Item>) : IGameState {

    override val itemStates: List<ItemState>
    private val gameFinishedListeners = mutableListOf<() -> Unit>()

    init {
        val itemStatesInit = mutableListOf<ItemState>()
        items.forEach {
            ItemState(it).let { itemState ->
                itemStatesInit.add(itemState)
                itemState.registerOnItemStateChange { checkForFinishedGame() }
            }
        }
        itemStates = itemStatesInit
    }

    override fun wasItemFound(item: Item): Boolean {
        return itemStates.find { it.equals(item) }?.found ?: false
    }

    override fun isGameFinished(): Boolean {
        itemStates.forEach { itemState ->
            if (!itemState.found) {
                return false
            }
        }
        return true
    }

    override fun registerOnGameFinished(listener: () -> Unit) {
        gameFinishedListeners.add(listener)
    }

    private fun checkForFinishedGame() {
        if (isGameFinished()) {
            gameFinishedListeners.forEach { it.invoke() }
        }
    }
}