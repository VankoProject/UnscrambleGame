package com.kliachenko.unscramblegame.main

import androidx.lifecycle.ViewModel
import com.kliachenko.unscramblegame.game.GameScreen
import com.kliachenko.unscramblegame.load.presentation.LoadScreen

class MainViewModel(
    private val repository: ScreenRepository.Read,
    private val navigation: NavigationObservable,
) : ViewModel() {

    fun screen() {
        val screen = if (repository.shouldLoadNewGame())
            LoadScreen
        else
            GameScreen
        navigation.navigate(screen)
    }

    fun startGettingUpdates(observer: Navigation) {
        navigation.updateNavigateObserver(observer)
    }

    fun stopGettingUpdates() {
        navigation.updateNavigateObserver(navigation)
    }

    fun notifyScreenObserved() {
        navigation.clear()
    }

}