package com.example.myapp.view.compound

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapp.databinding.ActivityCompoundWordsBinding
import com.example.myapp.model.CompoundWord

class CompoundWords: AppCompatActivity() {
    private lateinit var binding: ActivityCompoundWordsBinding
    private var compoundWords = ArrayList<CompoundWord>()

    init {
        initializeCompoundWords()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCompoundWordsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    private fun initializeCompoundWords() {
        compoundWords = arrayListOf(
            CompoundWord("air", arrayListOf("plane", "port", "field", "borne", "bag", "brush", "conditioner", "ship", "way", "tight")),
            CompoundWord("book", arrayListOf("case", "store", "shelf", "let", "worm", "keeper", "binding", "shop")),
            CompoundWord("court", arrayListOf("yard", "room", "house", "ship")),
            CompoundWord("door", arrayListOf("bell", "frame", "knob", "step", "way", "jamb", "lock")),
            CompoundWord("eye", arrayListOf("ball", "brow", "lash", "lid", "sight", "glasses", "piece", "witness")),
            CompoundWord("foot", arrayListOf("print", "ball", "step", "wear", "stool", "bridge", "locker", "rest", "path")),
            CompoundWord("grape", arrayListOf("fruit", "shot", "vine", "seed", "juice")),
            CompoundWord("hall", arrayListOf("way", "mark",)),
            CompoundWord("ice", arrayListOf("berg", "box", "breaker")),
            CompoundWord("jaw", arrayListOf("bone", "breaker", "line")),
            CompoundWord("key", arrayListOf("board", "chain", "hole", "pad", "stone", "word", "ring", "card")),
            CompoundWord("life", arrayListOf("guard", "line", "style", "span", "saver", "boat", "work")),
            CompoundWord("moon", arrayListOf("light", "beam", "walk", "shine")),
            CompoundWord("night", arrayListOf("fall", "time", "stand", "club", "shade", "cap", "gown", "light", "life", "watch", "mare")),
            CompoundWord("oat", arrayListOf("meal")),
            CompoundWord("pass", arrayListOf("port", "book", "key", "word", "phrase")),
            CompoundWord("quick", arrayListOf("sand", "step", "start")),
            CompoundWord("rain", arrayListOf("coat", "drop", "fall", "forest", "storm", "water", "bow")),
            CompoundWord("sky", arrayListOf("line", "diving", "scraper", "light")),
            CompoundWord("time", arrayListOf("piece", "card", "clock", "out", "zone", "share", "bomb", "line")),
            CompoundWord("utter", arrayListOf("most")),
            CompoundWord("vine", arrayListOf("yard")),
            CompoundWord("week", arrayListOf("day", "end", "night")),
        )

//        compoundWords["butter"] = "fly"
//        compoundWords["pepper"] = "mint"
//        compoundWords["hair"] = "cut"
//        compoundWords["candle"] = "stick"
//        compoundWords["air"] = "port"
//        compoundWords["any"] = "one"
//        compoundWords["north"] = "west"
//        compoundWords["super"] = "hero"
//        compoundWords["rain"] = "check"
//        compoundWords["tooth"] = "paste"
//        compoundWords["fore"] = "head"
//        compoundWords["book"] = "shelf"
//        compoundWords["door"] = "knob"
//        compoundWords["coat"] = "hanger"
//        compoundWords["straw"] = "berry"
//        compoundWords["cup"] = "cake"
//        compoundWords["week"] = "end"

    }
}