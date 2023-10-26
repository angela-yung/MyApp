package com.example.myapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.R
import com.example.myapp.databinding.ActivitySelectCategoryBinding
import com.example.myapp.model.Category
import com.example.myapp.model.RecyclerViewCategoryAdapter

private const val TAG = "SelectCategoryActivity"
private const val PARAGRAPHS_CATEGORY = "Paragraphs"
private const val HISTORY_CATEGORY = "History"
private const val RANDOM_TRIVIA_CATEGORY = "Random Trivia"
class SelectCategoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySelectCategoryBinding
    private var recyclerView : RecyclerView? = null
    private var recyclerViewCategoryAdapter: RecyclerViewCategoryAdapter? = null
    private var categoryList = mutableListOf<Category>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectCategoryBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        categoryList = ArrayList()
        recyclerView = binding.rvCategories
        recyclerViewCategoryAdapter = RecyclerViewCategoryAdapter(this, categoryList)
        val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(this, 2)
        recyclerView!!.layoutManager = layoutManager
        recyclerView!!.adapter = recyclerViewCategoryAdapter

        initializeCategoryList()
    }

    private fun initializeCategoryList() {
        var category = Category(RANDOM_TRIVIA_CATEGORY, R.drawable.brain_quiz_icon)
        categoryList.add(category)
        category = Category(HISTORY_CATEGORY, R.drawable.colosseum_icon)
        categoryList.add(category)
        category = Category(PARAGRAPHS_CATEGORY, R.drawable.colosseum_icon)
        categoryList.add(category)

        recyclerViewCategoryAdapter!!.notifyDataSetChanged()
    }

}