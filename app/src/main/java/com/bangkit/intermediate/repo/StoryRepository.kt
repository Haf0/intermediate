package com.bangkit.intermediate.repo

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.bangkit.intermediate.api.ApiService
import com.bangkit.intermediate.model.stories.ListStoryItem
import com.bangkit.intermediate.preference.SessionPreference
import com.bangkit.intermediate.view.home.StoryPagingSource

class StoryRepository(private val apiService: ApiService, private val sessionPreference: SessionPreference) {

    fun getStory():LiveData<PagingData<ListStoryItem>>{
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                StoryPagingSource(apiService, sessionPreference)
            }
        ).liveData
    }
}