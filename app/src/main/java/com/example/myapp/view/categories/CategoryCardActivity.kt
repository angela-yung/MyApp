package com.example.myapp.view.categories

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapp.databinding.ActivityCategoryCardBinding

private const val TAG : String = "CategoryCardActivity"

class CategoryCardActivity : AppCompatActivity () {
    private lateinit var binding: ActivityCategoryCardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryCardBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}