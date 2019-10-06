package com.jairrab.cache.dao

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jairrab.cache.Rndom
import com.jairrab.cache.db.AppDatabase
import com.jairrab.cache.entities.DataProperty
import com.jairrab.cache.entities.MapPointEntity
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CachedDaoTest {

    @Rule
    @JvmField var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var cachedDao: CachedDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).allowMainThreadQueries().build()
        cachedDao = db.cachedDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun getMapPoints() {
        val entity1 = mapPointEntity
        val list = listOf(entity1)

        cachedDao.saveMapPoints(list).test()

        val testObserver = cachedDao.getMapPoints().test()

        testObserver.assertValue(list)
    }

    @Test
    fun getProperty() {
        val value = Rndom.string()
        val property = DataProperty(123, value)

        cachedDao.updateProperty(property).test()

        val testObserver = cachedDao.getProperty(123).test()

        testObserver.assertValue(value)
    }

    private val mapPointEntity: MapPointEntity
        get() = MapPointEntity(
            Rndom.long(),
            Rndom.string(),
            Rndom.double(),
            Rndom.double(),
            Rndom.string()
        )
}