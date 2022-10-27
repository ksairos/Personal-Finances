package com.example.personalfinances.features.colorpicker

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.personalfinances.R
import com.example.personalfinances.databinding.ColorPickerBinding

class ColorAlertDialog: DialogFragment() {

    private lateinit var recView: RecyclerView

    private var _binding: ColorPickerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = ColorPickerBinding.inflate(layoutInflater)
        return AlertDialog.Builder(requireActivity())
            .setView(binding.root)
            .create()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.color_picker, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val adapter = ColorAdapter(context)
        val colorArray = listOf(
            context?.getColor(R.color.color1),
            context?.getColor(R.color.color2),
            context?.getColor(R.color.color3),
            context?.getColor(R.color.color4),
            context?.getColor(R.color.color5),
            context?.getColor(R.color.color6),
            context?.getColor(R.color.color7),
            context?.getColor(R.color.color8),
            context?.getColor(R.color.color9),
            context?.getColor(R.color.color10),
            context?.getColor(R.color.color11),
            context?.getColor(R.color.color12),
            context?.getColor(R.color.color13),
            context?.getColor(R.color.color14),
            context?.getColor(R.color.color15),
            context?.getColor(R.color.color16)
        )
        recView = binding.recyclerView
        recView.hasFixedSize()
        recView.layoutManager = GridLayoutManager(context, 4)
        adapter.colorList = colorArray

        recView.adapter = adapter
    }

//    override fun onStart() {
//        super.onStart()
//        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
//        val height = (resources.displayMetrics.heightPixels * 0.40).toInt()
//        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
//    }

}