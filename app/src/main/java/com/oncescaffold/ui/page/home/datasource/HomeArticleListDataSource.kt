package com.oncescaffold.ui.page.home.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.oncescaffold.bean.ArticleBean
import com.oncescaffold.net.RetrofitClient.retrofitWanAndroid

class HomeArticleListDataSource : PagingSource<Int, ArticleBean>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int,ArticleBean> {
        return try{
            val page = params.key ?: 1
            val pageSize = params.loadSize

            val bean = retrofitWanAndroid.getArticle(page,pageSize)

            val repoItems = bean.data?.datas
            val prevKey = if (page > 1) page - 1 else null
            val nextKey = if (repoItems?.isNotEmpty() == true) page + 1 else null

            LoadResult.Page(repoItems!!, prevKey, nextKey)
        }catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ArticleBean>): Int? {
        return null
    }

}