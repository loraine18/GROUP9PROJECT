package com.example.anima

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Mock data representing backend response
        val wellnessData = WellnessData(
            userName = "Loraine",
            date = "Monday, Oct 23",
            sleepDuration = "8h 15m",
            sleepQuality = "Excellent",
            waterIntake = 2.1f,
            waterGoal = 3.0f,
            heartRate = 68
        )

        updateUI(wellnessData)
    }

    private fun updateUI(data: WellnessData) {
        findViewById<TextView>(R.id.tvGreeting).text = "Hello, ${data.userName}!"
        findViewById<TextView>(R.id.tvDate).text = data.date
        
        findViewById<TextView>(R.id.tvSleepDuration).text = data.sleepDuration
        findViewById<TextView>(R.id.tvSleepQuality).text = data.sleepQuality
        
        findViewById<TextView>(R.id.tvWaterIntake).text = "${data.waterIntake} L"
        findViewById<TextView>(R.id.tvWaterGoal).text = "Goal: ${data.waterGoal} L"
        
        findViewById<TextView>(R.id.tvBPM).text = "${data.heartRate} BPM"
    }
}
