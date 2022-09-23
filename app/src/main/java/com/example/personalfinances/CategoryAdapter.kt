package com.example.personalfinances

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.personalfinances.databinding.CategoryItemMainBinding

class CategoryAdapter: RecyclerView.Adapter<CategoryAdapter.PlaceHolder>() {

    class PlaceHolder(item: View): RecyclerView.ViewHolder(item) {
        private val binding = CategoryItemMainBinding.bind(item)

        fun bind(category: Category) = with(binding){
            categoryName.text = category.name
            categoryIcon.setIconResource(category.icon)
            categoryPrice.text = category.expanses.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.category_item_main, parent, false)
        return PlaceHolder(view)
    }

    override fun onBindViewHolder(holder: PlaceHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }


}