package com.example.quizapp

import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    lateinit var pb: ProgressBar
    lateinit var bt: Button
    lateinit var tv: TextView
    lateinit var rg: RadioGroup
    lateinit var progressText: TextView

    val questions = listOf(
        "Which folder is responsible for creating a Vector Asset?" to
                listOf("drawable", "layout", "values", "All of the above"),
        "Which file is responsible for deciding the launcher activity?" to
                listOf("AndroidManifest.xml", "strings.xml", "layout.xml", "None of the above"),
        "Where do we create the themes for an activity?" to
                listOf("strings.xml", "values.xml", "theme.xml", "None of the above"),
        "Which of the following will provide an option to rate?" to
                listOf("RatingBar", "ProgressBar", "Rating", "None of the above")
    )

    val ans = listOf("drawable", "AndroidManifest.xml", "theme.xml", "RatingBar")
    var score = 0
    var currentQuesIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.quizToolbar)
        setSupportActionBar(toolbar)

        tv = findViewById(R.id.questionText)
        pb = findViewById(R.id.progressBar)
        bt = findViewById(R.id.nextButton)
        rg = findViewById(R.id.optionsGroup)
        progressText = findViewById(R.id.scoreText)

        loadQus()

        bt.setOnClickListener {
            val option = rg.checkedRadioButtonId
            if (option != -1) {
                val selected = findViewById<RadioButton>(option).text.toString()
                if (selected == ans[currentQuesIndex]) {
                    score++
                }
                currentQuesIndex++
                updateProgress()
                if (currentQuesIndex < questions.size) {
                    loadQus()
                } else {
                    tv.text = "Quiz Complete! Your score: $score/${questions.size}"
                    rg.removeAllViews()
                    bt.isEnabled = false
                }
            } else {
                Toast.makeText(this, "Please select an option!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun loadQus() {
        val (question, options) = questions[currentQuesIndex]
        tv.text = question
        rg.removeAllViews()
        options.forEach { option ->
            val rb = RadioButton(this)
            rb.text = option
            rb.setTextColor(ContextCompat.getColor(this, R.color.colorGreenDark))
            rg.addView(rb)
        }
    }

    fun updateProgress() {
        pb.progress = (currentQuesIndex + 1) * 100 / questions.size
        progressText.text = "Your progress: ${currentQuesIndex}/${questions.size}"
    }
}
