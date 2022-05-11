package com.bangkit.intermediate.view.login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.bangkit.intermediate.R
import com.bangkit.intermediate.databinding.ActivityMainBinding
import com.bangkit.intermediate.model.Login.UserSessionModel
import com.bangkit.intermediate.preference.SessionPreference
import com.bangkit.intermediate.preference.ViewModelFactory
import com.bangkit.intermediate.view.home.HomeActivity
import com.bangkit.intermediate.viewmodel.LoginViewModel.LoginVM


class LoginActivity : AppCompatActivity() {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    private lateinit var binding: ActivityMainBinding
    private lateinit var loginVM : LoginVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        loginVM = ViewModelProvider(
            this,
            ViewModelFactory(SessionPreference.getInstance(dataStore))
        )[LoginVM::class.java]




        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding){
            btnLogin.setOnClickListener {
                loginVM.LoginAccounts(
                    edEmailLogin.text.toString(),
                    edPassLogin.text.toString()
                )
                loginVM.login()
                loginVM.loginResponse.observe(this@LoginActivity){login->

                    saveSession(
                        UserSessionModel(
                        "${login.loginResult?.name}",
                        "Bearer ${login.loginResult?.token}",
                        true
                        )
                    )
                    val i = Intent(this@LoginActivity, HomeActivity::class.java)
                    i.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(i)


                }
            }
        }
    }
    fun saveSession(user:UserSessionModel){
        loginVM.saveUser(user)
    }
}