package com.kliachenko.unscramblegame.load.presentation

import com.kliachenko.unscramblegame.core.BaseViewModel
import com.kliachenko.unscramblegame.core.RunAsync
import com.kliachenko.unscramblegame.game.GameScreen
import com.kliachenko.unscramblegame.load.data.LoadRepository
import com.kliachenko.unscramblegame.main.NavigationObservable

class LoadViewModel(
    private val repository: LoadRepository,
    private val uiObservable: UiObservable,
    private val navigation: NavigationObservable,
    runAsync: RunAsync,
) : BaseViewModel(runAsync) {
    fun load() {
        uiObservable.updateUi(LoadUiState.Progress)
        runAsync({
            repository.load()
        }) { loadResult ->
            loadResult.handle(navigation, uiObservable)
        }
    }

    fun init(isFirstRun: Boolean) {
        if (isFirstRun) load()
    }

    fun startGettingUpdates(uiCallBack: UiCallBack) {
        uiObservable.updateObserver(uiCallBack)
    }

    fun stopGettingUpdates() {
        uiObservable.updateObserver(UiCallBack.Empty)
    }
}