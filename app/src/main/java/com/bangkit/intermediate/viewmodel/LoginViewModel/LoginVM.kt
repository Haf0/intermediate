package com.bangkit.intermediate.viewmodel.LoginViewModel

import android.util.Log
import androidx.lifecycle.*
import com.bangkit.intermediate.api.ApiConfig
import com.bangkit.intermediate.model.Login.LoginResponse
import com.bangkit.intermediate.model.Login.UserSessionModel
import com.bangkit.intermediate.preference.SessionPreference
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginVM(private val pref: SessionPreference) : ViewModel() {
    private val _loginResponse = MutableLiveData<LoginResponse>()
    val loginResponse: LiveData<LoginResponse> = _loginResponse

    fun saveUser(sessionModel: UserSessionModel){
        viewModelScope.launch {
            pref.saveUser(sessionModel)
        }
    }

    fun login() {
        viewModelScope.launch {
            pref.login()
        }
    }

    fun LoginAccounts( email: String, password: String){
        val client = ApiConfig.getApiService().loginAccount(email, password)

        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                if (response.isSuccessful){
                    _loginResponse.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.d("error login", t.message.toString())
            }

        }
        )

    }
}