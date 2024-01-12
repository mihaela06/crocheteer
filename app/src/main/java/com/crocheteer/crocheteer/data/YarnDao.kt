package com.crocheteer.crocheteer.data

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.crocheteer.crocheteer.data.entities.YarnColor
import com.crocheteer.crocheteer.data.entities.YarnType
import com.crocheteer.crocheteer.data.entities.YarnTypeWithColors
import kotlinx.coroutines.flow.Flow

@Dao
interface YarnDao {
    @Query("SELECT * FROM yarn_types ORDER BY company_name, name ASC")
    fun getAllYarnTypes(): Flow<List<YarnType>>

    @Transaction
    @Query("SELECT * FROM yarn_types")
    fun getAllYarnTypesWithColors(): Flow<List<YarnTypeWithColors>>

    @Transaction
    @Query("SELECT * FROM yarn_types")
    fun getAllYarnTypesWithColorsPagingSource(): PagingSource<Int, YarnTypeWithColors>

    @Transaction
    @Query("SELECT * from yarn_types WHERE id = :yarnTypeId")
    fun getYarnTypeWithColors(yarnTypeId: Long): Flow<YarnTypeWithColors>

    @Query("SELECT * FROM yarn_types WHERE id = :id")
    fun getYarnType(id: Long): Flow<YarnType>

    @Query("SELECT DISTINCT company_name FROM yarn_types")
    fun getCompanyNames(): Flow<List<String>>

    @Query(
        "SELECT DISTINCT company_name FROM yarn_types" +
                " WHERE company_name LIKE '%' || :searchTerm || '%'"
    )
    fun getCompanyNames(searchTerm: String): Flow<List<String>>

    @Transaction
    @Query(
        "SELECT t.* " +
                "FROM yarn_types t " +
                "INNER JOIN yarn_colors c " +
                "ON c.yarn_type_id = t.id " +
                "WHERE c.id = :yarnColorId"
    )
    fun getYarnTypeByYarnColorId(yarnColorId: Long): Flow<YarnType>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: YarnColor): Long

    @Update
    suspend fun update(item: YarnColor)

    @Delete
    suspend fun delete(item: YarnColor)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: YarnType): Long

    @Update
    suspend fun update(item: YarnType)

    @Delete
    suspend fun delete(item: YarnType)
}