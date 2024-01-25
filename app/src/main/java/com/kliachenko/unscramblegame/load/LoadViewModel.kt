package com.kliachenko.unscramblegame.load

import androidx.lifecycle.ViewModel

class LoadViewModel(private val repository: LoadRepository) : ViewModel() {
    fun load() {
        //TODO: show progressUiState

        repository.load(object : LoadCallback {
            override fun success() {
                // TODO: navigation.navigate(GameScreen)
            }

            override fun error(msg: String) {
                // TODO: create ui state and return to ui
            }
        })

    }

    fun init(isFirstRun: Boolean) {
        if (isFirstRun) {
            load()
        }
    }

}