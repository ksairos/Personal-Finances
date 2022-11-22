package com.example.personalfinances.accounts

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.personalfinances.PersonalFinancesApplication
import com.example.personalfinances.R
import com.example.personalfinances.Utils
import com.example.personalfinances.data.Account
import com.example.personalfinances.databinding.AccountItemRecviewBinding
import com.example.personalfinances.fragments.AccountBottomSheetFragment


class AccountsAdapter(private val mContext: Context?) :
    ListAdapter<Account, AccountsAdapter.ViewHolder>(DiffCallback()) {



    class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        private val binding = AccountItemRecviewBinding.bind(item)
        private val bottomSheetFragment = AccountBottomSheetFragment()

        fun bind(account: Account, mContext: Context?) = with(binding) {
            // Set account name
            accountName.text = account.name
            // Set balance
            val temp = String.format("$%.0f", account.balance)
            accountBalance.text = temp
            // Set Icon Color
            account.color?.let { accountIcon.setBackgroundColor(it) }
            // Set Icon image
            accountIcon.icon = account.icon?.let {
                PersonalFinancesApplication.instance.iconPack?.getIcon(
                    it
                )?.drawable
            }

            accountIcon.setOnClickListener{
                val bundle = Bundle()
                bundle.putInt(Utils.ACC_ID_TAG, account.id!!)
                bottomSheetFragment.arguments = bundle
                bottomSheetFragment.show((mContext as AppCompatActivity).supportFragmentManager, AccountBottomSheetFragment.TAG)
            }
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
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.account_item_recview, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), mContext)
    }
}