package com.acoria.isensewithmylittlelens.ui.rendering

import android.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import com.acoria.isensewithmylittlelens.R
import com.acoria.isensewithmylittlelens.core.constraintLayouting.ConstraintLayoutHorizontalListAligner
import com.acoria.isensewithmylittlelens.model.gameState.IGameState

class GameStateRenderer(
    private val gameState: IGameState,
    private val parentLayout: ConstraintLayout
) :
    IGameStateRenderer {

    private val itemTextViewIds = mutableListOf<Int>()
    private val itemStateRenderer by lazy { ItemStatesRenderer(parentLayout) }
    private var initialized = false
    private val itemStateChangeListener = render()

    override fun render() {
        if (!initialized) {
            gameState.itemStates.forEach { itemState ->
                itemState.registerOnItemStateChange { itemStateChangeListener }
                val itemTextViewId = itemStateRenderer.render(itemState)
                itemTextViewIds.add(itemTextViewId)
            }
            addConstraintsToItemTextViews()
            initialized = true
        }
        checkForFinishedGame()
    }

    private fun checkForFinishedGame() {
        if (gameState.isGameFinished()) {
            gameState.itemStates.forEach { itemState -> itemState.unregisterFromItemStateChange { itemStateChangeListener } }
            displayFinishedGame()
        }
    }

    private fun displayFinishedGame() {
        AlertDialog.Builder(parentLayout.context)
            .setIcon(R.drawable.observe_ant)
            .setMessage("Congraz! You finished the game")
            .setPositiveButton("Again!") { _, _ -> }
            .show()
    }

    private fun addConstraintsToItemTextViews() {
        ConstraintLayoutHorizontalListAligner().align(
            parentLayout,
            itemTextViewIds,
            3
        )
    }
}