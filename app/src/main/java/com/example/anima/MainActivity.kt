package com.example.anima

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        
        auth = FirebaseAuth.getInstance()
        
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Get the name passed from LoginActivity
        val loggedInUserName = intent.getStringExtra("USER_NAME") ?: auth.currentUser?.displayName ?: "User"

        // Mock data representing backend response, using the dynamic name
        val wellnessData = WellnessData(
            userName = loggedInUserName,
            date = "Monday, Oct 23",
            sleepDuration = "8h 15m",
            sleepQuality = "Excellent",
            waterIntake = 2.1f,
            waterGoal = 3.0f
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
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                logout()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun logout() {
        // Firebase sign out
        auth.signOut()

        // Google sign out
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        val googleSignInClient = GoogleSignIn.getClient(this, gso)
        googleSignInClient.signOut().addOnCompleteListener {
            // Redirect to Login
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}
