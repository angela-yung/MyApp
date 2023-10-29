package com.example.myapp.view.paragraphs

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.myapp.databinding.ActivityParagraphsBinding
import com.example.myapp.model.Problem
import com.example.myapp.model.ProblemSet
import com.example.myapp.view.categories.SelectCategoryActivity
import java.util.concurrent.atomic.AtomicInteger

private const val CATEGORY_NAME : String = "Paragraphs"
private const val TAG = "ParagraphsActivity"
class ParagraphsActivity: AppCompatActivity() {
    private lateinit var binding: ActivityParagraphsBinding
    private var paragraphs = HashMap<String, ProblemSet>()
    private var problemIdCounter = AtomicInteger()
    private var totalNumProblems = 0
    private var currentParagraph = ""
    private var currentProblemSet = ProblemSet(CATEGORY_NAME, mutableListOf<Problem>())
    private var currentProblem = Problem("", "", null, 0)
//    private var shuffledMap = LinkedHashMap<String, ProblemSet>()
    private var score = 0

    init {
       initializeParagraphs()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityParagraphsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        var extras = intent.extras

        if (extras != null) {
//            var temp = intent.getSerializableExtra("paragraphs") as HashMap<String, ProblemSet>
            try {
                paragraphs = intent.getSerializableExtra("paragraphs") as HashMap<String, ProblemSet>
//                paragraphs = temp.toMap() as LinkedHashMap<String, ProblemSet>
                Log.d(TAG, "intent received the following paragraphs: ")
                logRemainingParagraphs()
            } catch (e: Exception) {
                Log.e(TAG, "Exception: $e")
                Log.e(TAG, "paragraphs is null")
            }

        } else {
            shuffleParagraphs()
            Log.d(TAG, "Paragraphs after shuffling initially: ")
            logRemainingParagraphs()
            totalNumProblems = paragraphs.size
        }

//        if (savedInstanceState != null) {
//            var temp = savedInstanceState.getSerializable("paragraphs") as HashMap<String, ProblemSet>
//            shuffledMap = (temp.toMap() as? LinkedHashMap<String, ProblemSet>)!!
//            Log.d(TAG, "savedInstanceState received the following paragraphs: ")
//            logRemainingParagraphs()
//
//        } else {
//            shuffledMap = shuffleParagraphs()
//            Log.d(TAG, "Initial shuffledMap: ")
//            logRemainingParagraphs()
//        }

        if (isEmptyParagraphs()) {
            // No more paragraphs left, go to menu
            Log.d(TAG, "No more paragraphs left, going to menu")
            returnToCategorySelection()
        } else {
            updateParagraphAndProblemSet()
            updateProblem()
            updateDisplay()
        }
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

//    override fun onSaveInstanceState(outState: Bundle) {
//        super.onSaveInstanceState(outState)
//        outState.putSerializable("paragraphs", shuffledMap)
//    }

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
            Log.d(TAG, "paragraphs before sending to EndOfExerciseActivity: ")
            logRemainingParagraphs()
            Log.d(TAG, "Map type: ${paragraphs.javaClass}")
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

    private fun isEmptyParagraphs() : Boolean {
        return paragraphs.size == 0
    }

    private fun updateParagraphAndProblemSet() {
        currentParagraph = paragraphs.keys.first()
        currentProblemSet = paragraphs.values.first()
        paragraphs.remove(currentParagraph)
        Log.d(TAG, "number of paragraphs remaining after removing current paragraph = ${paragraphs?.size}")
    }

    private fun logRemainingParagraphs() {
        for (paragraph in paragraphs.keys) {
            Log.d(TAG, "paragraph = $paragraph")
        }
    }


    private fun loadEndOfExercise() {
        val intent = Intent(this, EndOfExerciseActivity::class.java)
        intent.putExtra("paragraphs", paragraphs)
        intent.putExtra("score", score)
        intent.putExtra("totalScore", totalNumProblems)
        startActivity(intent)
    }

    private fun shuffleParagraphs() {
        val keys = ArrayList(paragraphs.keys)
//        Log.d(TAG, "Before shuffling keys:")
//        logKeys()
        keys.shuffle()
//        Log.d(TAG, "After shuffling keys:")
//        logKeys()
        val shuffledMap = HashMap<String, ProblemSet>()
        for (key in keys) {
            shuffledMap[key] = paragraphs[key]!!
        }
        paragraphs = HashMap(shuffledMap)
    }

    private fun logKeys() {
        for (key in paragraphs.keys) {
            Log.d(TAG, "key = $key")
        }
    }

    private fun initializeParagraphs() {
        // Assign paragraphs to problem sets
        var paragraph1 = "Maria went to the grocery store on Monday afternoon. She bought milk, eggs," +
                " fresh vegetables, and meat."
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
                Problem("Did Maria buy eggs?",
                    "Yes",
                    null,
                    problemIdCounter.incrementAndGet()),
                Problem("Did Maria buy soap?",
                    "No",
                    null,
                    problemIdCounter.incrementAndGet())
            )
        )

