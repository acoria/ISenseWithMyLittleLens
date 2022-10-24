package com.acoria.isensewithmylittlelens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.acoria.isensewithmylittlelens.databinding.FragmentSecondBinding
import com.acoria.isensewithmylittlelens.game.Game
import com.acoria.isensewithmylittlelens.model.gameState.GameState
import com.acoria.isensewithmylittlelens.model.gameState.IGameState
import com.acoria.isensewithmylittlelens.model.Item
import com.acoria.isensewithmylittlelens.ui.rendering.GameStateRenderer

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val game = Game(binding.constLayoutItems)
        binding.testButton.setOnClickListener {
            game.gameState.itemStates.forEach { itemState -> itemState.found = true }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}