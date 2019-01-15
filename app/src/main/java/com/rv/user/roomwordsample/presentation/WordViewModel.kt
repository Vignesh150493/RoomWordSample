package com.rv.user.roomwordsample.presentation

import android.app.Application
import com.rv.user.roomwordsample.data.Word
import android.arch.lifecycle.LiveData
import com.rv.user.roomwordsample.data.source.WordRepository
import android.arch.lifecycle.AndroidViewModel


class WordViewModel(application: Application) : AndroidViewModel(application) {

    private val mRepository: WordRepository = WordRepository(application)

    val allWords: LiveData<List<Word>>

    init {
        allWords = mRepository.getAllWords()
    }

    fun insert(word: Word) {
        mRepository.insert(word)
    }
}