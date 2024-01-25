package com.kliachenko.unscramblegame.main

import androidx.lifecycle.ViewModel
import com.kliachenko.unscramblegame.game.GameScreen
import com.kliachenko.unscramblegame.load.LoadScreen

class MainViewModel(private  val repository: ScreenRepository): ViewModel() {

    fun screen(): Screen {
        return if (repository.shouldLoadNewGame())
            LoadScreen
        else
            GameScreen
    }

}