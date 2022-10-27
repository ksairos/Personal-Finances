package com.example.personalfinances

import android.content.Context
import android.content.res.Resources
import androidx.room.TypeConverter
import java.math.RoundingMode
import java.text.DecimalFormat
import java.util.*

class Utils {
    companion object{
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

        // Tags for the Dialogs
        const val COLOR_PICK_TAG: String = "colorPickTag"

        // Round Float to two numbers after the floating point
        fun roundDouble(number: Double?): Double?{
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
            context.getColor(R.color.color16)
        )



//        val ICONS = listOf(
//            R.drawable.category_transport_48,
//            R.drawable.category_giftcard_48,
//            R.drawable.ic_outline_fastfood_48,
//            R.drawable.ic_outline_house_48,
//            R.drawable.ic_outline_shopping_bag_48
//        )
//
//        val COLORS = listOf(
//            R.color.m3_black,
//            R.color.m3_sys_light_on_primary_container,
//            R.color.m3_sys_light_on_error_container,
//            R.color.my_green
//        )
    }

    // context class allows us to convert the Date class into Long, because Date is not available for Room DB
    class DateConverters {
        @TypeConverter
        fun toDate(value: Long?): Date? {
            return value?.let { Date(it) }
        }

        @TypeConverter
        fun fromDate(date: Date?): Long? {
            return date?.time
        }
    }

}