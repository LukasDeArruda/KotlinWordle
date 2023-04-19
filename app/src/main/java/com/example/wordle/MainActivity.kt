package com.example.wordle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import org.w3c.dom.Text
import java.io.BufferedReader
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {
    private lateinit var wordList : List<String>
    lateinit var word : String
    private var gameOver = false
    var guess = "";

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
        if (gameOver) { return }
        if (col == 1){getTextView(row, col).text = ""}
        else if (col == 5 && getTextView(row, col).text != ""){
            getTextView(row, col).text = ""
        }
        else {
            col--
            getTextView(row, col).text = ""
        }
    }
    // themes.xml - OnClickListener for enter
    fun enterHandler(view: View) {
        // No change to game state if the word is incomplete
        // grab text from textView and concatenate
        getGuess()
        if (gameOver){

        }
        else if(guess.length == 5 && legitGuess())
        {
            if (guess.lowercase() == word){
                colorCode()
                gameOver = true
                findViewById<TextView>(R.id.message).text = "You win!"
            }
            else{
                colorCode()
                row += 1
                col = 1
                // Mostly for debugging
                findViewById<TextView>(R.id.message).text = "Valid Guess"
            }
        }
        else
        {
            findViewById<TextView>(R.id.message).text = "Invalid Guess"
        }
        // No change to game state if the word is not in dictionary

        // At this point, reveal the game state

        // If we got here, the guessed word is in the dictionary
        // If it matches the word, the game is over

        // If we're on the last row, the game is over

    }
    // grab text from textView and concatenate
    private fun getGuess() {
        guess = ""
        for(i in 1..5)
        {
            guess += (getTextView(row, i).text)
        }
    }

    // based on a map<letter, occurrence>, update textView colors and keyboard button colors
    private fun colorCode() {
        // Store user's guess as array of strings. Five letters, index 0 to 4.
        val answerMap = countCharacterOccurrences(word)
        getGuess()
        for(i in 0..4)
        {

            if (word[i].toString() == guess[i].lowercase())
            {
                getTextView(row, i+1).setBackgroundResource(R.color.green)
            }
            else if(answerMap.contains(guess[i].lowercaseChar()))
            {
                getTextView(row, i+1).setBackgroundResource(R.color.yellow)
            }
            else
            {
                getTextView(row, i+1).setBackgroundResource(R.color.gray)
            }
        }
        // THIS WORKS
//        for(i in 0..4)
//        {
//            getTextView(row, i+1).setBackgroundResource(R.color.green)
//        }



        // First, highlight in green where the characters lined up properly

        // If the guessed letter matches the word letter, color code in green
        // and decrement the occurrences for the corresponding letter

        // If the guessed letter is in the word, highlight in yellow
        // and decrement the occurrences for the corresponding letter
        // Otherwise, highlight non-matches with a black background

    }

}
