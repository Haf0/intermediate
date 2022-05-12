package com.bangkit.intermediate.view.home

import android.content.Context
import com.bangkit.intermediate.api.ApiConfig
import com.bangkit.intermediate.preference.SessionPreference
import com.bangkit.intermediate.repo.StoryRepository

object Injection {
    fun provideRepository(context: Context, sessionPreference: SessionPreference) : StoryRepository{
        val apiService = ApiConfig.getApiService()
        return StoryRepository(apiService, sessionPreference)
    }
}