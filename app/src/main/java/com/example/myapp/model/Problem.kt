package com.example.myapp.model

import android.util.Log
import java.io.Serializable

private const val TAG = "Problem"
private const val MIN_CHARS_HIDDEN = 1

class Problem(private var question:String, private var answer:String, private var image: Int?, private var id:Int) : Serializable {
    fun getQuestion() : String {
        return question
    }

    fun getAnswer() : String {
        return answer
    }

    fun getPartialSolution() : String {
        var partialSolution = answer
        Log.d(TAG, "get partial solution answer.length = $answer.length")
        val maxCharsHidden = answer.length - 1
        val numCharsHidden = if (answer.length == 1) {
            1
        } else {
            ((MIN_CHARS_HIDDEN + maxCharsHidden)/2..maxCharsHidden).random()
        }
        Log.d(TAG, "get partial solution randomNum = $numCharsHidden")

        var prevRandomIndex = -1
        for (i in 1..numCharsHidden) {
            var randomIndex = (partialSolution.indices).random()
            while (prevRandomIndex == randomIndex) {
                randomIndex = (partialSolution.indices).random()
            }
            Log.d(TAG, "get partial solution randomIndex = $randomIndex")
            partialSolution = partialSolution.replaceRange(randomIndex, randomIndex + 1, "_")

            // Ensure spaces are not replaced by underscores
            var copy = answer
            while (true) {
                var spaceIndex = copy.indexOf(' ')
                if (spaceIndex == -1) {
                    break
                }
                partialSolution = partialSolution.replaceRange(spaceIndex, spaceIndex + 1, " ")
                copy = copy.replaceRange(spaceIndex, spaceIndex + 1, "_")
            }
            prevRandomIndex = randomIndex
        }
        Log.d(TAG, "get partial solution partialSolution = $partialSolution")
        return partialSolution
    }

    fun getId() : Int {
        return id
    }

    override fun toString(): String {
        return "Problem(question='$question', answer='$answer', id=$id)"
    }
}