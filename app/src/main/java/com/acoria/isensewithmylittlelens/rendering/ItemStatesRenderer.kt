package com.acoria.isensewithmylittlelens.rendering

import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.acoria.isensewithmylittlelens.constraintLayouting.ConstraintApplier
import com.acoria.isensewithmylittlelens.gameState.IItemState
import com.acoria.isensewithmylittlelens.uiElements.GameUIElementFactory

class ItemStatesRenderer(private val parentLayout: ConstraintLayout) : IItemStatesRenderer {

    private val itemInfos = mutableListOf<ItemInfo>()
    private val constraintApplier by lazy { ConstraintApplier() }
    private val uiElementFactory by lazy { GameUIElementFactory() }
    private var itemStateChangeListener: (newItemState: IItemState) -> Unit =
        { newItemState -> render(newItemState) }

    override fun render(itemState: IItemState): Int {
        val itemInfo = itemInfos.find { it.itemState == itemState }
            ?: ItemInfo(itemState).apply { itemInfos.add(this) }

        if (itemInfo.itemTextViewId == null) {
            createItemTextView(itemInfo)
            itemState.registerOnItemStateChange(itemStateChangeListener)
        }
        toggleTickImage(itemInfo)
        return itemInfo.itemTextViewId!!
    }

    private fun createTickImageView(itemInfo: ItemInfo) {
        val tickImageView = uiElementFactory.createTickImageView(parentLayout.context)
        itemInfo.tickImageViewId = tickImageView.id
        parentLayout.addView(tickImageView)
        constraintApplier.applyConstraints(parentLayout) { constraintSet, _ ->
            constraintSet.connect(
                tickImageView.id,
                ConstraintSet.TOP,
                itemInfo.itemTextViewId!!,
                ConstraintSet.TOP
            )
            constraintSet.connect(
                tickImageView.id,
                ConstraintSet.BOTTOM,
                itemInfo.itemTextViewId!!,
                ConstraintSet.BOTTOM
            )
            constraintSet.connect(
                tickImageView.id,
                ConstraintSet.END,
                itemInfo.itemTextViewId!!,
                ConstraintSet.START
            )
        }
    }

    private fun createItemTextView(itemInfo: ItemInfo) {
        val itemTextView =
            uiElementFactory.createItemTextView(itemInfo.itemState, parentLayout.context)
        itemInfo.itemTextViewId = itemTextView.id
        parentLayout.addView(itemTextView)
    }

    private fun toggleTickImage(itemInfo: ItemInfo) {
        if (itemInfo.itemState.found) {
            showTickImage(itemInfo)
        } else {
            hideTickImage(itemInfo)
        }
    }

    private fun hideTickImage(itemInfo: ItemInfo) {
        itemInfo.tickImageViewId?.apply {
            parentLayout.findViewById<ImageView>(this).visibility = View.GONE
        }
    }

    private fun showTickImage(itemInfo: ItemInfo) {
        val tickImageViewId = itemInfo.tickImageViewId
        if (tickImageViewId == null) {
            createTickImageView(itemInfo)
        } else {
            parentLayout.findViewById<ImageView>(tickImageViewId).visibility = View.VISIBLE
        }
    }

    class ItemInfo(var itemState: IItemState) {
        var itemTextViewId: Int? = null
        var tickImageViewId: Int? = null
    }
}