package com.example.anima

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.anima.databinding.ActivitySleepDetailBinding

class SleepDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySleepDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySleepDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            finish()
        }
    }
}