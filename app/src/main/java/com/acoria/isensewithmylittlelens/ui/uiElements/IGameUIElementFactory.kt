package com.acoria.isensewithmylittlelens.ui.uiElements

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import com.acoria.isensewithmylittlelens.model.gameState.IItemState

interface IGameUIElementFactory {
    fun createItemTextView(itemState: IItemState, context: Context): TextView
    fun createTickImageView(context: Context): ImageView
}