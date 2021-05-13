package com.example.android.unscramble.ui.game

import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {

    init {
        getNextWord()
    }

    // Backing properties to be accessed by [GameFragment]
    private var _score = 0
    val score: Int
        get() = _score
    private var _currentWordCount = 0
    val currentWordCount: Int
        get() = _currentWordCount
    private lateinit var currentWord: String
    private lateinit var _currentScrambledWord: String
    val currentScrambledWord: String
        get() = _currentScrambledWord

    /**
     * Gets a random word from [allWordsList], converts it to a char array
     * and scrambles the letters.
     */
    private fun getNextWord() {
        val wordsList: MutableList<String> = mutableListOf()
        currentWord = allWordsList.random()
        val tempWord = currentWord.toCharArray()
        tempWord.shuffle()

        // Keep shuffling if tempWord order is the same as the currentWord.
        while (tempWord.toString().equals(currentWord, false)) {
            tempWord.shuffle()
        }

        // Get a new word if the current one has already been used.
        if (wordsList.contains(currentWord)) {
            getNextWord()
        } else {
            _currentScrambledWord = String(tempWord)
            ++_currentWordCount
            wordsList.add(currentWord)
        }
    }

    /**
     * Returns true if the currentWordCount is less than MAX_NO_WORDS.
     * Updates de the next word
     */
    fun nextWord(): Boolean {
        return if (_currentWordCount < MAX_NO_OF_WORDS) {
            getNextWord()
            true
        } else false
    }

    /**
     * Increaes the score.
     */
    private fun increaseScore() {
        _score += SCORE_INCREASE
    }

    /**
     * Return true if user unscrambled word is equal to the currentWord.
     */
    fun isUserWordCorrect(playerWord: String): Boolean {
        if (playerWord.equals(currentWord, true)) {
            increaseScore()
            return true
        }
        return false
    }

    /**
     * Re-initialize all data to restart the game.
     */
    fun reinitializeData() {
        _score = 0
        _currentWordCount = 0
        getNextWord()
        // TODO: Clear the [wordsList]
    }
}