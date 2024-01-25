package com.kliachenko.unscramblegame

import android.app.Application
import androidx.lifecycle.ViewModel
import com.kliachenko.unscramblegame.game.GameRepository
import com.kliachenko.unscramblegame.game.GameViewModel
import com.kliachenko.unscramblegame.game.PermanentStorage
import com.kliachenko.unscramblegame.game.Shuffle
import com.kliachenko.unscramblegame.main.MainViewModel
import java.lang.IllegalStateException

class GameApp : Application() {


    fun <T : ViewModel> viewModel(clasz: Class<out T>): T {
        val isRelease = !BuildConfig.DEBUG
        val shuffle = if (isRelease) Shuffle.Base() else Shuffle.Reversed()
        val wordsCount = if (isRelease) 10 else 2

        return when (clasz) {
            MainViewModel::class.java -> MainViewModel()
            GameViewModel::class.java -> GameViewModel(
                GameRepository.Base(
                    shuffle = shuffle,
                    wordsCount = wordsCount,
                    permanentStorage = PermanentStorage.Base(this)
                )
            )

            else -> {throw IllegalStateException()}
        } as T

    }

}

