package com.example.personalfinances

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.personalfinances.data.Account
import com.example.personalfinances.databinding.AccountItemRecviewBinding


class AccountAdapter: ListAdapter<Account, AccountAdapter.ViewHolder>(DiffCallback()) {

    class ViewHolder(item: View): RecyclerView.ViewHolder(item){
        private val binding = AccountItemRecviewBinding.bind(item)

        fun bind(account: Account) = with(binding){
            accountName.text = account.name
            accountBalance.text = String.format("$%.2f", account.balance)
            account.icon?.let { accountIcon.setIconResource(it) }
            account.color?.let { accountIcon.setBackgroundColor(it) }
        }

    }

    // DiffUtil implementation
    class DiffCallback : DiffUtil.ItemCallback<Account>() {
        override fun areItemsTheSame(oldItem: Account, newItem: Account) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Account, newItem: Account) =
            oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.account_item_recview, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}