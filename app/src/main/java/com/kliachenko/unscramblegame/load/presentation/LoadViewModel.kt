package com.kliachenko.unscramblegame.load.presentation

import androidx.lifecycle.ViewModel
import com.kliachenko.unscramblegame.game.GameScreen
import com.kliachenko.unscramblegame.load.data.LoadCallback
import com.kliachenko.unscramblegame.load.data.LoadRepository
import com.kliachenko.unscramblegame.main.NavigationObservable

class LoadViewModel(
    private val repository: LoadRepository,
    private val uiObservable: UiObservable,
    private val navigation: NavigationObservable,
) : ViewModel(), LoadCallback {
    fun load() {
        uiObservable.updateUi(LoadUiState.Progress)
        repository.load(this)
    }

    fun init(isFirstRun: Boolean) {
        if (isFirstRun) load()
    }

    override fun success() {
        navigation.update(GameScreen)
    }

    override fun error(msg: String) {
        uiObservable.updateUi(LoadUiState.Error(message = msg))
    }

    fun startGettingUpdates(uiCallBack: UiCallBack) {
        uiObservable.updateObserver(uiCallBack)
    }

    fun stopGettingUpdates() {
        uiObservable.updateObserver(UiCallBack.Empty)
    }
}