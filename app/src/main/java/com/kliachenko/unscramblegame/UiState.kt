package com.kliachenko.unscramblegame

import android.view.View
import com.kliachenko.unscramblegame.databinding.ActivityMainBinding

interface UiState {
    fun show(binding: ActivityMainBinding)
    fun skip(viewModel: SkipActions): UiState = viewModel.skip()
    abstract class Abstract(
        private val counter: String,
        private val score: Int,
        private val shuffleWord: String
    ) : UiState {
        override fun show(binding: ActivityMainBinding) = with(binding) {
            counterTextView.text = counter
            scoreTextView.text = scoreTextView.context.getString(R.string.score, score)
            shuffledWordTextView.text = shuffleWord
            skipButton.setText(R.string.skip)
            inputLayout.error = ""
        }
    }

    data class Initial(
        private val counter: String,
        private val score: Int,
        private val shuffleWord: String
    ) : Abstract(counter, score, shuffleWord) {
        override fun show(binding: ActivityMainBinding) {
            super.show(binding)
            with(binding) {
                submitButton.isEnabled = false
                inputEditText.setText("")
                inputLayout.error = ""
                inputLayout.isErrorEnabled = false
                inputLayout.visibility = View.VISIBLE
                submitButton.visibility = View.VISIBLE
                counterTextView.visibility = View.VISIBLE
                skipButton.setText(R.string.skip)
            }
        }
    }

    data class ValidInput(
        private val counter: String,
        private val score: Int,
        private val shuffleWord: String
    ) : Abstract(counter, score, shuffleWord) {
        override fun show(binding: ActivityMainBinding) {
            super.show(binding)
            with(binding) {
                submitButton.isEnabled = true
                inputLayout.error = ""
                inputLayout.isErrorEnabled = false
            }
        }
    }

    data class Error(
        private val counter: String,
        private val score: Int,
        private val shuffleWord: String
    ) : Abstract(counter, score, shuffleWord) {
        override fun show(binding: ActivityMainBinding) {
            super.show(binding)
            with(binding) {
                submitButton.isEnabled = false
                inputLayout.error = inputLayout.context.getString(R.string.error_message)
                inputLayout.isErrorEnabled = true
            }
        }
    }

    data class GameOver(private val score: Int) :
        Abstract(counter = "", score, shuffleWord = "Game over") {
        override fun show(binding: ActivityMainBinding) {
            super.show(binding)
            with(binding) {
                inputLayout.visibility = View.INVISIBLE
                submitButton.visibility = View.INVISIBLE
                counterTextView.visibility = View.INVISIBLE
                skipButton.setText(R.string.restart)
                shuffledWordTextView.setText(R.string.game_over)
            }
        }

        override fun skip(viewModel: SkipActions): UiState {
            return viewModel.restart()
        }
    }

    data class InvalidInput(
        private val counter: String,
        private val score: Int,
        private val shuffleWord: String
    ) : Abstract(counter, score, shuffleWord) {
        override fun show(binding: ActivityMainBinding) {
            super.show(binding)
            with(binding) {
                submitButton.isEnabled = false
                inputLayout.error = ""
                inputLayout.isErrorEnabled = false
            }
        }
    }
}