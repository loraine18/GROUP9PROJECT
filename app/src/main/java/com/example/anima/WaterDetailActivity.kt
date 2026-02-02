package com.example.anima

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.anima.databinding.ActivityWaterDetailBinding

class WaterDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWaterDetailBinding
    private var totalIntake: Float = 0.0f
    private var waterGoal: Float = 2.5f
    private val glassSize: Float = 0.25f // 250ml per glass

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityWaterDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get initial values from MainActivity
        totalIntake = intent.getFloatExtra("CURRENT_INTAKE", 0.0f)
        waterGoal = intent.getFloatExtra("WATER_GOAL", 2.5f)

        updateIntakeUI()

        binding.btnAddWater.setOnClickListener {
            showAddWaterDialog()
        }

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun showAddWaterDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Add Water Intake")
        builder.setMessage("How many glasses of water?")

        val input = EditText(this)
        input.inputType = InputType.TYPE_CLASS_NUMBER
        input.setText("1")
        builder.setView(input)

        builder.setPositiveButton("Add") { dialog, _ ->
            val glassesStr = input.text.toString()
            if (glassesStr.isNotEmpty()) {
                val glasses = glassesStr.toInt()
                val addedAmount = glasses * glassSize
                totalIntake += addedAmount
                updateIntakeUI()
                Toast.makeText(this, "Added ${String.format("%.2f", addedAmount)} L", Toast.LENGTH_SHORT).show()
            }
            dialog.dismiss()
        }
        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.cancel()
        }

        builder.show()
    }

    private fun updateIntakeUI() {
        binding.tvTotalIntake.text = String.format("%.1f L", totalIntake)
        binding.tvWaterGoalDetail.text = "Goal: ${String.format("%.1f", waterGoal)} L"
    }

    override fun onBackPressed() {
        val resultIntent = Intent()
        resultIntent.putExtra("UPDATED_INTAKE", totalIntake)
        setResult(Activity.RESULT_OK, resultIntent)
        super.onBackPressed()
    }
}