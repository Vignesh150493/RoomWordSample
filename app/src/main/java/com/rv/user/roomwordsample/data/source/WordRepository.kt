package com.rv.user.roomwordsample.data.source

import android.app.Application
import com.rv.user.roomwordsample.data.Word
import android.os.AsyncTask
import android.arch.lifecycle.LiveData


class WordRepository(application: Application) {

    private val mWordDao: WordDao
    private val allWords: LiveData<List<Word>>

    init {
        val db = WordDatabase.getInstance(application)
        mWordDao = db.wordDao()
        allWords = mWordDao.getAllWords()
    }

    fun getAllWords(): LiveData<List<Word>> {
        return allWords
    }

    fun insert(word: Word) {
        InsertAsyncTask(mWordDao).execute(word)
    }

    private class InsertAsyncTask internal constructor(private val mAsyncTaskDao: WordDao) : AsyncTask<Word, Void, Void>() {

        override fun doInBackground(vararg params: Word): Void? {
            mAsyncTaskDao.insert(params[0])
            return null
        }
    }
}