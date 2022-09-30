package com.example.personalfinances

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.personalfinances.databinding.CategoryItemMainBinding

// Here we are using ListAdapter instead of usual RecyclerView.Adapter and pass there DiffCallback
class CategoryAdapter: ListAdapter<Category, CategoryAdapter.PlaceHolder>(DiffCallback()) {

    class PlaceHolder(item: View): RecyclerView.ViewHolder(item) {
        private val binding = CategoryItemMainBinding.bind(item)

        fun bind(category: Category) = with(binding){
            categoryName.text = category.name
            category.color?.let { categoryIcon.setBackgroundColor(it) }
            category.icon?.let { categoryIcon.setIconResource(it) }
            val text = "$" + category.expanses.toString()
            categoryPrice.text = text
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.category_item_main, parent, false)
        return PlaceHolder(view)
    }

    override fun onBindViewHolder(holder: PlaceHolder, position: Int) {
        holder.bind(getItem(position))
    }

    // DiffUtil implementation
    class DiffCallback : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Category, newItem: Category) =
            oldItem == newItem
    }

}