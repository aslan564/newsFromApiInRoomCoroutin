package com.aslanovaslan.tsetapplication.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.aslanovaslan.tsetapplication.newsModel.NewsArticles

@Database(
    entities = [NewsArticles::class],
    version = 1
)
abstract class DatabaseNews:RoomDatabase() {

    abstract fun getNewsDao():NewsDao

    companion object{
       @Volatile private var instance:DatabaseNews?=null
        private val LOCK=Any()
        operator fun invoke(context: Context)= instance?: synchronized(LOCK){
            instance?: buildNewsDatabase(context).also {
                instance=it
            }
        }
        private fun buildNewsDatabase(context: Context)=Room.databaseBuilder(
            context.applicationContext,DatabaseNews::class.java,"everynewspaper"
        ).build()
    }

}