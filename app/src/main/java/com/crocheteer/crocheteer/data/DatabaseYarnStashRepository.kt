package com.crocheteer.crocheteer.data

import androidx.paging.PagingSource
import com.crocheteer.crocheteer.data.entities.YarnTypeWithColors
import javax.inject.Inject

class DatabaseYarnStashRepository @Inject constructor(
    private val yarnDao: YarnDao
) : YarnStashRepository {
    override fun yarnStashPagingSource(searchTerm: String): PagingSource<Int, YarnTypeWithColors> {
        if (searchTerm.isNotEmpty())
            return yarnDao.getAllYarnTypesWithColorsPagingSource(searchTerm)
        return yarnDao.getAllYarnTypesWithColorsPagingSource()
    }

    override suspend fun addStashedYarn(newYarn: YarnTypeWithColors) {
        yarnDao.insert(newYarn.type)
        if (newYarn.colors != null)
            for (color in newYarn.colors) yarnDao.insert(color)
    }

    override suspend fun updateStashedYarn(updatedYarn: YarnTypeWithColors) {
        yarnDao.update(updatedYarn.type)
        if (updatedYarn.colors != null)
            for (color in updatedYarn.colors)
                if (color.id == 0L) yarnDao.insert(color) else yarnDao.update(color)
    }

    override suspend fun deleteStashedYarn(toDelYarn: YarnTypeWithColors) {
        TODO("Not yet implemented")
    }

    override suspend fun getCompanyName(searchTerm: String): List<String> {
        val flow =
            if (searchTerm.isEmpty()) yarnDao.getCompanyNames()
            else yarnDao.getCompanyNames(searchTerm)

        val companyNames = mutableListOf<String>()
        flow.collect { it.forEach { companyName -> companyNames.add(companyName) } }

        return companyNames
    }
}