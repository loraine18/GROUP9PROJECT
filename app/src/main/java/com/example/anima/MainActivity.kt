package com.example.anima

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.card.MaterialCardView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    
    private lateinit var auth: FirebaseAuth
    private var currentWaterIntake: Float = 2.1f
    private var waterGoal: Float = 3.0f
    private var sleepDurationHours: Float = 8.25f // 8h 15m
    private var stepCount: Int = 6432

    private val waterResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val updatedIntake = result.data?.getFloatExtra("UPDATED_INTAKE", currentWaterIntake) ?: currentWaterIntake
            currentWaterIntake = updatedIntake
            updateUI(intent.getStringExtra("USER_NAME") ?: auth.currentUser?.displayName ?: "User")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        
        auth = FirebaseAuth.getInstance()
        
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            // Remove bottom padding from the root view to let BottomNavigationView sit lower
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }

        val loggedInUserName = intent.getStringExtra("USER_NAME") ?: auth.currentUser?.displayName ?: "User"

        // Initialize UI with current values
        updateUI(loggedInUserName)

        findViewById<MaterialCardView>(R.id.cardSleep).setOnClickListener {
            val intent = Intent(this, SleepDetailActivity::class.java)
            startActivity(intent)
        }

        findViewById<MaterialCardView>(R.id.cardWater).setOnClickListener {
            val intent = Intent(this, WaterDetailActivity::class.java)
            intent.putExtra("CURRENT_INTAKE", currentWaterIntake)
            intent.putExtra("WATER_GOAL", waterGoal)
            waterResultLauncher.launch(intent)
        }

        findViewById<MaterialCardView>(R.id.cardSteps).setOnClickListener {
            val intent = Intent(this, StepsDetailActivity::class.java)
            startActivity(intent)
        }

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.selectedItemId = R.id.nav_home
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> true
                R.id.nav_history -> {
                    val intent = Intent(this, HistoryActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                    startActivity(intent)
                    overridePendingTransition(0, 0)
                    true
                }
                R.id.nav_profile -> {
                    val intent = Intent(this, ProfileActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                    startActivity(intent)
                    overridePendingTransition(0, 0)
                    true
                }
                else -> false
            }
        }
    }

    override fun onResume() {
        super.onResume()
        findViewById<BottomNavigationView>(R.id.bottom_navigation).selectedItemId = R.id.nav_home
    }

    private fun updateUI(userName: String) {
        findViewById<TextView>(R.id.tvGreeting).text = "Hello, $userName!"
        findViewById<TextView>(R.id.tvDate).text = "Monday, Oct 23"
        
        findViewById<TextView>(R.id.tvSleepDuration).text = "8h 15m"
        findViewById<TextView>(R.id.tvSleepQuality).text = "Excellent"
        
        findViewById<TextView>(R.id.tvWaterIntake).text = "${String.format("%.1f", currentWaterIntake)} L"
        findViewById<TextView>(R.id.tvWaterGoal).text = "Goal: ${String.format("%.1f", waterGoal)} L"

        // Calculate and update Mood
        val mood = calculateMood(currentWaterIntake, waterGoal, sleepDurationHours, stepCount)
        findViewById<TextView>(R.id.tvMood).text = "Mood: $mood"
    }

    private fun calculateMood(waterIntake: Float, waterGoal: Float, sleepHours: Float, steps: Int): String {
        var score = 0
        
        // Water score
        if (waterIntake >= waterGoal) score += 2
        else if (waterIntake >= waterGoal * 0.5f) score += 1
        
        // Sleep score
        if (sleepHours >= 7) score += 2
        else if (sleepHours >= 5) score += 1
        
        // Steps score
        if (steps >= 8000) score += 2
        else if (steps >= 4000) score += 1
        
        return when {
            score >= 5 -> "Energetic"
            score >= 3 -> "Good"
            else -> "Tired"
        }
    }
}