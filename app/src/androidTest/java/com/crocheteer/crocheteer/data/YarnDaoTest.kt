package com.crocheteer.crocheteer.data

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.crocheteer.crocheteer.data.entities.YarnColor
import com.crocheteer.crocheteer.data.entities.YarnType
import com.crocheteer.crocheteer.data.entities.YarnWeight
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException


@RunWith(AndroidJUnit4::class)
class YarnDaoTest {
    private lateinit var yarnDao: YarnDao
    private lateinit var yarnDatabase: YarnDatabase

    private val superSaver = YarnType(
        id = 1,
        name = "Super Saver",
        companyName = "Red Heart",
        genericPhotoUrl = "https://images4-g.ravelrycache.com/uploads/redheartyarns/797884997/upload_medium",
        grams = 198,
        length = 332,
        machineWashable = true,
        weight = YarnWeight.Aran
    )

    private val catania = YarnType(
        id = 2,
        name = "Catania Solids",
        companyName = "Schachenmayr",
        genericPhotoUrl = "https://images4-f.ravelrycache.com/uploads/Schachenmayr/512915703/Catania_00391_petrol_medium.jpg",
        grams = 50,
        length = 125,
        machineWashable = true,
        weight = YarnWeight.Sport
    )

    private val superSaverRed = YarnColor(
        id = 1,
        yarnTypeId = 1,
        quantity = 500,
        colorName = "Cherry Red",
        colorCode = "0319"
    )

    private val superSaverPumpkin = YarnColor(
        id = 2,
        yarnTypeId = 1,
        quantity = 1000,
        colorName = "Pumpkin",
        colorCode = "254"
    )

    private val cataniaGreen = YarnColor(
        id = 3,
        yarnTypeId = 2,
        quantity = 70,
        colorName = "Apple Green",
        colorCode = "205",
    )

    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()
        yarnDatabase = Room.inMemoryDatabaseBuilder(context, YarnDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        yarnDao = yarnDatabase.yarnDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        yarnDatabase.close()
    }

    @Test
    @Throws(Exception::class)
    fun daoInsert_insertsYarnIntoDB() = runBlocking {
        addSuperSaverToDb()

        val allYarns = yarnDao.getAllYarnTypes().first()

        assertEquals(1, allYarns.size)
        assertEquals(superSaver, allYarns[0])
    }

    @Test
    @Throws(Exception::class)
    fun daoGetAllYarns_returnsAllYarnsFromDB() = runBlocking {
        addCataniaToDb()
        addSuperSaverToDb()

        val allYarns = yarnDao.getAllYarnTypes().first()
        // returns the yarns in alphabetical order (company name - name)

        assertEquals(2, allYarns.size)
        assertEquals(superSaver, allYarns[0]) // Red Heart Super Saver
        assertEquals(catania, allYarns[1])  // Schachenmayr Catania Solids
    }

    @Test
    @Throws(Exception::class)
    fun daoGetYarn_returnsYarnFromDB() = runBlocking {
        addSuperSaverToDb()

        val yarn = yarnDao.getYarnType(superSaver.id)

        assertEquals(superSaver, yarn.first())
    }

    @Test
    @Throws(Exception::class)
    fun daoDeleteYarns_deletesAllYarnsFromDB() = runBlocking {
        addSuperSaverToDb()
        addCataniaToDb()

        yarnDao.delete(superSaver)
        yarnDao.delete(catania)
        val allYarns = yarnDao.getAllYarnTypes().first()

        assertTrue(allYarns.isEmpty())
    }

    @Test
    @Throws(Exception::class)
    fun daoDeleteYarnColor_deletesYarnColorsInDB() = runBlocking {
        addSuperSaverWithColorsToDb()

        yarnDao.delete(superSaverPumpkin)

        val superSaverModified = yarnDao.getYarnTypeWithColors(superSaver.id).first()
        Log.d("", superSaverModified.toString())

        assertEquals(1, superSaverModified.colors!!.size)
        assertEquals(superSaverRed, superSaverModified.colors!![0])
    }

    @Test
    @Throws(Exception::class)
    fun daoUpdateYarns_updatesYarnsInDB() = runBlocking {
        val superSaverModified = superSaver.copy(machineWashable = false, length = 50)
        val cataniaModified = catania.copy(machineWashable = false, length = 200)

        addSuperSaverToDb()
        addCataniaToDb()

        yarnDao.update(superSaverModified)
        yarnDao.update(cataniaModified)

        val allYarns = yarnDao.getAllYarnTypes().first()
        assertEquals(superSaverModified, allYarns[0])
        assertEquals(cataniaModified, allYarns[1])
    }

    @Test
    @Throws(Exception::class)
    fun daoUpdateYarnColors_updatesYarnColorsInDB() = runBlocking {
        val superSaverRedModified = superSaverRed.copy(colorCode = "42")
        val superSaverPumpkinModified = superSaverPumpkin.copy(colorCode = "42")
        val cataniaGreenModified = cataniaGreen.copy(colorCode = "42")

        addSuperSaverWithColorsToDb()
        addCataniaWithColorsToDb()

        yarnDao.update(superSaverRedModified)
        yarnDao.update(superSaverPumpkinModified)
        yarnDao.update(cataniaGreenModified)

        val superSaver = yarnDao.getYarnTypeWithColors(superSaver.id).first()
        val catania = yarnDao.getYarnTypeWithColors(catania.id).first()

        assertEquals(superSaverRedModified, superSaver.colors!![0])
        assertEquals(superSaverPumpkinModified, superSaver.colors!![1])
        assertEquals(cataniaGreenModified, catania.colors!![0])
    }

    @Test
    @Throws(Exception::class)
    fun daoGetYarnTypeByYarnColorId_returnsYarnTypeInDB() = runBlocking {
        addSuperSaverWithColorsToDb()

        val yarn = yarnDao.getYarnTypeByYarnColorId(superSaver.id).first()
        assertEquals(superSaver, yarn)
    }

    @Test
    @Throws(Exception::class)
    fun daoGetYarnTypeWithColors_returnsYarnTypeWithColorsInDB() = runBlocking {
        addSuperSaverWithColorsToDb()

        val superSaverColors = yarnDao.getYarnTypeWithColors(superSaver.id).first()
        assertEquals(2, superSaverColors.colors!!.size)
    }

    @Test
    @Throws(Exception::class)
    fun daoGetAllYarnTypesWithColors_returnsYarnTypesWithColorsInDB() = runBlocking {
        addSuperSaverWithColorsToDb()
        addCataniaWithColorsToDb()

        val allYarnTypesWithColors = yarnDao.getAllYarnTypesWithColors().first()
        assertEquals(3, allYarnTypesWithColors.sumOf { it.colors!!.size })
    }

    private suspend fun addSuperSaverToDb() {
        yarnDao.insert(superSaver)
    }

    private suspend fun addSuperSaverWithColorsToDb() {
        addSuperSaverToDb()
        yarnDao.insert(superSaverRed)
        yarnDao.insert(superSaverPumpkin)
    }

    private suspend fun addCataniaToDb() {
        yarnDao.insert(catania)
    }

    private suspend fun addCataniaWithColorsToDb() {
        addCataniaToDb()
        yarnDao.insert(cataniaGreen)
    }
}