package com.bangkit.intermediate.preference

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bangkit.intermediate.viewmodel.AddStoryViewModel.addStoryVM
import com.bangkit.intermediate.viewmodel.HomeViewModel.HomeVM
import com.bangkit.intermediate.viewmodel.LoginViewModel.LoginVM
import com.bangkit.intermediate.viewmodel.RegisterViewModel.RegisteVM

class ViewModelFactory(private val pref:SessionPreference) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginVM::class.java) -> {
                LoginVM(pref) as T
            }
            modelClass.isAssignableFrom(HomeVM::class.java)->{
                HomeVM(pref) as T
            }
            modelClass.isAssignableFrom(addStoryVM::class.java)->{
                addStoryVM(pref) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}