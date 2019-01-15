package com.rv.user.roomwordsample.data.source

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.rv.user.roomwordsample.data.Word
import android.arch.persistence.db.SupportSQLiteDatabase
import android.os.AsyncTask


@Database(entities = [Word::class], version = 1)
abstract class WordDatabase : RoomDatabase() {
    abstract fun wordDao(): WordDao


    companion object {

        private var INSTANCE: WordDatabase? = null

        private val sRoomDatabaseCallback = object : RoomDatabase.Callback() {

            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                PopulateDbAsync(INSTANCE!!).execute()
            }
        }

        private val lock = Any()

        fun getInstance(context: Context): WordDatabase {
            synchronized(lock) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            WordDatabase::class.java, "word_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build()
                }
                return INSTANCE!!
            }
        }
    }


    private class PopulateDbAsync internal constructor(db: WordDatabase) : AsyncTask<Void, Void, Void>() {

        private val mDao: WordDao = db.wordDao()

        override fun doInBackground(vararg params: Void): Void? {
            mDao.deleteAll()
            var word = Word("Hello")
            mDao.insert(word)
            word = Word("World")
            mDao.insert(word)
            return null
        }
    }

}