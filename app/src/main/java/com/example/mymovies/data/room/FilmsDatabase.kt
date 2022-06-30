package com.example.mymovies.data.room

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mymovies.data.FilmsDao

abstract class FilmsDatabase: RoomDatabase() {

    abstract fun filmDao(): FilmsDao

    companion object{

        var INSTANCE: FilmsDatabase? = null

        fun getFilmsDatabase(context: Context): FilmsDatabase?{
            if (INSTANCE == null){
                INSTANCE = Room.databaseBuilder(context.applicationContext, FilmsDatabase::class.java, "myDB").build()
            }
            return INSTANCE
        }
    }
}