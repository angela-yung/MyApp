package com.example.myapp.model

class ProblemSet (private var category : String, private var problemList : MutableList<Problem>) {
    fun getCategory() : String {
        return category
    }

    fun getProblemList() : MutableList<Problem> {
        return problemList
    }

    fun getProblemListCount() : Int {
        return problemList.count()
    }

    override fun toString(): String {
        return "ProblemSet(category='$category', problemList=$problemList)"
    }

}