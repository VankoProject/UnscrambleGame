package com.kliachenko.unscramblegame

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import com.kliachenko.unscramblegame.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var uiState: UiState
    private lateinit var viewModel: GameViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = (application as GameApp).viewModel()

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
//        } else {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//                savedInstanceState.getSerializable(UiKey, UiState::class.java) as UiState
//            } else {
//                savedInstanceState.getSerializable(UiKey) as UiState
//            }
//        }

        }
    }

    private val watcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            TODO("Not yet implemented")
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            TODO("Not yet implemented")
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

    companion object {
        private const val UiKey = "uiState_key"
    }
}