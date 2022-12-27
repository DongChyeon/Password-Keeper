package com.dongchyeon.passwordkeeper.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dongchyeon.passwordkeeper.data.datasource.MemoDao
import com.dongchyeon.passwordkeeper.data.model.Memo

class MemoPagingSource(
    private val memoDao: MemoDao,
    private val category: String?
) : PagingSource<Int, Memo>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Memo> {
        val page = params.key ?: 0

        return try {
            val memos = if (category != null) {
                memoDao.getPagedMemosByCategory(category, params.loadSize, page * params.loadSize)
            } else {
                memoDao.getPagedMemos(params.loadSize, page * params.loadSize)
            }

            LoadResult.Page(
                data = memos,
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (memos.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Memo>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}