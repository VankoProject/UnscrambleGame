package com.kliachenko.unscramblegame.load

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WordsService {

    //https://random-word-api.herokuapp.com/word?number=10
    @GET("word")
    fun load(@Query("number") number: Int = 10) : Call<List<String>>
}
