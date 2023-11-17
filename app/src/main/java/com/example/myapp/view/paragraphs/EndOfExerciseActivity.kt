package com.example.myapp.view.paragraphs

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.myapp.databinding.ActivityEndOfExerciseBinding
import com.example.myapp.view.categories.SelectCategoryActivity

private const val DEFAULT_SCORE_TOTAL = 5
private const val TAG = "EndOfExerciseActivity"

class EndOfExerciseActivity: AppCompatActivity() {
    private lateinit var binding: ActivityEndOfExerciseBinding
    private var savedKeys = ArrayList<String>()
    private var score = ""
    private var total = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEndOfExerciseBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        var extras = intent.extras
        if (extras != null) {
//            var temp = intent.getSerializableExtra("paragraphs") as HashMap<String, ProblemSet>
            savedKeys = intent.getSerializableExtra("shuffledKeys") as ArrayList<String>

//            savedParagraphs = temp.toMap() as? LinkedHashMap<String, ProblemSet>
            Log.d(TAG, "intent received the following keys: ")
            logRemainingParagraphs()
            Log.d(TAG, "Map type: ${savedKeys!!.javaClass}")
//            Log.d(TAG, "number of paragraphs remaining = ${savedParagraphs?.size}")
            if (savedKeys == null) {
                Log.e(TAG, "savedParagraphs is null")
            }
            score = extras.getString("score").toString()
            total = extras.getString("total").toString()
            Log.d(TAG, "intent received the following total: $total")
        }

        binding.btnMenu.setOnClickListener() {
            returnToCategorySelection()
        }

        binding.btnNextExercise.setOnClickListener() {
            loadNextParagraph()
        }

        binding.tvExerciseScore.text = "$score / $total"
    }

    private fun logRemainingParagraphs() {
        try {
            for (key in savedKeys) {
                Log.d(TAG, "paragraph = $key")
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
        intent.putExtra("shuffledKeys", savedKeys)
        startActivity(intent)
    }
}
