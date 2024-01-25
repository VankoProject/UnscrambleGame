package com.kliachenko.unscramblegame.load

interface LoadCallback {

    fun success()
    fun error(msg: String)

}
