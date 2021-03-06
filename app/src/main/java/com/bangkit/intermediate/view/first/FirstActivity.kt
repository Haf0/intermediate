package com.bangkit.intermediate.view.first

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.bangkit.intermediate.databinding.ActivityFirstBinding
import com.bangkit.intermediate.preference.SessionPreference
import com.bangkit.intermediate.preference.ViewModelFactory
import com.bangkit.intermediate.view.home.HomeActivity
import com.bangkit.intermediate.view.login.LoginActivity
import com.bangkit.intermediate.view.register.RegisterActivity
import com.bangkit.intermediate.viewmodel.HomeViewModel.HomeVM

class FirstActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFirstBinding
    private lateinit var homeVM: HomeVM
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFirstBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        supportActionBar?.hide()
        homeVM = ViewModelProvider(
            this,
            ViewModelFactory(SessionPreference.getInstance(dataStore))
        )[HomeVM::class.java]

        homeVM.getUser().observe(this){
            if(it.token.isNotEmpty()){
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            }
        }

        with(binding){
            btnToLogin.setOnClickListener {
                startActivity(Intent(this@FirstActivity, LoginActivity::class.java))
            }
            btnToRegister.setOnClickListener {
                startActivity(Intent(this@FirstActivity,RegisterActivity::class.java))
            }
        }

        val textYour = ObjectAnimator.ofFloat(binding.tvYour, View.ALPHA, 1f).setDuration(500)
        val textShare = ObjectAnimator.ofFloat(binding.tvShare, View.ALPHA, 1f).setDuration(500)
        val textStory = ObjectAnimator.ofFloat(binding.tvStory, View.ALPHA, 1f).setDuration(500)

        AnimatorSet().apply {
            playSequentially(textShare,textYour,textStory)
            start()
        }

    }
}