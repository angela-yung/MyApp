package com.example.myapp.model

import android.util.Log
import java.io.Serializable
private const val TAG = "ProblemSet"
class ProblemSet (private var category : String, private var problemList : MutableList<Problem>) :
    Serializable {
    fun getCategory() : String {
        return category
    }

    fun getProblemSet() : MutableList<Problem> {
        return problemList
    }

    fun getProblemSetCount() : Int {
//        Log.d(TAG, "problemList.size = ${problemList.size}")
        return problemList.size
    }

    override fun toString(): String {
        return "ProblemSet(category='$category', problemList=$problemList)"
    }

}