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
    private var problemSetManager = ProblemSetManager()
    private var currentProblemSet = problemSetManager.getProblemSet(DEFAULT_CATEGORY)
    private var currentProblem = updateCurrentProblem()
    private var randomNum : Int = 0
    private var category = DEFAULT_CATEGORY

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnEnter.setOnClickListener {
            var answer = binding.etAnswer.text.toString()
            Log.d(TAG, "answer = $answer")
            if (answer.equals(problemSetManager.getProblemByCategoryAndId(category, randomNum)?.getAnswer(), true)) {
                Toast.makeText(this@MainActivity, "Correct!", Toast.LENGTH_SHORT).show()
                updateCurrentProblem()
                binding.etAnswer.text.clear()
            } else {
                Toast.makeText(this@MainActivity, "Incorrect!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnNext.setOnClickListener {
            updateCurrentProblem()
//            Toast.makeText(this@MainActivity, "Next Question", Toast.LENGTH_SHORT).show()
        }

        binding.btnHint.setOnClickListener {
            binding.tvHintBar.text = currentProblem?.getPartialSolution()
            Toast.makeText(this@MainActivity, "Hint Pressed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getNewProblem() : Problem? {
        var problem : Problem? = null
        try {
            val prevRandomNum = randomNum
            while (randomNum == prevRandomNum) {
                randomNum = (1..(currentProblemSet?.getProblemListCount() ?: throw Exception ("Null problem set: failed to get problem list count"))).random()
            }
            Log.d(TAG, "randomNum = $randomNum")
            problem = problemSetManager.getProblemByCategoryAndId(category, randomNum) ?: throw Exception("Failed getProblemByCategoryAndId()")
        } catch (e: Exception) {
            Log.e(TAG, "Exception: $e")
        }
        return problem
    }
    private fun updateCurrentProblem() : Problem? {
        var problem = getNewProblem()
        if (problem == null) {
            Log.e(TAG, "Failed updateCurrentProblem()")
            binding.tvQuestion.text = "Error"
            return null
        }
        binding.tvQuestion.text = problem.getQuestion()
        return problem
    }
}