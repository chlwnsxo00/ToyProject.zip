package com.example.lottorandompicker

import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible

class MainActivity : AppCompatActivity() {
    private val addButton : Button by lazy{
        findViewById<Button>(R.id.addButton)
    }
    private val resetButton : Button by lazy {
        findViewById<Button>(R.id.resetButton)
    }
    private val resultButton : Button by lazy {
        findViewById<Button>(R.id.resultButton)
    }
    private val numberPicker : NumberPicker by lazy{
        findViewById(R.id.numberPicker)
    }
    private val numberTextViewList : List<TextView> by lazy{
        listOf<TextView>(
            findViewById(R.id.Number1),
            findViewById(R.id.Number2),
            findViewById(R.id.Number3),
            findViewById(R.id.Number4),
            findViewById(R.id.Number5),
            findViewById(R.id.Number6)

        )
    }
    private var didRun = false
    private val pickNumberSet = hashSetOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        numberPicker.minValue=1
        numberPicker.maxValue=45

        initResultButton()
        initAddButton()
        initResetButton()
    }
    private fun initResetButton(){
        resetButton.setOnClickListener{
            pickNumberSet.clear()
            numberTextViewList.forEach{
                it.isVisible=false
            }
            didRun = false
        }
    }
    private fun initAddButton(){
        addButton.setOnClickListener{
            if(didRun) {
                Toast.makeText(this, "초기화 후에 다시 시도해주세요!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(pickNumberSet.size>=5){
                Toast.makeText(this, "숫자는 5개까지 추가할 수 있습니다!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(pickNumberSet.contains(numberPicker.value)){
                    Toast.makeText(this, "이미 추가한 숫자입니다!", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
            }
            val textView : TextView = numberTextViewList[pickNumberSet.size]
            textView.isVisible=true
            textView.text=numberPicker.value.toString()
            setNumberBackgound(numberPicker.value,textView)
            pickNumberSet.add(numberPicker.value)
        }
    }
    private fun setNumberBackgound(number : Int , textView:TextView){
        when(number){
            in 1..10 -> textView.background = ContextCompat.getDrawable(this,R.drawable.ciircle_yellow)
            in 11..20 -> textView.background = ContextCompat.getDrawable(this,R.drawable.circle_blue)
            in 21..30 -> textView.background = ContextCompat.getDrawable(this,R.drawable.circle_red)
            in 31..40 -> textView.background = ContextCompat.getDrawable(this,R.drawable.circle_gray)
            else -> textView.background = ContextCompat.getDrawable(this,R.drawable.circle_green)
        }
    }

    private fun initResultButton(){
        resultButton.setOnClickListener{
            val list = getRandomNumber()
            list.forEachIndexed { index, i ->
                val textView = numberTextViewList[index]
                textView.isVisible = true
                textView.text = i.toString()
                setNumberBackgound(i,textView)
            }
            didRun = true

        }
    }
    private fun getRandomNumber() : List<Int>{
        val numberList = mutableListOf<Int>()
            .apply {
                for(i in 1..45){
                    if(pickNumberSet.contains(i))
                        continue
                    this.add(i)
                }
            }
        numberList.shuffle()
        val newList = pickNumberSet.toList() +numberList.subList(0,6-pickNumberSet.size)
        return newList.sorted()
    }

}