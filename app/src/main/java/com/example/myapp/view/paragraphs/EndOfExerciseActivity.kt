package com.example.myapp.view.paragraphs

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.myapp.databinding.ActivityEndOfExerciseBinding
import com.example.myapp.model.ProblemSet
import com.example.myapp.view.categories.SelectCategoryActivity

private const val DEFAULT_SCORE_TOTAL = 5
private const val TAG = "EndOfExerciseActivity"

class EndOfExerciseActivity: AppCompatActivity() {
    private lateinit var binding: ActivityEndOfExerciseBinding
    private var savedParagraphs = HashMap<String, ProblemSet>()
    private var score = ""
    private var totalScore = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEndOfExerciseBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        var extras = intent.extras
        if (extras != null) {
//            var temp = intent.getSerializableExtra("paragraphs") as HashMap<String, ProblemSet>
            savedParagraphs = intent.getSerializableExtra("paragraphs") as HashMap<String, ProblemSet>

//            savedParagraphs = temp.toMap() as? LinkedHashMap<String, ProblemSet>
            Log.d(TAG, "intent received the following paragraphs: ")
            logRemainingParagraphs()
            Log.d(TAG, "Map type: ${savedParagraphs!!.javaClass}")
//            Log.d(TAG, "number of paragraphs remaining = ${savedParagraphs?.size}")
            if (savedParagraphs == null) {
                Log.e(TAG, "savedParagraphs is null")
            }
            score = extras.getString("score").toString()
            totalScore = extras.getString("totalScore").toString()
        }

        binding.btnMenu.setOnClickListener() {
            returnToCategorySelection()
        }

        binding.btnNextExercise.setOnClickListener() {
            loadNextParagraph()
        }

        binding.tvExerciseScore.text = "$score / $DEFAULT_SCORE_TOTAL"
    }

    private fun logRemainingParagraphs() {
        try {
            for (paragraph in savedParagraphs!!.keys) {
                Log.d(TAG, "paragraph = $paragraph")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Exception: $e")
        }
    }

    private fun returnToCategorySelection() {
        val intent = Intent(this, SelectCategoryActivity::class.java)
        startActivity(intent)
    }

    private fun loadNextParagraph() {
        val intent = Intent(this, ParagraphsActivity::class.java)
        intent.putExtra("paragraphs", savedParagraphs)
        startActivity(intent)
    }
}
