package com.kliachenko.unscramblegame.core

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

interface RunAsync {
    fun <T : Any> start(
        coroutineScope: CoroutineScope,
        background: suspend () -> T,
        uiState: (T) -> Unit,
    ): Job

    class Base(
        private val dispatcherIO: CoroutineDispatcher = Dispatchers.IO,
        private val dispatcherMain: CoroutineDispatcher = Dispatchers.Main,
    ) : RunAsync {
        override fun <T : Any> start(
            coroutineScope: CoroutineScope,
            background: suspend () -> T,
            uiState: (T) -> Unit,
        ) = coroutineScope.launch(dispatcherIO) {
            val result = background.invoke()
            withContext(dispatcherMain) {
                uiState.invoke(result)
            }
        }
    }
}