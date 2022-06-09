package com.example.guesstheplayerapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.guesstheplayerapp.databinding.ActivityAuthBinding

class AuthActivity : AppCompatActivity() {
    private lateinit var _binding : ActivityAuthBinding
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnStart.setOnClickListener {
            if (binding.username.text.isEmpty()) {
                Toast.makeText(this, "Please enter username", Toast.LENGTH_SHORT).show()
            } else {
                val Intent = Intent(this, QuestionsActivity::class.java)
                Intent.putExtra(QuestionSource.Username, binding.username.text.toString())
                startActivity(Intent)
            }
        }
        }
    }