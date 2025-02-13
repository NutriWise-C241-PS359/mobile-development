package com.product.nutriwise.utils

import java.text.SimpleDateFormat
import java.util.Locale

object Utils {
    fun calculateBMI(weight: Double, height: Double): Double {
        return weight / ((height/100) * (height/100))
    }

    fun getBMICategory(bmi: Double, gender: Boolean): String {
        return when (gender) {
            true -> getBMICategoryForMale(bmi)
            false -> getBMICategoryForFemale(bmi)
        }
    }

    fun convertDateFormat(dateString: String, fromFormat: String = "yyyy-MM-dd", toFormat: String = "dd-MM-yyyy"): String {
        return try {
            val sdfFrom = SimpleDateFormat(fromFormat, Locale.getDefault())
            val sdfTo = SimpleDateFormat(toFormat, Locale.getDefault())
            val date = sdfFrom.parse(dateString)
            date?.let { sdfTo.format(it) } ?: ""
        } catch (e: Exception) {
            ""
        }
    }

    private fun getBMICategoryForMale(bmi: Double): String {
        return when {
            bmi < 18.5 -> "Underweight"
            bmi >= 18.5 && bmi < 24.9 -> "Normal Weight"
            bmi >= 24.9 && bmi < 29.9 -> "Overweight"
            else -> "Obesity"
        }
    }

    private fun getBMICategoryForFemale(bmi: Double): String {
        return when {
            bmi < 18.5 -> "Underweight"
            bmi >= 18.5 && bmi < 23.9 -> "Normal Weight"
            bmi >= 23.9 && bmi < 28.9 -> "Overweight"
            else -> "Obesity"
        }
    }
}