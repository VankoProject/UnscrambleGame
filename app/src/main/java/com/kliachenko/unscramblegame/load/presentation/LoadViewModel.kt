package com.kliachenko.unscramblegame.load.presentation

import androidx.lifecycle.ViewModel
import com.kliachenko.unscramblegame.load.data.LoadCallback
import com.kliachenko.unscramblegame.load.data.LoadRepository

class LoadViewModel(private val repository: LoadRepository) : ViewModel(), LoadCallback {
    fun load() {
        //TODO: show progressUiState
        repository.load(this)

    }

    fun init(isFirstRun: Boolean) {
        if (isFirstRun) load()
    }

    override fun success() {
    }

    override fun error(msg: String) {
    }

}