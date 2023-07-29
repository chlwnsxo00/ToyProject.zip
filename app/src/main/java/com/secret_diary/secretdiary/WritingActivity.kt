package com.secret_diary.secretdiary

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.EditText
import androidx.core.content.edit
import androidx.core.widget.addTextChangedListener
import com.example.secretdiary.R

class WritingActivity : AppCompatActivity() {
    private val DiaryText : EditText by lazy {
        findViewById(R.id.DiaryText)
    }
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_writing)

        val diaryPreference = getSharedPreferences("diary" , Context.MODE_PRIVATE)
        DiaryText.setText(diaryPreference.getString("detail",""))

        val runnable = Runnable{
            getSharedPreferences("diary" , Context.MODE_PRIVATE).edit{
                putString("detail",DiaryText.text.toString())
            }
        }

        DiaryText.addTextChangedListener{
            handler.removeCallbacks(runnable)
            handler.postDelayed(runnable,500)
        }
    }
}