package com.kliachenko.unscramblegame.game

import androidx.lifecycle.ViewModel

class GameViewModel(private val repository: GameRepository) : ViewModel(),SkipActions {

    fun init() = UiState.Initial(
        "${repository.currentWordPosition()}/${repository.maxWordsCount()}",
        repository.score(),
        repository.shuffleWord()
    )

    fun update(text: String) = if (text.length == repository.shuffleWord().length)
        UiState.ValidInput
    else
        UiState.InvalidInput

    fun submit(text: String) = if (repository.isTextCorrect(text)) {
        skip()
    } else
        UiState.Error

    override fun skip() = if (repository.isLastWord())
        UiState.GameOver(repository.score())
    else {
        repository.next()
        init()
    }

    override fun restart(): UiState {
        repository.restart()
        return init()
    }
}

//из-за того, что на кнопку навешано два действия - по дефолту скип, а в случае с геймовером - рестарт
interface SkipActions {
    fun skip(): UiState
    fun restart(): UiState

}