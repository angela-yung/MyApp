package com.example.myapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.myapp.databinding.ActivityMainBinding

private const val TAG : String = "MainActivity"
private const val DEFAULT_CATEGORY : String = "Random Trivia"
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var category = DEFAULT_CATEGORY
    private var randomNum : Int = 0
    private var problemSetManager = ProblemSetManager()
    private var currentProblemSet = problemSetManager.getProblemSet(DEFAULT_CATEGORY)
    private lateinit var currentProblem : Problem
    private var isHintClicked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        updateCurrentProblem()
        displayQuestion()
//        Log.d(TAG, "currentProblem = $currentProblem")

        binding.btnEnter.setOnClickListener {
            val answer = binding.etAnswer.text.toString()
            Log.d(TAG, "answer = $answer")
            if (answer.equals(problemSetManager.getProblemByCategoryAndId(category, randomNum)?.getAnswer(), true)) {
                Toast.makeText(this@MainActivity, "Correct!", Toast.LENGTH_SHORT).show()
                nextQuestion()
                resetProblem()
            } else {
                Toast.makeText(this@MainActivity, "Incorrect!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnNext.setOnClickListener {
            nextQuestion()
            resetProblem()
        }

        binding.btnHint.setOnClickListener {
            if (!isHintClicked) {
                binding.tvHintBar.text = currentProblem.getPartialSolution()
                isHintClicked = true
            }
            Toast.makeText(this@MainActivity, "Hint Pressed", Toast.LENGTH_SHORT).show()
        }

        binding.btnRevealAnswer.setOnClickListener {
            binding.tvHintBar.text = currentProblem.getAnswer()
            Toast.makeText(this@MainActivity, "Reveal Answer Pressed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun resetProblem() {
        binding.etAnswer.text.clear()
        binding.tvHintBar.text = ""
        isHintClicked = false
    }

    private fun getNewProblem() : Problem {
        val problem : Problem?

        val prevRandomNum = randomNum
        while (randomNum == prevRandomNum) {
            randomNum = (1..(currentProblemSet?.getProblemListCount() ?: throw Exception ("Null problem set: failed to get problem list count"))).random()
        }
        Log.d(TAG, "getNewProblem() randomNum = $randomNum")
        Log.d(TAG, "getNewProblem() category = $category")

        problem = problemSetManager.getProblemByCategoryAndId(category, randomNum) ?: throw Exception("Failed getProblemByCategoryAndId()")

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
        binding.tvQuestion.text = currentProblem.getQuestion()
    }

    private fun nextQuestion() {
        updateCurrentProblem()
        displayQuestion()
    }
}