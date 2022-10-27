package com.example.personalfinances.features.colorpicker

import android.content.Context
import android.content.res.Resources
import android.content.res.TypedArray
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.personalfinances.R
import com.example.personalfinances.databinding.ColorPickerItemBinding

class ColorAdapter(private val mContext: Context?): RecyclerView.Adapter<ColorAdapter.MyViewHolder>() {

    private val TAG = "ColorAdapter"
    var colorList = listOf<Int?>()

    class MyViewHolder(view: View): RecyclerView.ViewHolder(view){
        // No need to inflate, because we receive already inflated view
        private val binding = ColorPickerItemBinding.bind(view)

        fun bind(color: Int?, mContext: Context?) = with(binding){
            color?.let { colorBlob.background.setTint(color) }

            colorBlob.setOnClickListener {
                Toast.makeText(mContext, "${color}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // Take LayoutInflater from tha parent view's context to inflate our plant_item view
        val view = LayoutInflater.from(parent.context).inflate(R.layout.color_picker_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Log.d(TAG, "PRINT CURRENT COLOR: ${colorList[position]}")
        holder.bind(colorList[position], mContext)
    }

    override fun getItemCount(): Int {
        return colorList.size
    }

}