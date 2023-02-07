package com.oncescaffold.ui.page.home.viewmodel

import android.util.Log
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.oncescaffold.bean.ArticleBean
import com.oncescaffold.ui.page.home.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject


@HiltViewModel
class HomePageViewModel @Inject constructor(private val homeRepository: HomeRepository): ViewModel() {


    val homePageViewModelState by mutableStateOf(HomePageViewModelState(pagingData = requestArticle()))

    private fun requestArticle() = homeRepository.getArticleList()
            .cachedIn(viewModelScope)
            .catch {
                Log.e("catch","${it.message}")
            }



    fun getBanner() = homeRepository.getBanner()
        .onStart {}
        .catch {
            it.printStackTrace()
            Log.e("catch","${it.message}")
        }


}

data class HomePageViewModelState(
    val listState: LazyListState = LazyListState(),
    val pagingData: Flow<PagingData<ArticleBean>>
)