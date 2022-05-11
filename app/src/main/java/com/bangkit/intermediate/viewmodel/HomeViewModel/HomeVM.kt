package com.bangkit.intermediate.viewmodel.HomeViewModel

import android.util.Log
import androidx.lifecycle.*
import com.bangkit.intermediate.api.ApiConfig
import com.bangkit.intermediate.model.Login.LoginResponse
import com.bangkit.intermediate.model.Login.UserSessionModel
import com.bangkit.intermediate.model.stories.ListStoryItem
import com.bangkit.intermediate.model.stories.StoriesResponse
import com.bangkit.intermediate.preference.SessionPreference
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeVM(private val pref: SessionPreference) : ViewModel() {
    private val _list = MutableLiveData<StoriesResponse>()
    val list: LiveData<StoriesResponse> = _list

    fun getListStories(token:String){
        val client = ApiConfig.getApiService().getAllStories(token)

        client.enqueue(object : Callback<StoriesResponse> {
            override fun onResponse(
                call: Call<StoriesResponse>,
                response: Response<StoriesResponse>
            ) {
                if (response.isSuccessful){
                    _list.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<StoriesResponse>, t: Throwable) {
                Log.d("error register", t.message.toString())
            }

        }
        )
    }

    fun getUser(): LiveData<UserSessionModel> {
        return pref.getUser().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            pref.logout()
        }
    }


}