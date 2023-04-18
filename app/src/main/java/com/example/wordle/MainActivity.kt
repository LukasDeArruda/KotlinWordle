package com.example.wordle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import java.io.BufferedReader
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {
    private lateinit var wordList : List<String>
    private lateinit var word : String
    private var gameOver = false
    private var guess = "     ";

    private fun legitGuess():Boolean = guess.lowercase() in wordList

    // build a map<character,count> for the word
    // Key is a letter, value counts occurrences of the letter
    private fun countCharacterOccurrences(str:String):Map<Char, Int> {
        val charCountMap = mutableMapOf<Char, Int>()    // initialize the map
        // how many times the same character appeared in the word
        for (c in str) charCountMap[c] = charCountMap.getOrDefault(c, 0) + 1
        return charCountMap
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        wordList = BufferedReader(InputStreamReader(resources.openRawResource(resources.getIdentifier("wordle", "raw", packageName)))).readLines()
        word = wordList.random()
        findViewById<TextView>(R.id.message).text = "The word is $word"

    }
    private var row = 1
    private var col = 1
    private fun getTextView(row : Int, col : Int): TextView {
        val idName = if (col > 5) "textView${row}5" else "textView${row}${col}"
        val id = resources.getIdentifier(idName, "id", packageName)
        return findViewById<TextView>(id)
    }
    private fun getButton(letter : String): Button {
        val idName = "button${letter.uppercase()}"
        val id = resources.getIdentifier(idName, "id", packageName)
        return findViewById<Button>(id)
    }
    fun letterHandler(view: View) {
        // if game is over, just return

        // when a user press a letter, show the letter to current textView
        getTextView(row, col).text = (view as Button).text.toString()
        if(col < 5)
        {
            col += 1
        }
        //println((view as Button).text.toString())     // for debugging
        // advance cursor to next textView

    }
    // themes.xml - OnClickListener for back space
    fun backspaceHandler(view: View) {
        // if game is over, just return

        // if we went past the end, so clamp down
        // Go back if we advanced
        // Erase the text
    }
    // themes.xml - OnClickListener for enter
    fun enterHandler(view: View) {
        // No change to game state if the word is incomplete

        // grab text from textView and concatenate

        // No change to game state if the word is not in dictionary

        // At this point, reveal the game state
        colorCode()
        // If we got here, the guessed word is in the dictionary
        // If it matches the word, the game is over
        row += 1
        col = 1
        // If we're on the last row, the game is over

    }
    // grab text from textView and concatenate
    private fun getGuess() {

    }
    private fun updateTextColor(row: Int, col: Int, color: Int) {

    }
    private val colorMap = mutableMapOf<String,Int>()
    private fun updateButtonColor(letter: String, color: Int) {

        // Pick the best color for the button


        // Green beats yellow and gray

        // Yellow beats gray

    }

    // based on a map<letter, occurrence>, update textView colors and keyboard button colors
    private fun colorCode() {
        // Store user's guess as array of strings. Five letters, index 0 to 4.

        // First, highlight in green where the characters lined up properly

        // If the guessed letter matches the word letter, color code in green
        // and decrement the occurrences for the corresponding letter

        // If the guessed letter is in the word, highlight in yellow
        // and decrement the occurrences for the corresponding letter
        // Otherwise, highlight non-matches with a black background

    }

}
