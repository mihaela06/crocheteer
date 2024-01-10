package com.crocheteer.crocheteer.data

import com.crocheteer.crocheteer.data.entities.YarnTypeWithColors
import javax.inject.Inject

class DatabaseYarnStashRepository @Inject constructor(
    val yarnDao: YarnDao
) : YarnStashRepository {
    override val yarnStashPagingSource = yarnDao.getAllYarnTypesWithColorsPagingSource()
    override suspend fun addStashedYarn(newYarn: YarnTypeWithColors) {
        yarnDao.insert(newYarn.type)
        if (newYarn.colors != null)
            for(color in newYarn.colors)
                yarnDao.insert(color)
    }

    override suspend fun updateStashedYarn(updatedYarn: YarnTypeWithColors) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteStashedYarn(toDelYarn: YarnTypeWithColors) {
        TODO("Not yet implemented")
    }
}