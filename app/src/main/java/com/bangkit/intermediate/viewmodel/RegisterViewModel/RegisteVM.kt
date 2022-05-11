package com.bangkit.intermediate.viewmodel.RegisterViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.intermediate.api.ApiConfig
import com.bangkit.intermediate.model.Register.RegisterResponse
import com.bangkit.intermediate.preference.SessionPreference
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisteVM() : ViewModel() {
    private val _registerResponse = MutableLiveData<RegisterResponse>()
    val registerResponse: LiveData<RegisterResponse> = _registerResponse


  fun registerAccount(name: String, email: String, password: String){
      val client = ApiConfig.getApiService().registerAccount(name, email, password)

      client.enqueue(object : Callback<RegisterResponse>{
          override fun onResponse(
              call: Call<RegisterResponse>,
              response: Response<RegisterResponse>
          ) {
              if (response.isSuccessful){
                  _registerResponse.postValue(response.body())
              }
          }

          override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
              Log.d("error register", t.message.toString())
          }

      }
      )
  }
}