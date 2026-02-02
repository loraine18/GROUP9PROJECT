package com.example.anima

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.anima.databinding.ActivityHistoryBinding

class HistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        setupRecyclerView()
        setupBottomNavigation()
    }

    private fun setupRecyclerView() {
        val historyList: List<HistoryItem> = listOf(
            HistoryItem("Oct 22, 2023", "8h 05m Sleep", "2.8L Water", "8,231 Steps"),
            HistoryItem("Oct 21, 2023", "7h 45m Sleep", "2.5L Water", "10,120 Steps"),
            HistoryItem("Oct 20, 2023", "6h 30m Sleep", "1.9L Water", "5,432 Steps"),

        )

        binding.rvHistory.layoutManager = LinearLayoutManager(this)
        binding.rvHistory.adapter = HistoryAdapter(historyList)
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigation.selectedItemId = R.id.nav_history
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    val intent = Intent(this, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                    startActivity(intent)
                    overridePendingTransition(0, 0)
                    true
                }
                R.id.nav_history -> true
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
        binding.bottomNavigation.selectedItemId = R.id.nav_history
    }
}

data class HistoryItem(
    val date: String,
    val sleep: String,
    val water: String,
    val steps: String
)