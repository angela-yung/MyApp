package com.example.myapp

class Problem (private var question:String, private var answer:String, private var id:Int){
    fun getQuestion() : String {
        return question
    }

    fun getAnswer() : String {
        return answer
    }

    fun getId() : Int {
        return id
    }

}