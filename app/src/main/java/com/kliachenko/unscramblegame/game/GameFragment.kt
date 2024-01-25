package com.kliachenko.unscramblegame.game

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kliachenko.unscramblegame.ProvideViewModel
import com.kliachenko.unscramblegame.databinding.FragmentGameBinding

class GameFragment : Fragment() {

    private lateinit var uiState: UiState
    private lateinit var viewModel: GameViewModel

    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel =
            (requireActivity() as ProvideViewModel).viewModel(clasz = GameViewModel::class.java)

        binding.submitButton.setOnClickListener {
            uiState = viewModel.submit(binding.input.text())
            uiState.show(binding)
        }
        binding.skipButton.setOnClickListener {
            uiState =
                uiState.skip(viewModel) //вызываем текущий UiState и он уже сам решает что ему надо(скип или рестарт)
            uiState.show(binding)
        }

        if (savedInstanceState == null) {
            uiState = viewModel.init()
            uiState.show(binding)
        }
    }

    private val watcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

        override fun afterTextChanged(s: Editable?) {
            val uiState = viewModel.update(binding.input.text())
            uiState.show(binding)
        }
    }

    override fun onResume() {
        super.onResume()
        binding.input.binding.inputEditText.addTextChangedListener(watcher)
    }

    override fun onPause() {
        super.onPause()
        binding.input.binding.inputEditText.removeTextChangedListener(watcher)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}