package com.acoria.isensewithmylittlelens.ui.rendering

import com.acoria.isensewithmylittlelens.model.gameState.IItemState

interface IItemStatesRenderer {
    fun render(itemState: IItemState): Int
}