package com.kliachenko.unscramblegame.load.data

interface LoadCallback {

    fun success()
    fun error(msg: String)

}
