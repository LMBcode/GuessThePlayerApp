package com.example.guesstheplayerapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.guesstheplayerapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var _binding : ActivityMainBinding
    private val binding get() = _binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.start.setOnClickListener {
            val Intent = Intent(this,AuthActivity::class.java)
            startActivity(Intent)
        }
        binding.leaderboard.setOnClickListener {
            val Intent = Intent(this,Leaderboard::class.java)
            startActivity(Intent)
        }
    }
}