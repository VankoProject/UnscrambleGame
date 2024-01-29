package com.kliachenko.unscramblegame.load.data

import com.kliachenko.unscramblegame.game.GameScreen
import com.kliachenko.unscramblegame.load.presentation.LoadUiState
import com.kliachenko.unscramblegame.load.presentation.UiObservable
import com.kliachenko.unscramblegame.main.Navigation
import com.kliachenko.unscramblegame.main.ScreenRepository
import java.net.UnknownHostException

interface LoadRepository {
    suspend fun load(): LoadResult

    class Base(
        private val service: WordsService,
        private val cacheDataSource: WordsCacheDataSource.Save,
        private val screenRepository: ScreenRepository.Save,
        private val wordsCount: Int,
    ) : LoadRepository {
        override suspend fun load(): LoadResult = try {
            val response = service.load(wordsCount).execute()
            val body = response.body()!!
            if (body.isNotEmpty()) {
                cacheDataSource.save(body)
                screenRepository.saveNewGameStarted()
                LoadResult.Success
            } else {
                throw IllegalStateException("server exception")
            }
        } catch (e: Exception) {
            if (e is UnknownHostException)
                LoadResult.Error("no internet connection")
            else
                LoadResult.Error("service unavailable")
        }
    }
}

interface LoadResult {
    fun handle(navigation: Navigation, observable: UiObservable)

    data class Error(private val message: String) : LoadResult {
        override fun handle(navigation: Navigation, observable: UiObservable) {
            observable.updateUi(LoadUiState.Error(message))
        }
    }

    object Success : LoadResult {
        override fun handle(navigation: Navigation, observable: UiObservable) {
            navigation.navigate(GameScreen)
        }
    }
}