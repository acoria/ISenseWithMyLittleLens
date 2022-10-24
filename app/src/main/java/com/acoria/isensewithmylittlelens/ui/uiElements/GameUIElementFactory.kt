package com.acoria.isensewithmylittlelens.ui.uiElements

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.acoria.isensewithmylittlelens.R
import com.acoria.isensewithmylittlelens.model.gameState.IItemState
import com.acoria.isensewithmylittlelens.ui.uiElements.IGameUIElementFactory

class GameUIElementFactory : IGameUIElementFactory {
    override fun createItemTextView(itemState: IItemState, context: Context): TextView {
        val itemTextView = TextView(context)
        itemTextView.id = View.generateViewId()
        itemTextView.textAlignment = TextView.TEXT_ALIGNMENT_CENTER
        setDefaultWrapContentAttributes(itemTextView)
        itemTextView.text = itemState.item.name
        return itemTextView
    }

    override fun createTickImageView(context: Context): ImageView {
        val tickImageView = ImageView(context).apply {
            id = View.generateViewId()
            setImageDrawable(context.getDrawable(R.drawable.checkbox_tick))
            setColorFilter(
                context.getColor(R.color.purple_brown),
                android.graphics.PorterDuff.Mode.SRC_IN
            )
        }
        setDefaultWrapContentAttributes(tickImageView)
        return tickImageView
    }

    private fun setDefaultWrapContentAttributes(view: View) {
        view.layoutParams = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.WRAP_CONTENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT
        )
    }

}