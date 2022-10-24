package com.acoria.isensewithmylittlelens.game

import android.app.AlertDialog
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import com.acoria.isensewithmylittlelens.R
import com.acoria.isensewithmylittlelens.model.Item
import com.acoria.isensewithmylittlelens.model.gameState.GameState
import com.acoria.isensewithmylittlelens.model.gameState.IGameState
import com.acoria.isensewithmylittlelens.ui.rendering.GameStateRenderer

class Game(private val parentLayout: ConstraintLayout) {

    lateinit var gameState: IGameState
    private lateinit var gameStateRenderer: GameStateRenderer
    private lateinit var gameLayout: ConstraintLayout

    private val finishedGameInfoAlert: () -> Unit = {
        AlertDialog.Builder(parentLayout.context)
            .setView(ImageView(parentLayout.context).apply {
                id = View.generateViewId()
                setImageDrawable(context.getDrawable(R.drawable.ic_winner_jump))
            })
            .setMessage("Congraz! You finished the game!")
            .setPositiveButton("Again!") { dialogInterface, _ ->
                initializeGameState()
                dialogInterface.dismiss()
            }
            .setNegativeButton("No, thanks") { dialogInterface, _ ->
                dialogInterface.dismiss()
            }
            .create().show()
    }

    init {
        initializeGameState()
    }

    private fun initializeGameState() {
        if (this::gameStateRenderer.isInitialized) {
            parentLayout.removeView(gameLayout)
        }
        gameState = GameState(
            mutableListOf(
                Item("Plant"),
                Item("Spoon"),
                Item("Fruit"),
                Item("Screen"),
                Item("Keyboard"),
                Item("Mouse"),
                Item("Cup")
            )
        )
        gameState.registerOnGameFinished(finishedGameInfoAlert)
        gameLayout = createGameLayout()
        gameStateRenderer = GameStateRenderer(gameState, gameLayout)
        gameStateRenderer.render()
    }

    private fun createGameLayout(): ConstraintLayout {
        return ConstraintLayout(parentLayout.context).also { gameLayout ->
            gameLayout.id = View.generateViewId()
            parentLayout.addView(gameLayout)
            gameLayout.updateLayoutParams<ConstraintLayout.LayoutParams> {
                width = 0
                height = 0
                startToStart = parentLayout.id
                endToEnd = parentLayout.id
                topToTop = parentLayout.id
                bottomToBottom = parentLayout.id
            }
        }
    }
}