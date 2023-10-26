package com.example.myapp.view.paragraphs

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapp.databinding.ActivityEndOfExerciseBinding
import com.example.myapp.model.ProblemSet
import com.example.myapp.view.categories.SelectCategoryActivity

private const val DEFAULT_SCORE_TOTAL = 5

class EndOfExerciseActivity: AppCompatActivity() {
    private lateinit var binding: ActivityEndOfExerciseBinding
    private var savedParagraphs: LinkedHashMap<String, ProblemSet> = LinkedHashMap<String, ProblemSet>()
    private var score = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEndOfExerciseBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        var extras = intent.extras
        if (extras != null) {
            savedParagraphs = intent.getSerializableExtra("paragraphs") as LinkedHashMap<String, ProblemSet>
            score = extras.getString("score").toString()
        }

        binding.btnMenu.setOnClickListener() {
            returnToCategorySelection()
        }

        binding.btnNextExercise.setOnClickListener() {
            loadNextParagraph()
        }

        binding.tvExerciseScore.text = "$score / $DEFAULT_SCORE_TOTAL"
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
