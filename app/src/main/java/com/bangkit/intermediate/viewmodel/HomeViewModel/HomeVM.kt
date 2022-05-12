package com.bangkit.intermediate.viewmodel.HomeViewModel

import android.util.Log
import androidx.datastore.preferences.protobuf.ListValue
import androidx.lifecycle.*
import androidx.paging.PagingData
import com.bangkit.intermediate.api.ApiConfig
import com.bangkit.intermediate.model.Login.LoginResponse
import com.bangkit.intermediate.model.Login.UserSessionModel
import com.bangkit.intermediate.model.stories.ListStoryItem
import com.bangkit.intermediate.model.stories.StoriesResponse
import com.bangkit.intermediate.model.stories.StoriesResponseWithLocation
import com.bangkit.intermediate.preference.SessionPreference
import com.bangkit.intermediate.repo.StoryRepository
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeVM(private val pref: SessionPreference) : ViewModel() {
    private val _list = MutableLiveData<StoriesResponseWithLocation>()
    val list: LiveData<StoriesResponseWithLocation> = _list

    fun getListStoriesWithLocation(token:String){
        val client = ApiConfig.getApiService().getAllStorieswithLocation(token)

        client.enqueue(object : Callback<StoriesResponseWithLocation> {
            override fun onResponse(
                call: Call<StoriesResponseWithLocation>,
                response: Response<StoriesResponseWithLocation>
            ) {
                if (response.isSuccessful){
                    _list.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<StoriesResponseWithLocation>, t: Throwable) {
                Log.d("error getlist", t.message.toString())
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