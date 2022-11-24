package com.example.personalfinances.categories

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.personalfinances.MakeTransactionActivity
import com.example.personalfinances.PersonalFinancesApplication
import com.example.personalfinances.R
import com.example.personalfinances.Utils
import com.example.personalfinances.accounts.EditAccountActivity
import com.example.personalfinances.data.Category
import com.example.personalfinances.databinding.CategoryItemRecviewBinding

// Here we are using ListAdapter instead of usual RecyclerView.Adapter and pass there DiffCallback
class CategoriesAdapter(private val mContext: Context?) :
    ListAdapter<Category, CategoriesAdapter.ViewHolder>(DiffCallback()) {

    class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        private val binding = CategoryItemRecviewBinding.bind(item)

        fun bind(category: Category, mContext: Context?) = with(binding) {
            categoryName.text = category.name
            category.color?.let { categoryIcon.setBackgroundColor(it) }
//            categoryIcon.setIconResource(mContext?.resources?.getIdentifier("category_coffee_24", "drawable", mContext.packageName)!!)
            categoryIcon.icon = category.icon?.let {
                PersonalFinancesApplication.instance.iconPack?.getIcon(
                    it
                )?.drawable
            }

            val text = String.format("$%.0f", category.expenses)
            categoryPrice.text = text

            categoryIcon.setOnClickListener { startTransaction(mContext, category.id, false) }

            categoryIcon.setOnLongClickListener { editCategory(mContext, category.id) }


        }

        private fun startTransaction(mContext: Context?, categoryId: Int?, isTransfer: Boolean) {
            val intent = Intent(mContext, MakeTransactionActivity::class.java)
            intent.putExtra(Utils.TRANSACTION_ID_TO_KEY, categoryId)
            intent.putExtra(Utils.TRANSACTION_IS_TRANSFER, isTransfer)
            mContext?.startActivity(intent)
        }

        private fun editCategory(mContext: Context?, categoryId: Int?): Boolean{
            val intent = Intent(mContext, EditCategoryActivity::class.java)
            intent.putExtra(Utils.CAT_ID_TAG, categoryId)
            mContext?.startActivity(intent)
            return true
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.category_item_recview, parent, false)
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