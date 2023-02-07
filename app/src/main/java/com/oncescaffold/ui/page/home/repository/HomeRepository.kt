package com.oncescaffold.ui.page.home.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.oncescaffold.base.BaseRepository
import com.oncescaffold.bean.ArticleBean
import com.oncescaffold.bean.HttpData
import com.oncescaffold.bean.BannerBean
import com.oncescaffold.net.RetrofitClient.retrofitWanAndroid
import com.oncescaffold.ui.page.home.datasource.HomeArticleListDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class HomeRepository @Inject constructor() : BaseRepository() {


    fun getArticleList(): Flow<PagingData<ArticleBean>> {
        return Pager(PagingConfig(pageSize = 20)) {
            HomeArticleListDataSource()
        }.flow
    }

    fun getBanner(): Flow<HttpData<List<BannerBean>>> {
        return flow {
            emit( retrofitWanAndroid.getBanner() )
        }.flowOn(Dispatchers.IO)


    }

}