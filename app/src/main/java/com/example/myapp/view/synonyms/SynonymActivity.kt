package com.example.myapp.view.synonyms

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.myapp.databinding.ActivitySynonymBinding
import com.example.myapp.model.WordAssociation
import com.example.myapp.view.categories.SelectCategoryActivity

private const val TAG = "SynonymActivity"
private const val NUM_BUTTONS = 3
private const val CORRECT_BTN_COLOUR = "#40a832"
private const val INCORRECT_BTN_COLOUR = "#c41417"

class SynonymActivity: AppCompatActivity() {
    private lateinit var binding: ActivitySynonymBinding
    private var synonyms = ArrayList<WordAssociation>()
    private var currentWordAssoc = WordAssociation("", "", "", "")
    private var isAnswerSelected = false
    private var numCorrectAnswers = 0
    private var numAttemptedQuestions = 0
    private var isFinished = false
    private lateinit var defaultTint: ColorStateList

    init {
        initializeSynonyms()
        Log.d(TAG, "The following words were added to synonyms:\n$synonyms")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySynonymBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.tvSynonymInstructions.text = "Select the word that is most similar to the following word:"
        defaultTint = binding.btnAnswer1.backgroundTintList!!
        synonyms.shuffle()
        updateCurrentWordAssoc()
        updateDisplay()

        binding.btnAnswer1.setOnClickListener() {
            if (!isAnswerSelected) {
                if (currentWordAssoc.synonym == binding.btnAnswer1.text) {
                    binding.tvCorrectAnswer.text = "Correct"
                    binding.btnAnswer1.backgroundTintList = ColorStateList.valueOf(Color.parseColor(CORRECT_BTN_COLOUR))
                    numCorrectAnswers++
                } else {
                    binding.tvCorrectAnswer.text = "The correct answer is \"${currentWordAssoc.synonym}\""
                    binding.btnAnswer1.backgroundTintList = ColorStateList.valueOf(Color.parseColor(INCORRECT_BTN_COLOUR))
                }
                isAnswerSelected = true
                nextQuestion()
                numAttemptedQuestions++
            }
        }

        binding.btnAnswer2.setOnClickListener() {
            if (!isAnswerSelected) {
                if (currentWordAssoc.synonym == binding.btnAnswer2.text) {
                    binding.tvCorrectAnswer.text = "Correct"
                    binding.btnAnswer2.backgroundTintList = ColorStateList.valueOf(Color.parseColor(CORRECT_BTN_COLOUR))
                    numCorrectAnswers++
                } else {
                    binding.tvCorrectAnswer.text = "The correct answer is \"${currentWordAssoc.synonym}\""
                    binding.btnAnswer2.backgroundTintList = ColorStateList.valueOf(Color.parseColor(INCORRECT_BTN_COLOUR))
                }
                isAnswerSelected = true
                nextQuestion()
                numAttemptedQuestions++
            }
        }

        binding.btnAnswer3.setOnClickListener() {
            if (!isAnswerSelected) {
                if (currentWordAssoc.synonym == binding.btnAnswer3.text) {
                    binding.tvCorrectAnswer.text = "Correct"
                    binding.btnAnswer3.backgroundTintList = ColorStateList.valueOf(Color.parseColor(CORRECT_BTN_COLOUR))
                    numCorrectAnswers++
                } else {
                    binding.tvCorrectAnswer.text = "The correct answer is \"${currentWordAssoc.synonym}\""
                    binding.btnAnswer3.backgroundTintList = ColorStateList.valueOf(Color.parseColor(INCORRECT_BTN_COLOUR))
                }
                isAnswerSelected = true
                nextQuestion()
                numAttemptedQuestions++
            }
        }

//        binding.btnNextSynonym.setOnClickListener() {
//            if (synonyms.isEmpty()) {
//                binding.tvCorrectAnswer.text = "You answered __ out of __ correctly."
//            } else {
//                updateCurrentWordAssoc()
//                updateDisplay()
//                isAnswerSelected = false
//            }
//        }

        binding.btnBack2.setOnClickListener() {
            if (isFinished) {
                returnToCategorySelection()
            } else {
                displayResults()
                synonyms.clear()
                binding.btnBack2.visibility = android.view.View.GONE

                Handler(Looper.getMainLooper()).postDelayed({
                    returnToCategorySelection()
                }, 3000)
            }

        }
    }

