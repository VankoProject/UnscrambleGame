package com.kliachenko.unscramblegame

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.kliachenko.unscramblegame.game.GameRepository
import com.kliachenko.unscramblegame.game.GameViewModel
import com.kliachenko.unscramblegame.game.LocalStorage
import com.kliachenko.unscramblegame.game.PermanentStorage
import com.kliachenko.unscramblegame.game.Shuffle
import com.kliachenko.unscramblegame.load.data.LoadRepository
import com.kliachenko.unscramblegame.load.presentation.LoadViewModel
import com.kliachenko.unscramblegame.load.data.WordsCacheDataSource
import com.kliachenko.unscramblegame.load.data.WordsService
import com.kliachenko.unscramblegame.main.MainViewModel
import com.kliachenko.unscramblegame.main.ScreenRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.IllegalStateException

class GameApp : Application(), ProvideViewModel {
    private lateinit var factory: ProvideViewModel.Factory

    override fun onCreate() {
        super.onCreate()
        val makeViewModel = ProvideViewModel.Base(this)
        factory = ProvideViewModel.Factory(makeViewModel)
    }

    override fun <T : ViewModel> viewModel(clasz: Class<out T>): T {
        return factory.viewModel(clasz)
    }

}

interface ProvideViewModel {
    fun <T : ViewModel> viewModel(clasz: Class<out T>): T

    class Factory(private val makeViewModel: ProvideViewModel) : ProvideViewModel {
        private val map = HashMap<Class<out ViewModel>, ViewModel>()
        override fun <T : ViewModel> viewModel(clasz: Class<out T>): T {
            return if (map.containsKey(clasz))
                map[clasz]
            else {
                val viewModel = makeViewModel.viewModel(clasz)
                map[clasz] = viewModel
                viewModel
            } as T
        }
    }

    class Base(context: Context) : ProvideViewModel {
        private val localStorage = LocalStorage.Base(context)
        private val screenRepository = ScreenRepository.Base(localStorage)
        private val cacheDataSource = WordsCacheDataSource.Base(localStorage, Gson())

        override fun <T : ViewModel> viewModel(clasz: Class<out T>): T {
            val isRelease = !BuildConfig.DEBUG
            val shuffle = if (isRelease) Shuffle.Base() else Shuffle.Reversed()
            val wordsCount = if (isRelease) 10 else 2


            return when (clasz) {
                LoadViewModel::class.java -> {
                    val logging = HttpLoggingInterceptor()
                    logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                    val client: OkHttpClient = OkHttpClient.Builder()
                        .addInterceptor(logging)
                        .build()

                    val retrofit = Retrofit.Builder()
                        .baseUrl("https://random-word-api.herokuapp.com/")
                        .client(client)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                    LoadViewModel(
                        LoadRepository.Base(
                            retrofit.create(WordsService::class.java),
                            cacheDataSource,
                            screenRepository
                        )
                    )
                }

                MainViewModel::class.java -> MainViewModel(screenRepository)

                GameViewModel::class.java -> GameViewModel(
                    GameRepository.Base(
                        shuffle = shuffle,
                        wordsCount = wordsCount,
                        permanentStorage = PermanentStorage.Base(localStorage),
                        cacheDataSource = cacheDataSource
                    )
                )

                else -> throw IllegalStateException()
            } as T
        }
    }
}
