package com.example.myapp.model

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.databinding.LayoutCategoryBinding
import com.example.myapp.view.SolveActivity


class RecyclerViewCategoryAdapter(private val context: Context, private val categoryList: List<Category>) :
    RecyclerView.Adapter<RecyclerViewCategoryAdapter.MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = LayoutCategoryBinding.inflate(layoutInflater, parent, false)
        return MyViewHolder(context, binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(categoryList[position])
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    class MyViewHolder(private val context: Context, private val binding: LayoutCategoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(categoryItem: Category) {
            binding.btnCategoryName.text = categoryItem.category
            binding.ivCategory.setImageResource(categoryItem.image)
            binding.btnCategoryName.setOnClickListener() {
                startSolveActivity(binding.btnCategoryName.text.toString())
                Toast.makeText(context, "Category selected!", Toast.LENGTH_SHORT).show()
            }
        }

        private fun startSolveActivity(category: String) {
            val intent = Intent(context, SolveActivity::class.java)
            intent.putExtra("category", category)
            context.startActivity(intent)
        }
    }
}