        // reset idCounter
        problemIdCounter.set(0)

        var paragraph2 = "Anthony thought he had a cavity. He called the dentist's office and got an" +
                " appointment for Wednesday at 2:00 p.m."
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
                " that he took 17 pictures before the day was over."
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

        // reset idCounter
        problemIdCounter.set(0)

        var paragraph4 = "The shirt was on sale at the discount store for $19. Alice didn't have" +
                " enough cash, so she decided to charge it on her credit card."
        paragraphs[paragraph1] = ProblemSet(
            "Paragraphs", mutableListOf<Problem>(
                Problem(
                    "Was Alice buying pants?",
                    "No",
                    null,
                    problemIdCounter.incrementAndGet()
                ),
                Problem(
                    "Was the shirt on sale?",
                    "Yes",
                    null,
                    problemIdCounter.incrementAndGet()
                ),
                Problem(
                    "Did the shirt cost $39?",
                    "No",
                    null,
                    problemIdCounter.incrementAndGet()
                ),
                Problem("Did Alice have enough cash?",
                    "No",
                    null, problemIdCounter.incrementAndGet()),
                Problem("Did Alice use her credit card?",
                    "Yes",
                    null,
                    problemIdCounter.incrementAndGet())
            )
        )

        // reset idCounter
        problemIdCounter.set(0)

        var paragraph5 = "It was Saturday afternoon and Miguel had several things to do. He" +
                " couldn’t decide if he should mow the lawn first or wait until later. After" +
                " listening to the weather forecast, Miguel decided to mow the lawn right" +
                " away and do the other jobs later."
        paragraphs[paragraph1] = ProblemSet(
            "Paragraphs", mutableListOf<Problem>(
                Problem(
                    "Was it a Friday afternoon?",
                    "No",
                    null,
                    problemIdCounter.incrementAndGet()
                ),
                Problem(
                    "Was Miguel trying to decide what to do first?",
                    "Yes",
                    null,
                    problemIdCounter.incrementAndGet()
                ),
                Problem(
                    "Did he decide to mow the lawn on Sunday?",
                    "No",
                    null,
                    problemIdCounter.incrementAndGet()
                ),
                Problem("Did he check the weather forecast?",
                    "Yes",
                    null,
                    problemIdCounter.incrementAndGet()),
                Problem("Did Miguel decide to trim the bushes right away?",
                    "No",
                    null,
                    problemIdCounter.incrementAndGet())
            )
        )

        // reset idCounter
        problemIdCounter.set(0)

        var paragraph6 = "Mr. and Mrs. Montel had been driving a long time and they were getting" +
                " hungry. Finally, they found a fast-food restaurant. They decided to go" +
                " inside instead of using the drive-thru window. After eating, they began" +
                " looking for a motel."
        paragraphs[paragraph1] = ProblemSet(
            "Paragraphs", mutableListOf<Problem>(
                Problem(
                    "Had Mr. and Mrs. Montel been driving a long time?",
                    "Yes",
                    null,
                    problemIdCounter.incrementAndGet()
                ),
                Problem(
                    "Were they hungry?",
                    "Yes",
                    null,
                    problemIdCounter.incrementAndGet()
                ),
                Problem(
                    "Did they eat at home?",
                    "No",
                    null,
                    problemIdCounter.incrementAndGet()
                ),
                Problem("Did they eat inside the restaurant?",
                    "Yes",
                    null,
                    problemIdCounter.incrementAndGet()),
                Problem("Did they go right home after eating?",
                    "No",
                    null,
                    problemIdCounter.incrementAndGet())
            )
        )

        // reset idCounter
        problemIdCounter.set(0)

        var paragraph7 = "Wilma had been training for three months for the marathon race. She ran" +
                "for two hours a day, swam for one hour a day, and worked out at the gym" +
                "three times each week. The marathon was scheduled for April 22, just two" +
                "weeks away. Wilma felt she was as ready as she’d ever be, and working" +
                "out the next two weeks would keep her in top condition."
        paragraphs[paragraph1] = ProblemSet(
            "Paragraphs", mutableListOf<Problem>(
                Problem(
                    "Had Mr. and Mrs. Montel been driving a long time?",
                    "Yes",
                    null,
                    problemIdCounter.incrementAndGet()
                ),
                Problem(
                    "Were they hungry?",
                    "Yes",
                    null,
                    problemIdCounter.incrementAndGet()
                ),
                Problem(
                    "Did they eat at home?",
                    "No",
                    null,
                    problemIdCounter.incrementAndGet()
                ),
                Problem("Did they eat inside the restaurant?",
                    "Yes",
                    null,
                    problemIdCounter.incrementAndGet()),
                Problem("Did they go right home after eating?",
                    "No",
                    null,
                    problemIdCounter.incrementAndGet())
            )
        )

        // reset idCounter
        problemIdCounter.set(0)
    }
}