package com.bangkit.intermediate.view.home

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bangkit.intermediate.api.ApiService
import com.bangkit.intermediate.model.stories.ListStoryItem
import com.bangkit.intermediate.preference.SessionPreference
import kotlinx.coroutines.flow.first

class StoryPagingSource(private val apiService: ApiService, private val sessionPreference: SessionPreference) : PagingSource<Int, ListStoryItem>() {
    override fun getRefreshKey(state: PagingState<Int, ListStoryItem>): Int? {
        return state.anchorPosition?.let {
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ListStoryItem> {
        return try {
            val position = params.key ?: PAGE
            val token = sessionPreference.getUser().first().token
            if (token.isNotEmpty()){
                val responseData = apiService.getAllStories(token, position,params.loadSize)
                if (responseData.isSuccessful){
                    Log.d("storypaging", "load: ${responseData.body()}")
                    LoadResult.Page(
                        data = responseData.body()?.listStory ?: emptyList(),
                        prevKey = if (position == PAGE) null else position - 1,
                        nextKey = if (responseData.body()?.listStory.isNullOrEmpty()) null else position+1
                    )
                }else{
                    LoadResult.Error(Exception("Failed"))
                }
            }else{
                LoadResult.Error(Exception("Failed"))
            }


        }catch (e: Exception){
            return LoadResult.Error(e)
        }
    }

    private companion object{
        const val PAGE = 1
    }
}