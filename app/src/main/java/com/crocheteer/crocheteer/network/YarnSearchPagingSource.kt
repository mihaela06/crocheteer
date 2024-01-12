package com.crocheteer.crocheteer.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.crocheteer.crocheteer.data.entities.YarnType
import com.crocheteer.crocheteer.data.entities.YarnWeight
import com.crocheteer.crocheteer.network.api.YarnApiService
import com.crocheteer.crocheteer.network.entities.YarnResponse

private const val STARTING_PAGE = 1

class YarnSearchPagingSource(
    private val searchTerm: String,
    private val apiService: YarnApiService
) : PagingSource<Int, Pair<YarnType, Int>>() {

    private val uniqueIds: MutableSet<Int> = mutableSetOf();

    private fun tryParseYarnWeight(yarnWeight: String?): YarnWeight? {
        return try {
            if (yarnWeight == null) throw Exception()
            YarnWeight.valueOf(yarnWeight)
        } catch (_: Exception) {
            null
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Pair<YarnType, Int>>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val anchorPage = state.closestPageToPosition(anchorPosition) ?: return null

        return anchorPage.prevKey?.plus(1) ?: anchorPage.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Pair<YarnType, Int>> {
        val pageId = params.key ?: STARTING_PAGE

        try {

            val searchResponse = apiService.paginatedYarnSearch(searchTerm, pageId)

            val prevKey = when (pageId) {
                STARTING_PAGE -> null
                else -> pageId
            }

            val nextKey = when {
                searchResponse.paginator != null ->
                    if (searchResponse.paginator!!.page == searchResponse.paginator!!.lastPage) null
                    else pageId + 1

                else -> null
            }

            val uniqueYarns = mutableListOf<YarnResponse>()
            val oldUniqueIdsCount = uniqueIds.size

            for (y in searchResponse.yarns) {
                if (y.id != null) {
                    if (!uniqueIds.contains(y.id)) {
                        uniqueYarns.add(y)
                        uniqueIds.add(y.id!!)
                    }
                }
            }

            return LoadResult.Page(
                data = uniqueYarns.map() {
                    Pair(
                        YarnType(
                            name = if (it.name == null) "" else it.name!!,
                            companyName = if (it.yarnCompanyName == null) "" else it.yarnCompanyName!!,
                            machineWashable = it.machineWashable,
                            genericPhotoUrl = it.firstPhoto?.medium2Url,
                            grams = if (it.grams == null) 0 else it.grams!!,
                            length = if (it.yardage == null) 0 else it.yardage!!,
                            weight = tryParseYarnWeight(it.yarnWeight?.name)
                        ),
                        it.id!!
                    )
                },
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}