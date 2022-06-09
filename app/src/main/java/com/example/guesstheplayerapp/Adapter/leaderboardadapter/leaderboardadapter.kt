package com.example.guesstheplayerapp.Adapter.leaderboardadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.guesstheplayerapp.databinding.ActivityLeaderboardBinding
import com.example.guesstheplayerapp.databinding.LblayoutBinding
import com.example.guesstheplayerapp.model.User

class Leaderboardadapter(val user : ArrayList<User>) : RecyclerView.Adapter<Leaderboardadapter.ViewHolder>() {


    class ViewHolder(val binding : LblayoutBinding) : RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Leaderboardadapter.ViewHolder {
        val binding = LblayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: Leaderboardadapter.ViewHolder, position: Int) {
        val current = user[position]
        holder.binding.username.text = current.name
        holder.binding.score.text = current.score.toString()
    }

    override fun getItemCount(): Int {
        return user.size
    }
}