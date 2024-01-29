package com.kliachenko.unscramblegame.load.data

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WordsService {
    @GET("word")
    fun load(@Query("number") number: Int = 10) : Call<List<String>>

}