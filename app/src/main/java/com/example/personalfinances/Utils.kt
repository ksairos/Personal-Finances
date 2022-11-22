package com.example.personalfinances

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import eltos.simpledialogfragment.color.SimpleColorDialog
import java.math.RoundingMode
import java.text.DecimalFormat

class Utils {
    companion object {
        // Keys for our future intents
        const val CAT_NAME_KEY: String = "catNameKey"
        const val CAT_ICON_KEY: String = "catIconKey"
        const val CAT_COLOR_KEY: String = "catColorKey"

        const val ACC_COLOR_KEY: String = "accColorKey"
        const val ACC_NAME_KEY: String = "accNameKey"
        const val ACC_ICON_KEY: String = "accIconKey"
        const val ACC_BALANCE_KEY: String = "accBalanceKey"

        const val TRANSACTION_ID_TO_KEY: String = "transactionIdToKey"
        const val TRANSACTION_NAME_TO_KEY: String = "transactionNameToKey"

        // Tags for the color dialog
        const val COLOR_PICK_TAG: String = "colorPickTag"

        // Tags for Icon Picker
        const val ICON_DIALOG_TAG = "iconDialog"

        // Tag for Bundle
        const val ACC_ID_TAG = "accountID"


        // Round Float to two numbers after the floating point
        fun roundDouble(number: Double?): Double? {
            val df = DecimalFormat("#.##")
            df.roundingMode = RoundingMode.HALF_UP
            return df.format(number)?.toDouble()
        }

        fun colorArray(context: Context) = intArrayOf(
            context.getColor(R.color.color1),
            context.getColor(R.color.color2),
            context.getColor(R.color.color3),
            context.getColor(R.color.color4),
            context.getColor(R.color.color5),
            context.getColor(R.color.color6),
            context.getColor(R.color.color7),
            context.getColor(R.color.color8),
            context.getColor(R.color.color9),
            context.getColor(R.color.color10),
            context.getColor(R.color.color11),
            context.getColor(R.color.color12),
            context.getColor(R.color.color13),
            context.getColor(R.color.color14),
            context.getColor(R.color.color15),
            context.getColor(R.color.color16),
        )

        //? Color Picker Functionality
        // Build and display the Color Picker Dialog
        fun showColorPickerDialog(mContext: Context, fragment: FragmentActivity) {
            SimpleColorDialog.build()
                .title(mContext.getString(R.string.icon_color))
                .colors(colorArray(mContext))
                .show(fragment, COLOR_PICK_TAG)
        }

    }



}