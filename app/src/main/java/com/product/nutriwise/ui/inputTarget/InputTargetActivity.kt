package com.product.nutriwise.ui.inputTarget

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.product.nutriwise.databinding.ActivityInputTargetBinding
import com.product.nutriwise.ui.main.MainActivity
import java.util.*

class InputTargetActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInputTargetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputTargetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR,14)
        val today = calendar.timeInMillis

        val constraintsBuilder = CalendarConstraints.Builder()
            .setValidator(DateValidatorPointForwardCustom.from(today))

        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Pilih Tanggal")
            .setCalendarConstraints(constraintsBuilder.build())
            .build()

        binding.layoutDate.setEndIconOnClickListener {
            datePicker.show(supportFragmentManager, "DATE_PICKER")
        }

        binding.etDate.setOnClickListener {
            datePicker.show(supportFragmentManager, "DATE_PICKER")
        }

        binding.tvSkip.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        datePicker.addOnPositiveButtonClickListener {
            binding.etDate.setText(datePicker.headerText)
        }
    }
}

class DateValidatorPointForwardCustom(private val minDate: Long) : CalendarConstraints.DateValidator {

    override fun isValid(date: Long): Boolean {
        return date >= minDate
    }

    override fun writeToParcel(dest: android.os.Parcel, flags: Int) {
        dest.writeLong(minDate)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : android.os.Parcelable.Creator<DateValidatorPointForwardCustom> {
        override fun createFromParcel(parcel: android.os.Parcel): DateValidatorPointForwardCustom {
            return DateValidatorPointForwardCustom(parcel.readLong())
        }

        override fun newArray(size: Int): Array<DateValidatorPointForwardCustom?> {
            return arrayOfNulls(size)
        }

        fun from(minDate: Long): DateValidatorPointForwardCustom {
            return DateValidatorPointForwardCustom(minDate)
        }
    }
}
