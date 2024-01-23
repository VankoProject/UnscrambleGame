package com.kliachenko.unscramblegame

import android.os.Bundle
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

    override fun onResume() {
        super.onResume()
        // TODO: subscribe textWatcher 
        binding.input.doAfterTextChanged {
            val uiState = viewModel.update(binding.input.text())
            uiState.show(binding)
        }

    }

//    override fun onSaveInstanceState(outState: Bundle) {
//        super.onSaveInstanceState(outState)
//        outState.putSerializable(UiKey, uiState)
//    }

    override fun onPause() {
        super.onPause()
        // TODO: unsubscribe textWatcher 
    }

    companion object {
        private const val UiKey = "uiState_key"
    }
}