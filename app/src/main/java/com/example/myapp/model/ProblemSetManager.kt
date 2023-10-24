package com.example.myapp.model

import android.util.Log
import java.util.concurrent.atomic.AtomicInteger

private const val TAG : String = "ProblemSetManager"
private const val RANDOM_TRIVIA_CATEGORY : String = "Random Trivia"
private const val HISTORY_CATEGORY : String = "History"

class ProblemSetManager () {
    private val problemSets = mutableListOf<ProblemSet>()
    private var problemIdCounter = AtomicInteger()

    init {
        // Initialize list of problems in each category
        // Random Trivia
        var randomTriviaProblems = mutableListOf<Problem>(
            Problem("What is the rarest blood type?", "AB-", problemIdCounter.incrementAndGet()),
            Problem(
                "What is the name of the largest freshwater lake in the world?",
                "Lake Superior",
                problemIdCounter.incrementAndGet()
            ),
            Problem("What is the capital city of Spain?", "Madrid", problemIdCounter.incrementAndGet()),
            Problem(
                "What is the capital city of Australia?",
                "Canberra",
                problemIdCounter.incrementAndGet()
            ),
            Problem("What is the capital city of Canada?", "Ottawa", problemIdCounter.incrementAndGet()),
            Problem(
                "How many elements are there in the periodic table?",
                "118",
                problemIdCounter.incrementAndGet()
            )
        )
        problemSets.add(ProblemSet(RANDOM_TRIVIA_CATEGORY, randomTriviaProblems))
        // reset idCounter
        problemIdCounter.set(0)
        Log.d(TAG, "Generated the following problems: $randomTriviaProblems")
        Log.d(TAG, "numProblems = ${randomTriviaProblems.count()}")

        // History
        var historyProblems = mutableListOf<Problem>(
            Problem("What year did World War I begin?", "1914", problemIdCounter.incrementAndGet()),
            Problem("What year did World War II begin?", "1939", problemIdCounter.incrementAndGet()),
            Problem(
                "What year did the United States declare independence?",
                "1776",
                problemIdCounter.incrementAndGet()
            ),
            Problem(
                "One of the ancient world wonders, the “Hanging Gardens,” was found in which city?",
                "Babylon",
                problemIdCounter.incrementAndGet()
            ),
            Problem(
                "Who was the first man to walk on the moon?",
                "Neil Armstrong",
                problemIdCounter.incrementAndGet()
            )
        )
        problemSets.add(ProblemSet(HISTORY_CATEGORY, historyProblems))
        // reset idCounter
        problemIdCounter.set(0)

        Log.d(TAG, "Generated the following problems: $historyProblems")
        Log.d(TAG, "numProblems = ${historyProblems.count()}")
    }

    fun getProblemByCategoryAndId(category: String, id: Int) : Problem? {
        Log.d(TAG, "getProblemByCategoryAndId() called with category = $category, id = $id")
        var problemSet = getProblemSet(category)
        if (problemSet == null) {
            Log.e(TAG, "Could not associate a ProblemSet with that category.")
            return null
        }

        var problem = problemSet.getProblemList().find { it.getId() == id }
        if (problem == null) {
            Log.e(TAG, "Could not associate a Problem with that ID.")
            return null
        }
        return problem
    }

    fun getProblemSet(category: String): ProblemSet? {
        var problemSet : ProblemSet? = null
        try {
            problemSet = problemSets.find { it.getCategory() == category } ?: throw Exception("ProblemSet not found")
        } catch (e: Exception) {
            Log.e(TAG, "Exception: $e")
        }
        return problemSet
    }
}