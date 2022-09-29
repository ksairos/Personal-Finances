package com.example.personalfinances

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.personalfinances.databinding.CategoryItemMainBinding

class CategoryAdapter(private val allCategories: List<Category>): RecyclerView.Adapter<CategoryAdapter.PlaceHolder>() {

    class PlaceHolder(item: View): RecyclerView.ViewHolder(item) {
        private val binding = CategoryItemMainBinding.bind(item)

        fun bind(category: Category) = with(binding){
            categoryName.text = category.name
            categoryIcon.setBackgroundColor(category.color)
            categoryIcon.setIconResource(category.icon)
            val text = "$" + category.expanses.toString()
            categoryPrice.text = text
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.category_item_main, parent, false)
        return PlaceHolder(view)
    }

    override fun onBindViewHolder(holder: PlaceHolder, position: Int) {
        holder.bind(allCategories[position])
    }

    override fun getItemCount(): Int {
        return allCategories.size
    }

    // TODO: Try using Intent to send the data from AddCategoryActivity to MainActivity and use addCategory() there
//    fun addCategory(newCategory: Category, db: MainDb){
//        Thread {
//            db.getDao().insert(newCategory)
//            notifyItemInserted(-1)
//        }.start()
//    }


}