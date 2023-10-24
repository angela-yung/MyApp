package com.example.myapp.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapp.databinding.LayoutCategoryBinding

class CategoryCardActivity : AppCompatActivity() {
    private lateinit var binding: LayoutCategoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LayoutCategoryBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnCategoryName.setOnClickListener() {
            startSolveActivity(binding.btnCategoryName.text.toString())
            Toast.makeText(this@CategoryCardActivity, "Category selected!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun startSolveActivity(category: String) {
        val intent = Intent(this, SolveActivity::class.java)
        intent.putExtra("category", category)
        startActivity(intent)
    }

}