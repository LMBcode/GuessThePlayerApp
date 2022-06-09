package com.example.guesstheplayerapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.guesstheplayerapp.Adapter.leaderboardadapter.Leaderboardadapter
import com.example.guesstheplayerapp.databinding.ActivityLeaderboardBinding
import com.example.guesstheplayerapp.model.User
import com.google.firebase.firestore.*

class Leaderboard : AppCompatActivity() {
    private lateinit var _binding : ActivityLeaderboardBinding
    private val binding get() = _binding
    private val db = FirebaseFirestore.getInstance()
    private lateinit var adapter : Leaderboardadapter
    private lateinit var layoutManager : LinearLayoutManager
    private lateinit var userList : ArrayList<User>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLeaderboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val username = intent.getStringExtra(QuestionSource.Username)
        val answer = intent.getIntExtra(QuestionSource.CorrectAnswer,0)
        userList = arrayListOf()
        adapter = Leaderboardadapter(userList)
        layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = layoutManager

        if (username != null) {
            db.collection("users").document(username).set(hashMapOf("name" to username , "score" to answer))
        }
        database()
        binding.finish.setOnClickListener {
            val Intent = Intent(this,MainActivity::class.java)
            startActivity(Intent)

        }
    }

    private fun database(){
        db.collection("users").orderBy("score", Query.Direction.DESCENDING).addSnapshotListener { value, error ->
            if (error != null) {
                Log.e("Error", error.message.toString())
            }

            for (dc: DocumentChange in value?.documentChanges!!) {
                if (dc.type == DocumentChange.Type.ADDED) {
                    userList.add(dc.document.toObject(User::class.java))
                }
            }
            adapter.notifyDataSetChanged()
        }
    }
}