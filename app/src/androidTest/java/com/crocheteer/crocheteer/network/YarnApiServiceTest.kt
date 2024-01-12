package com.crocheteer.crocheteer.network

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.crocheteer.crocheteer.R
import com.crocheteer.crocheteer.network.api.YarnApiService
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class YarnApiServiceTest {
    private lateinit var yarnApiService: YarnApiService

    @Before
    fun createApiService() {
        val context: Context = ApplicationProvider.getApplicationContext()
        yarnApiService = NetworkModule.createRavelryApiServiceWithRetrofit(
            context.getString(R.string.ravelry_user),
            context.getString(R.string.ravelry_password)
        )
    }

    @Test
    @Throws(Exception::class)
    fun apiServiceSearch_returnsResults() = runBlocking {
        val searchResult = yarnApiService.paginatedYarnSearch("merino", 1)

        Assert.assertTrue(searchResult.yarns.isNotEmpty())
    }

    @Test
    @Throws(Exception::class)
    fun apiServiceSearch_navigatesToPage() = runBlocking {
        val page = 2
        val searchResult = yarnApiService.paginatedYarnSearch("merino", page)

        Assert.assertNotNull(searchResult.paginator)
        Assert.assertEquals(page, searchResult.paginator!!.page)
    }
}