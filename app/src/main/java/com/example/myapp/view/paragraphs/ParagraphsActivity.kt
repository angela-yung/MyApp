package com.example.myapp.view.paragraphs

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapp.databinding.ActivityParagraphsBinding
import com.example.myapp.model.Problem
import com.example.myapp.model.ProblemSet
import com.example.myapp.view.categories.SelectCategoryActivity
import java.util.concurrent.atomic.AtomicInteger

private const val CATEGORY_NAME : String = "Paragraphs"
class ParagraphsActivity: AppCompatActivity() {
    private lateinit var binding: ActivityParagraphsBinding
    private var paragraphs = LinkedHashMap<String, ProblemSet>()
    private var problemIdCounter = AtomicInteger()
    private var currentParagraph = ""
    private var currentProblemSet = ProblemSet(CATEGORY_NAME, mutableListOf<Problem>())
    private var currentProblem = Problem("", "", null, 0)
    private var shuffledMap = LinkedHashMap<String, ProblemSet>()
    private var score = 0

    init {
        // Assign paragraphs to problem sets
        var paragraph1 = "Maria went to the grocery store on Monday afternoon. She bought milk, eggs," +
                "fresh vegetables, and meat."
        paragraphs[paragraph1] = ProblemSet(
            "Paragraphs", mutableListOf<Problem>(
                Problem(
                    "Did Maria go to the grocery store?",
                    "Yes",
                    null,
                    problemIdCounter.incrementAndGet()
                ),
                Problem(
                    "Did Maria go to the grocery store on Friday?",
                    "No",
                    null,
                    problemIdCounter.incrementAndGet()
                ),
                Problem(
                    "Did Maria go to the grocery store in the morning?",
                    "No",
                    null,
                    problemIdCounter.incrementAndGet()
                ),
                Problem("Did Maria buy eggs?", "Yes", null, problemIdCounter.incrementAndGet()),
                Problem("Did Maria buy soap?", "No", null, problemIdCounter.incrementAndGet())
            )
        )

        // reset idCounter
        problemIdCounter.set(0)

        var paragraph2 = "Anthony thought he had a cavity. He called the dentist's office and got an" +
                "appointment for Wednesday at 2:00 p.m."
        paragraphs[paragraph2] = ProblemSet(
            "Paragraphs", mutableListOf<Problem>(
                Problem(
                    "Did Anthony get an appointment?",
                    "Yes",
                    null,
                    problemIdCounter.incrementAndGet()
                ),
                Problem(
                    "Was the appointment for Monday?",
                    "No",
                    null,
                    problemIdCounter.incrementAndGet()
                ),
                Problem(
                    "Did Anthony call the dentist's office?",
                    "Yes",
                    null,
                    problemIdCounter.incrementAndGet()
                ),
                Problem(
                    "Did Anthony think he had a cavity?",
                    "Yes",
                    null,
                    problemIdCounter.incrementAndGet()
                ),
                Problem(
                    "Was his appointment for 3:00 p.m.?",
                    "No",
                    null,
                    problemIdCounter.incrementAndGet()
                )
            )
        )

        // reset idCounter
        problemIdCounter.set(0)

        var paragraph3 = "Paula gave her husband a new camera for his birthday. He enjoyed it so much" +
                "that he took 17 pictures before the day was over."
        paragraphs[paragraph3] = ProblemSet(
            "Paragraphs", mutableListOf<Problem>(
                Problem(
                    "Did Paula give her husband a present?",
                    "Yes",
                    null,
                    problemIdCounter.incrementAndGet()
                ),
                Problem(
                    "Did Paula give her husband a camera?",
                    "Yes",
                    null,
                    problemIdCounter.incrementAndGet()
                ),
                Problem(
                    "Did Paula give the camera to her brother?",
                    "No",
                    null,
                    problemIdCounter.incrementAndGet()
                ),
                Problem(
                    "Did Paula's husband enjoy the present?",
                    "Yes",
                    null,
                    problemIdCounter.incrementAndGet()
                ),
                Problem(
                    "Did Paula's husband take 37 pictures?",
                    "No",
                    null,
                    problemIdCounter.incrementAndGet()
                )
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityParagraphsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        var extras = intent.extras

        if (extras != null) {
            shuffledMap = intent.getSerializableExtra("paragraphs") as LinkedHashMap<String, ProblemSet>
        } else {
            shuffledMap = shuffleParagraphs()
        }

        if (isEmptyShuffledList()) {
            // No more paragraphs left, go to menu
            returnToCategorySelection()
        }

        updateParagraphAndProblemSet()
        updateProblem()
        updateDisplay()

        binding.btnYes.setOnClickListener() {
            // Extract below to a new method called checkAnswer() and use for btnYes and btnNo
            checkAnswer("Yes")
            getNextProblem()
        }

        binding.btnNo.setOnClickListener() {
            // Extract below to a new method called checkAnswer() and use for btnYes and btnNo
            checkAnswer("No")
            getNextProblem()
        }

        binding.iBtnBackArrowParagraphs.setOnClickListener() {
            returnToCategorySelection()
        }
    }

    private fun returnToCategorySelection() {
        val intent = Intent(this, SelectCategoryActivity::class.java)
        startActivity(intent)
    }
    private fun checkAnswer(btnType: String) {
        if (currentProblem.getAnswer().equals(btnType, true)) {
//            binding.tvCorrect.setTextColor(0x249034)
//            binding.tvCorrect.text = "Correct"
            score++
        } else {
//            binding.tvCorrect.setTextColor(0x7a113d)
//            binding.tvCorrect.text = "Incorrect"
        }
    }

    private fun getNextProblem() {
        if (!isEmptyProblemSet()) {
            updateProblem()
        } else {
            // No questions left in this problem set
            loadEndOfExercise()
        }
        updateDisplay()
    }

    private fun updateDisplay() {
        binding.tvParagraph.text = currentParagraph
        binding.tvQuestion.text = currentProblem.getId().toString() + ". " + currentProblem.getQuestion()
    }

    private fun updateProblem() {
        currentProblem = currentProblemSet.getProblemSet().first()
        currentProblemSet.getProblemSet().remove(currentProblem)
    }

    private fun isEmptyProblemSet() : Boolean {
        return currentProblemSet.getProblemSetCount() == 0
    }

    private fun isEmptyShuffledList() : Boolean {
        return shuffledMap.size == 0
    }

    private fun updateParagraphAndProblemSet() {
        currentParagraph = shuffledMap.keys.first()
        currentProblemSet = shuffledMap.values.first()
        shuffledMap.remove(shuffledMap.keys.first())
    }


    private fun loadEndOfExercise() {
        val intent = Intent(this, EndOfExerciseActivity::class.java)
        intent.putExtra("paragraphs", shuffledMap)
        intent.putExtra("score", score.toString())
        startActivity(intent)
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