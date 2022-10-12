package com.acoria.isensewithmylittlelens.uiElements

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import com.acoria.isensewithmylittlelens.gameState.IItemState

interface IGameUIElementFactory {
    fun createItemTextView(itemState: IItemState, context: Context): TextView
    fun createTickImageView(context: Context): ImageView
}