package com.example.guesstheplayerapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.text.InputFilter
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.guesstheplayerapp.databinding.ActivityQuestionsBinding
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds

class QuestionsActivity : AppCompatActivity(),View.OnClickListener {
    private lateinit var _binding: ActivityQuestionsBinding
    private val binding get() = _binding
    private var mQuestionList: List<clubs>? = null
    private var mCurrentQuestion = 1
    private var mUsername : String? = null
    private var mCorrectAnswer = 0
    private var TIME_IN_MILLIS = 16000
    val numberofseconds = TIME_IN_MILLIS / 1000
    private var factor = 100 / numberofseconds
    private lateinit var timer : CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityQuestionsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.guess.filters += InputFilter.AllCaps()
        mQuestionList = QuestionSource.guessPlayer()
        setQuestion()
        MobileAds.initialize(this) {}
        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
        mUsername = intent.getStringExtra(QuestionSource.Username)
        binding.confirm.setOnClickListener(this)
    }

    private fun clock(){
        val question = mQuestionList?.get(mCurrentQuestion - 1)
        timer = object : CountDownTimer(TIME_IN_MILLIS.toLong(),1000){
            @SuppressLint("SetTextI18n")
            override fun onTick(p0: Long) {
                val secondsremaining = p0/1000
                val percentage = (numberofseconds - secondsremaining)
                binding.timer.text = ((p0 / 1000).toString())
                binding.guess.visibility = View.VISIBLE
                binding.progressBarr.setProgress(percentage.toInt())
                binding.progressBarr.max = numberofseconds
            }

            override fun onFinish() {
                timer.cancel()
                binding.name.text = question!!.answer
                binding.player.setImageResource(question.image)
                binding.guess.visibility = View.GONE
            }

        }
        timer.start()
    }


    private fun setQuestion() {
        clock()
        binding.animation.visibility = View.GONE
        binding.wronganimation.visibility = View.GONE
        binding.player.setImageResource(0)
        binding.name.text = ""
        val question = mQuestionList?.get(mCurrentQuestion - 1)
        if (mCurrentQuestion == mQuestionList?.size) {
            binding.confirm.text = "FINISH"
        } else {
            binding.confirm.text = "SUMBIT"
        }
        question?.club1?.let { binding.club1.setImageResource(it) }
        question?.club2?.let { binding.club2.setImageResource(it) }
        question?.club3?.let { binding.club3.setImageResource(it) }
        question?.club4?.let { binding.club4.setImageResource(it) }
        question?.club5?.let { binding.club5.setImageResource(it) }
        question?.club6?.let { binding.club6.setImageResource(it) }

    }

    override fun onClick(v: View?) {
        val question = mQuestionList?.get(mCurrentQuestion - 1)
        when (v?.id) {
            R.id.confirm -> {
                timer.cancel()
                if (binding.guess.text.toString() == "") {
                    mCurrentQuestion++
                    when {
                        mCurrentQuestion <= mQuestionList!!.size -> {
                            setQuestion()
                        }
                        else -> {
                            val Intent = Intent(this, Leaderboard::class.java)
                            Intent.putExtra(QuestionSource.Username,mUsername)
                            Intent.putExtra(QuestionSource.CorrectAnswer,mCorrectAnswer)
                            startActivity(Intent)
                        }
                    }
                }else{
                    if(binding.guess.text.toString() != question!!.answer){
                        binding.player.setImageResource(question.image)
                        binding.wronganimation.playAnimation()
                        binding.wronganimation.visibility = View.VISIBLE
                        binding.name.text = question.answer
                    }
                    else{
                        binding.player.setImageResource(question.image)
                        binding.animation.playAnimation()
                        binding.animation.visibility = View.VISIBLE
                        mCorrectAnswer++
                    }
                    if (mCurrentQuestion == mQuestionList!!.size) {
                        binding.confirm.text = "FINISH"
                    } else {
                        binding.confirm.text = "NEXT QUESTION"
                    }
                    binding.guess.setText("")
                }
            }
        }
    }


}