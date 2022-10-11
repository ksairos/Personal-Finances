package com.example.personalfinances.categories

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.personalfinances.MakeTransactionActivity
import com.example.personalfinances.R
import com.example.personalfinances.Utils
import com.example.personalfinances.data.Category
import com.example.personalfinances.databinding.CategoryItemRecviewBinding
import java.lang.Math.round
import kotlin.math.roundToInt

// Here we are using ListAdapter instead of usual RecyclerView.Adapter and pass there DiffCallback
class CategoryAdapter(private val mContext: Context?): ListAdapter<Category, CategoryAdapter.ViewHolder>(DiffCallback()) {

    class ViewHolder(item: View): RecyclerView.ViewHolder(item) {
        private val binding = CategoryItemRecviewBinding.bind(item)

        fun bind(category: Category, mContext: Context?) = with(binding){
            categoryName.text = category.name
            category.color?.let { categoryIcon.setBackgroundColor(it) }
            category.icon?.let { categoryIcon.setIconResource(it) }
            val text = "$" + category.expanses?.toString()
            categoryPrice.text = text
            categoryIcon.setOnClickListener{ startTransaction(mContext) }

        }

        private fun startTransaction(mContext: Context?) {
            val intent = Intent(mContext, MakeTransactionActivity::class.java)
            intent.putExtra(Utils.TRANSACTION_TO_KEY, binding.categoryName.text.toString())
            mContext?.startActivity(intent)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.category_item_recview, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), mContext)
    }


    // DiffUtil implementation
    class DiffCallback : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Category, newItem: Category) =
            oldItem == newItem
    }

}