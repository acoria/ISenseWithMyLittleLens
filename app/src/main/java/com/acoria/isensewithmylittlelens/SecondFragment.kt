package com.acoria.isensewithmylittlelens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.acoria.isensewithmylittlelens.databinding.FragmentSecondBinding
import com.acoria.isensewithmylittlelens.gameState.GameState
import com.acoria.isensewithmylittlelens.gameState.IGameState
import com.acoria.isensewithmylittlelens.items.Item
import com.acoria.isensewithmylittlelens.rendering.GameStateRenderer

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var gameState: IGameState

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeGameState()
        binding.testButton.setOnClickListener {
            gameState.itemStates.forEach { itemState -> itemState.found = true }
        }
    }

    private fun initializeGameState() {
        binding.constLayoutItems.removeAllViews()
        gameState = GameState(
            mutableListOf(
                Item("Plant")
//                Item("Spoon"),
//                Item("Fruit"),
//                Item("Screen"),
//                Item("Keyboard"),
//                Item("Mouse"),
//                Item("Cup")
            )
        )
        gameState.registerOnGameFinished { initializeGameState() }
        GameStateRenderer(gameState, binding.constLayoutItems).render()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}