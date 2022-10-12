package com.acoria.isensewithmylittlelens.rendering

import com.acoria.isensewithmylittlelens.gameState.IItemState

interface IItemStatesRenderer {
    fun render(itemState: IItemState): Int
}