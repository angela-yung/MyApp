package com.example.myapp.view.demo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapp.databinding.ActivitySolveBinding
import com.example.myapp.model.Problem
import com.example.myapp.model.ProblemSetManager
import com.example.myapp.view.categories.SelectCategoryActivity

private const val TAG : String = "SolveActivity"
private const val DEFAULT_CATEGORY : String = "Random Trivia"

class SolveActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySolveBinding
    private var currentCategory = DEFAULT_CATEGORY
    private var randomId : Int = 0
    private var problemSetManager = ProblemSetManager()
    private var currentProblemSet = problemSetManager.getProblemSet(DEFAULT_CATEGORY)
    private var currentProblem : Problem? = null
    private var isHintClicked = false
    private var isRevealed = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySolveBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        var extras = intent.extras
        if (extras != null) {
            Log.d(TAG, "imported the category $currentCategory from SelectCategoryActivity")
            currentCategory = extras.getString("category") ?: DEFAULT_CATEGORY
            Log.d(TAG, "current category = $currentCategory")
            currentProblemSet = problemSetManager.getProblemSet(currentCategory)
        }
        updateCurrentProblem()
        displayQuestion()
//        Log.d(TAG, "currentProblem = $currentProblem")

        binding.btnEnter.setOnClickListener {
            val answer = binding.etAnswer.text.toString()
            Log.d(TAG, "answer = $answer")
            if (answer.equals(problemSetManager.getProblemByCategoryAndId(currentCategory, randomId)?.getAnswer(), true)) {
                Toast.makeText(this@SolveActivity, "Correct!", Toast.LENGTH_SHORT).show()
                nextQuestion()
                resetProblem()
            } else {
                Toast.makeText(this@SolveActivity, "Incorrect!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnNext.setOnClickListener {
            nextQuestion()
            resetProblem()
        }

        binding.btnHint.setOnClickListener {
            if (!isHintClicked && !isRevealed) {
                binding.tvHintBar.text = currentProblem?.getPartialSolution() ?: throw Exception("Null currentProblem: failed to get partial solution")
                isHintClicked = true
            }
            Toast.makeText(this@SolveActivity, "Hint Pressed", Toast.LENGTH_SHORT).show()
        }

        binding.btnRevealAnswer.setOnClickListener {
            binding.tvHintBar.text = currentProblem?.getAnswer() ?: throw Exception("Null currentProblem: failed to get answer")
            isRevealed = true
            Toast.makeText(this@SolveActivity, "Reveal Answer Pressed", Toast.LENGTH_SHORT).show()
        }

        binding.iBtnBackArrow.setOnClickListener {
            returnToCategorySelection()
        }
    }

    private fun returnToCategorySelection() {
        val intent = Intent(this, SelectCategoryActivity::class.java)
        startActivity(intent)
    }

    private fun resetProblem() {
        binding.etAnswer.text.clear()
        binding.tvHintBar.text = ""
        isHintClicked = false
        isRevealed = false
    }

    private fun getNewProblem() : Problem {
        val problem : Problem?

        val prevRandomId = randomId
        while (randomId == prevRandomId) {
            randomId = (1..(currentProblemSet?.getProblemSetCount() ?: throw Exception ("Null problem set: failed to get problem list count"))).random()
        }
        Log.d(TAG, "getNewProblem() randomNum = $randomId")
        Log.d(TAG, "getNewProblem() category = $currentCategory")
        problem = problemSetManager.getProblemByCategoryAndId(currentCategory, randomId) ?: throw Exception("Failed getProblemByCategoryAndId()")
        return problem
    }

    private fun updateCurrentProblem() {
        try {
            currentProblem = getNewProblem()
        } catch (e: Exception) {
            Log.e(TAG, "Exception: $e")
        }
    }

    private fun displayQuestion() {
        binding.tvQuestionBar.text = currentProblem?.getQuestion() ?: throw Exception("Null currentProblem: failed to get question")
    }

    private fun nextQuestion() {
        updateCurrentProblem()
        displayQuestion()
    }
}