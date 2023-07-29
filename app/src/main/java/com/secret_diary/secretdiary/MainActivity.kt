package com.secret_diary.secretdiary

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.NumberPicker
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.edit
import com.example.secretdiary.R

class MainActivity : AppCompatActivity() {
    private val numberPicker1 : NumberPicker by lazy {
        findViewById<NumberPicker?>(R.id.NumberPicker1)
            .apply {
                minValue =0
                maxValue =9
            }
    }
    private val numberPicker2 : NumberPicker by lazy{
        findViewById<NumberPicker?>(R.id.NumberPicker2)
            .apply {
                minValue =0
                maxValue =9
            }
    }
    private val numberPicker3 : NumberPicker by lazy{
        findViewById<NumberPicker?>(R.id.NumberPicker3)
            .apply {
                minValue =0
                maxValue =9
            }
    }
    private val checkPasswordButton : AppCompatButton by lazy{
        findViewById(R.id.CheckPasswordButton)
    }
    private val changePasswordButton : AppCompatButton by lazy {
        findViewById(R.id.ChangePasswordButton)
    }
    private var changeMode =false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkPasswordButton.setOnClickListener{
            if(changeMode){
                ErrorAlertDialog("비밀번호 변경 중입니다!")
                return@setOnClickListener
            }
            val setPassword = getSharedPreferences("password", Context.MODE_PRIVATE)
            val PasswordFromUser = "${numberPicker1.value}${numberPicker2.value}${numberPicker3.value}"
            if(setPassword.getString("password","000").equals(PasswordFromUser))
            {
                startActivity(Intent(this, WritingActivity::class.java))
            }
            else{
                ErrorAlertDialog("비밀번호가 잘못되었습니다!!")
            }
        }
        changePasswordButton.setOnClickListener{
            if(changeMode) {
                val setPassword = getSharedPreferences("password", Context.MODE_PRIVATE)
                setPassword.edit{
                    val newPassword = "${numberPicker1.value}${numberPicker2.value}${numberPicker3.value}"
                    putString("password",newPassword)
                }
                changeMode=false
                changePasswordButton.setBackgroundColor(Color.GRAY)
            }
            else{
                val setPassword = getSharedPreferences("password", Context.MODE_PRIVATE)
                val PasswordFromUser = "${numberPicker1.value}${numberPicker2.value}${numberPicker3.value}"
                if (setPassword.getString("password", "000").equals(PasswordFromUser)) {
                    changeMode=true
                    changePasswordButton.setBackgroundColor(Color.RED)
                } else {
                    ErrorAlertDialog("비밀번호가 잘못되었습니다!!")
                }
            }
        }
    }

    private fun ErrorAlertDialog(message : String){
        AlertDialog.Builder(this)
            .setTitle("실패!")
            .setMessage(message)
            .setPositiveButton("확인"){ _, _ -> }
            .create()
            .show()
    }
}