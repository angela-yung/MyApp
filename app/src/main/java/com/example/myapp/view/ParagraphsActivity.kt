package com.example.myapp.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapp.databinding.ActivityParagraphsBinding
import com.example.myapp.model.Problem
import com.example.myapp.model.ProblemSet
import java.util.concurrent.atomic.AtomicInteger

private const val DEFAULT_CATEGORY : String = "Paragraphs"

class ParagraphsActivity: AppCompatActivity() {
    private lateinit var binding: ActivityParagraphsBinding
    private var paragraphs = LinkedHashMap<String, ProblemSet>()
    private var problemIdCounter = AtomicInteger()
    private var currentParagraph = ""
    private var currentProblemSet = ProblemSet(DEFAULT_CATEGORY, mutableListOf<Problem>())
    private var currentProblem = Problem("", "", null, 0)
    private var shuffledMap = LinkedHashMap<String, ProblemSet>()

    init {
        // Assign paragraphs to problem sets
        var paragraph1 = "Maria went to the grocery store on Monday afternoon. She bought milk, eggs," +
                "fresh vegetables, and meat."
        paragraphs[paragraph1] = ProblemSet("Paragraphs", mutableListOf<Problem>(
                Problem("Did Maria go to the grocery store?", "Yes", null, problemIdCounter.incrementAndGet()),
                Problem("Did Maria go to the grocery store on Friday?", "No", null, problemIdCounter.incrementAndGet()),
                Problem("Did Maria go to the grocery store in the morning?", "No", null, problemIdCounter.incrementAndGet()),
                Problem("Did Maria buy eggs?", "Yes", null, problemIdCounter.incrementAndGet()),
                Problem("Did Maria buy soap?", "No", null, problemIdCounter.incrementAndGet()))
        )

        // reset idCounter
        problemIdCounter.set(0)

        var paragraph2 = "Anthony thought he had a cavity. He called the dentist's office and got an" +
                "appointment for Wednesday at 2:00 p.m."
        paragraphs[paragraph2] = ProblemSet("Paragraphs", mutableListOf<Problem>(
                Problem("Did Anthony get an appointment?", "Yes", null, problemIdCounter.incrementAndGet()),
                Problem("Was the appointment for Monday?", "No", null, problemIdCounter.incrementAndGet()),
                Problem("Did Anthony call the dentist's office?", "Yes", null, problemIdCounter.incrementAndGet()),
                Problem("Did Anthony think he had a cavity?", "Yes", null, problemIdCounter.incrementAndGet()),
                Problem("Was his appointment for 3:00 p.m.?", "No", null, problemIdCounter.incrementAndGet()))
        )

        // reset idCounter
        problemIdCounter.set(0)

        var paragraph3 = "Paula gave her husband a new camera for his birthday. He enjoyed it so much" +
                "that he took 17 pictures before the day was over."
        paragraphs[paragraph3] = ProblemSet("Paragraphs", mutableListOf<Problem>(
            Problem("Did Paula give her husband a present?", "Yes", null, problemIdCounter.incrementAndGet()),
            Problem("Did Paula give her husband a camera?", "Yes", null, problemIdCounter.incrementAndGet()),
            Problem("Did Paula give the camera to her brother?", "No", null, problemIdCounter.incrementAndGet()),
            Problem("Did Paula's husband enjoy the present?", "Yes", null, problemIdCounter.incrementAndGet()),
            Problem("Did Paula's husband take 37 pictures?", "No", null, problemIdCounter.incrementAndGet()))
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityParagraphsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        updateCurrentValues()
        updateDisplay()

        binding.btnYes.setOnClickListener() {
            if (currentProblem.getAnswer().equals("Yes", true)) {
                binding.tvCorrect.text = "Correct"
            } else {
                binding.tvCorrect.text = "Incorrect"
            }
            updateDisplay()
        }

    }

    private fun updateDisplay() {
        if (currentProblemSet.getProblemSetCount() > 0) {
            binding.tvParagraph.text = currentParagraph
            currentProblem = currentProblemSet.getProblemSet().first()
            binding.tvQuestion.text = currentProblem.getQuestion()
            currentProblemSet.getProblemSet().remove(currentProblem)
        } else {
            // No questions left in this problem set, get next problem set if it exists
            updateCurrentValues()
        }

    }

    private fun updateCurrentValues() {
        if (shuffledMap.size > 0) {
            currentParagraph = shuffledMap.keys.first()
            currentProblemSet = shuffledMap.values.first()
            shuffledMap.remove(shuffledMap.keys.first())
        } else {
            // No more paragraphs left
            currentParagraph = "No more paragraphs left"
            binding.tvParagraph.text = currentParagraph
            currentProblem = Problem("-blank-", "", null, 0)
            binding.tvQuestion.text = currentProblem.getQuestion()
        }
    }

    private fun shuffleParagraphs() : LinkedHashMap<String, ProblemSet> {
        val keys = ArrayList(paragraphs.keys)
        keys.shuffle()
        val shuffledMap = LinkedHashMap<String, ProblemSet>()
        for (key in keys) {
            shuffledMap[key] = paragraphs[key]!!
        }
        return shuffledMap
    }

}