    private fun nextQuestion() {
        Handler(Looper.getMainLooper()).postDelayed({
            if (synonyms.isEmpty()) {
               displayResults()
                isFinished = true
            } else {
                updateCurrentWordAssoc()
                updateDisplay()
                isAnswerSelected = false
            }
        }, 3000)
    }

    private fun displayResults() {
        binding.tvSynonymInstructions.text = ""
        binding.tvFindSynonym.text = "You answered $numCorrectAnswers out of $numAttemptedQuestions correctly."
        binding.tvCorrectAnswer.text = ""
        binding.btnAnswer1.visibility = android.view.View.GONE
        binding.btnAnswer2.visibility = android.view.View.GONE
        binding.btnAnswer3.visibility = android.view.View.GONE
    }

    private fun returnToCategorySelection() {
        val intent = Intent(this, SelectCategoryActivity::class.java)
        startActivity(intent)
    }

    private fun updateCurrentWordAssoc() {
        currentWordAssoc = synonyms.first()
        synonyms.remove(currentWordAssoc)

    }

    private fun updateDisplay() {
        binding.tvFindSynonym.text = currentWordAssoc.word
        var buttonOptions = randomizeOptions()
        for (i in 0..< NUM_BUTTONS) {
            when (i) {
                0 -> {
                    binding.btnAnswer1.text = buttonOptions[i]}
                1 -> {
                    binding.btnAnswer2.text = buttonOptions[i]
                }
                else -> {
                    binding.btnAnswer3.text = buttonOptions[i]
                }
            }
        }
        binding.tvCorrectAnswer.text = ""
        resetBtnColours()
    }

    private fun resetBtnColours() {
//        Log.d(TAG, "Default btn colour = ${binding.btnAnswer1.backgroundTintList}")
        // set button to default theme colour
        binding.btnAnswer1.backgroundTintList = defaultTint
        binding.btnAnswer2.backgroundTintList = defaultTint
        binding.btnAnswer3.backgroundTintList = defaultTint
    }

    private fun randomizeOptions(): ArrayList<String> {
        var options = ArrayList<String>()
        options.add(currentWordAssoc.synonym)
        options.add(currentWordAssoc.firstAssociation)
        options.add(currentWordAssoc.secondAssociation)
        options.shuffle()
        return options
    }
    private fun initializeSynonyms() {
        synonyms = ArrayList<WordAssociation>( listOf(
            WordAssociation("rock", "stone", "slide", "hard"),
            WordAssociation("exit", "leave", "window", "late"),
            WordAssociation("pants", "trousers", "clothing", "legs"),
            WordAssociation("last", "final", "slow", "first"),
            WordAssociation("street", "road", "house", "travel"),
            WordAssociation("money", "cash", "spend", "shop"),
            WordAssociation("couple", "two", "trio", "date"),
            WordAssociation("applaud", "clap", "rake", "hands"),
            WordAssociation("wrong", "incorrect", "guess", "answer"),
            WordAssociation("yell", "shout", "loud", "person"),
            WordAssociation("same", "alike", "time", "again"),
            WordAssociation("great", "fantastic", "play", "watch"),
            WordAssociation("home", "residence", "dance", "syrup"),
            WordAssociation("shut", "close", "door", "window"),
            WordAssociation("coat", "jacket", "zipper", "clothes"),
            WordAssociation("leave", "depart", "door", "come"),
            WordAssociation("movie", "film", "color", "watch"),
            WordAssociation("stay", "remain", "hold", "room"),
            WordAssociation("hurry", "rush", "up", "home"),
            WordAssociation("author", "writer", "book", "publish"),
            WordAssociation("act", "perform", "action", "up"),
            WordAssociation("listen", "hear", "stop", "look"),
            WordAssociation("fast", "quick", "time", "race"),
            WordAssociation("fix", "mend", "broken", "chip"),
            WordAssociation("physician", "doctor", "pill", "sick"),
            WordAssociation("sea", "ocean", "water", "salt"),
            WordAssociation("big", "large", "time", "deal"),
            WordAssociation("twelve", "dozen", "carton", "eggs"),
            WordAssociation("short", "brief", "person", "group"),
            WordAssociation("humorous", "funny", "riddle", "cry"),
            WordAssociation("plain", "bland", "spicy", "blend"),
            WordAssociation("starved", "hungry", "full", "food"),
            WordAssociation("gift", "present", "bow", "birthday"),
            WordAssociation("loud", "noisy", "whisper", "voice"))
        )
    }
}