package com.kliachenko.unscramblegame

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.kliachenko.unscramblegame.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var uiState: UiState
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repository = GameRepository.Base()
        val viewModel = GameViewModel(repository)

        binding.submitButton.setOnClickListener {
            uiState = viewModel.submit(binding.inputEditText.text.toString())
            uiState.show(binding)
        }
        binding.skipButton.setOnClickListener {
            val newUiState = uiState.skip(viewModel)
            uiState = newUiState
            uiState.show(binding)
        }
        binding.inputEditText.doAfterTextChanged {
            uiState = viewModel.update(binding.inputEditText.text.toString())
            uiState.show(binding)
        }

        uiState = viewModel.init()
        uiState.show(binding)

    }
}