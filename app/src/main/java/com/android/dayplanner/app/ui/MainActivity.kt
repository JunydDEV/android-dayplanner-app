package com.android.dayplanner.app.ui

import android.os.Bundle
import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import androidx.test.espresso.IdlingResource
import com.android.dayplanner.app.R
import com.android.dayplanner.app.data.TaskDatabase
import com.android.dayplanner.app.ui.resourceidling.SimpleIdlingResource


class MainActivity : AppCompatActivity() {

    private lateinit var db: TaskDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun getDatabaseInstance(): TaskDatabase {
        if(!this::db.isInitialized) {
            db = Room.databaseBuilder(
                applicationContext,
                TaskDatabase::class.java, "tasks-database"
            ).allowMainThreadQueries().build()
        }
        return db
    }
}