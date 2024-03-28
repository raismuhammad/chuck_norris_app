package com.rais.chucknorris.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rais.chucknorris.data.remote.network.ApiConfig
import com.rais.chucknorris.data.remote.response.Jokes
import com.rais.chucknorris.data.remote.response.JokesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel : ViewModel() {

    private val _listJokes = MutableLiveData<ArrayList<Jokes>>()
    val listJokes: LiveData<ArrayList<Jokes>> = _listJokes

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    fun searchJokes(query: String) {
        try {
            val response = ApiConfig.getApiService().searchJokes(query)
            response.enqueue(object : Callback<JokesResponse> {
                override fun onResponse(call: Call<JokesResponse>, response: Response<JokesResponse>) {
                    _isLoading.value = false
                    val responseBody = response.body()
                    if (response.isSuccessful && responseBody != null) {
                        _listJokes.value = ArrayList(responseBody.result)
                    }
                }

                override fun onFailure(call: Call<JokesResponse>, t: Throwable) {
                    Log.e("TAG", "onFailure: ${t.message}")
                }
            })
        } catch (e: Exception) {
            Log.d("TAG", "search: ${e.message}")
        }
    }
}