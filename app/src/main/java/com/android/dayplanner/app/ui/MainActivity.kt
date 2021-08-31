package com.android.dayplanner.app.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.Room
import com.android.dayplanner.app.R
import com.android.dayplanner.app.data.TaskDatabase

class MainActivity : AppCompatActivity() {

    private lateinit var db: TaskDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = Room.databaseBuilder(
            applicationContext,
            TaskDatabase::class.java, "tasks-database"
        ).allowMainThreadQueries().build()
    }

    fun getDatabaseInstance(): TaskDatabase {
        return db
    }

}