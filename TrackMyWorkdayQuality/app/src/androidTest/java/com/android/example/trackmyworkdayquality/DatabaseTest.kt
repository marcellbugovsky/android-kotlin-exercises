package com.android.example.trackmyworkdayquality

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.android.example.trackmyworkdayquality.database.DatabaseDao
import com.android.example.trackmyworkdayquality.database.WorkDatabase
import com.android.example.trackmyworkdayquality.database.Workday
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotSame
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

/**
 * This is not meant to be a full set of tests. For simplicity, most of your samples do not
 * include tests. However, when building the Room, it is helpful to make sure it works before
 * adding the UI.
 */

@RunWith(AndroidJUnit4::class)
class SleepDatabaseTest {

    private lateinit var workDao: DatabaseDao
    private lateinit var db: WorkDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, WorkDatabase::class.java)
                // Allowing main thread queries, just for testing.
                .allowMainThreadQueries()
                .build()
        workDao = db.databaseDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetWorkday() {
        val workday = Workday()
        workDao.insert(workday)
        val today = workDao.getToday()
        assertEquals(today?.workdayQuality, -1)
    }

    @Test
    @Throws (Exception::class)
    fun clear() {
        val workday = Workday()
        workDao.insert(workday)
        workDao.clear()
        val today = workDao.getToday()
        assertNotSame(today?.workdayQuality, -1)
    }
}