package com.example.myapp.view.categories

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.databinding.ActivityCategoryCardBinding
import com.example.myapp.model.Category
import com.example.myapp.view.demo.SolveActivity
import com.example.myapp.view.paragraphs.ParagraphsActivity

private const val TAG : String = "RecyclerViewCategoryAdapter"

class RecyclerViewCategoryAdapter(private val context: Context, private val categoryList: List<Category>) :
    RecyclerView.Adapter<RecyclerViewCategoryAdapter.MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ActivityCategoryCardBinding.inflate(layoutInflater, parent, false)
        return MyViewHolder(context, binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(categoryList[position])
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    class MyViewHolder(private val context: Context, private val binding: ActivityCategoryCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(categoryItem: Category) {
            binding.btnCategoryName.text = categoryItem.category
            binding.ivCategory.setImageResource(categoryItem.image)
            binding.btnCategoryName.setOnClickListener() {
                startCategoryActivity(binding.btnCategoryName.text.toString())
                Toast.makeText(context, "Category selected!", Toast.LENGTH_SHORT).show()
            }
        }

        private fun startCategoryActivity(category: String) {
            Log.d(TAG, "Passed \"$category\" to startCategoryActivity")
            if (category == "Paragraphs") {
                val intent = Intent(context, ParagraphsActivity::class.java)
                context.startActivity(intent)
            } else {
                val intent = Intent(context, SolveActivity::class.java)
                intent.putExtra("category", category)
                Log.d(TAG, "Attempting to start an activity...")
                context.startActivity(intent)
            }
        }
    }
}