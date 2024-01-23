package com.kliachenko.unscramblegame

import android.view.View
import com.kliachenko.unscramblegame.databinding.ActivityMainBinding
import java.io.Serializable

interface UiState : Serializable{
    fun show(binding: ActivityMainBinding)
    fun skip(viewModel: SkipActions): UiState = viewModel.skip()

    data class Initial(
        private val counter: String,
        private val score: Int,
        private val shuffleWord: String
    ) : UiState {
        override fun show(binding: ActivityMainBinding) {
            with(binding) {
                scoreTextView.text = scoreTextView.context.getString(R.string.score, score)
                counterTextView.text = counter
                inputEditText.setText("")
                inputLayout.error = ""
                inputLayout.isErrorEnabled = false
                submitButton.isEnabled = false
                shuffledWordTextView.text = shuffleWord
                inputLayout.visibility = View.VISIBLE
                submitButton.visibility = View.VISIBLE
                counterTextView.visibility = View.VISIBLE
                skipButton.setText(R.string.skip)
            }
        }
    }
    object Error: UiState {
        override fun show(binding: ActivityMainBinding) {
            with(binding) {
                inputLayout.error = inputLayout.context.getString(R.string.error_message)
                inputLayout.isErrorEnabled = true
                submitButton.isEnabled = false
                skipButton.setText(R.string.skip)
            }
        }
    }
    object ValidInput : UiState {
        override fun show(binding: ActivityMainBinding) {
            with(binding) {
                submitButton.isEnabled = true
                inputLayout.error = ""
                inputLayout.isErrorEnabled = false
                skipButton.setText(R.string.skip)
            }
        }
    }
    object InvalidInput : UiState {
        override fun show(binding: ActivityMainBinding) {
            with(binding) {
                inputLayout.error = ""
                inputLayout.isErrorEnabled = false
                submitButton.isEnabled = false
                skipButton.setText(R.string.skip)
            }
        }
    }
    data class GameOver(private val score: Int) : UiState {
        override fun show(binding: ActivityMainBinding) = with(binding) {
            inputEditText.setText("")
                scoreTextView.text = scoreTextView.context.getString(R.string.score, score)
                inputLayout.visibility = View.INVISIBLE
                submitButton.visibility = View.INVISIBLE
                counterTextView.visibility = View.INVISIBLE
                skipButton.setText(R.string.restart)
                shuffledWordTextView.setText(R.string.game_over)

            }
        override fun skip(viewModel: SkipActions) = viewModel.restart()
    }
}