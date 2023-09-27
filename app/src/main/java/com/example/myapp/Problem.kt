package com.example.myapp

import android.util.Log

private const val TAG = "Problem"
//private const val MAX_CHARS_HIDDEN = 2
private const val MIN_CHARS_HIDDEN = 1
class Problem (private var question:String, private var answer:String, private var id:Int) {
    fun getQuestion() : String {
        return question
    }

    fun getAnswer() : String {
        return answer
    }

//    fun getLengthOfSolution() : Int {
//        return answer.length
//    }

    fun getPartialSolution() : String {
        var partialSolution = answer
        Log.d(TAG, "get partial solution answer.length = $answer.length")
        var maxCharsHidden = answer.length - 1
        var numCharsHidden = if (answer.length == 1) {
            1
        } else {
            (MIN_CHARS_HIDDEN..maxCharsHidden).random()
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
            prevRandomIndex = randomIndex
        }
        return partialSolution
    }

    fun getId() : Int {
        return id
    }

    override fun toString(): String {
        return "Problem(question='$question', answer='$answer', id=$id)"
    }
}