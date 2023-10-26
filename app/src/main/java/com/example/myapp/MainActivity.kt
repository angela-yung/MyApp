package com.example.myapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapp.databinding.ActivityMainBinding
import com.example.myapp.view.categories.SelectCategoryActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnStart.setOnClickListener {
            openSelectCategoryActivity()
        }
    }

    private fun openSelectCategoryActivity() {
        val intent = Intent(this, SelectCategoryActivity::class.java)
        startActivity(intent)
    }
}