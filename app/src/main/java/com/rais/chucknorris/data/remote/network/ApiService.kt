package com.rais.chucknorris.data.remote.network

import com.rais.chucknorris.data.remote.response.JokesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("jokes/search")
    fun searchJokes(
        @Query("query") query: String
    ) : Call<JokesResponse>


}