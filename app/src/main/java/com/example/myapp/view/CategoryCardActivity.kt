package com.example.myapp.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapp.databinding.LayoutCategoryBinding

private const val TAG : String = "CategoryCardActivity"

class CategoryCardActivity : AppCompatActivity() {
    private lateinit var binding: LayoutCategoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LayoutCategoryBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

}