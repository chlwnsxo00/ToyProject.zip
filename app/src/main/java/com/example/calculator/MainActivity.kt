package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.room.Room
import com.example.calculator.model.History
import java.lang.NumberFormatException

private fun String.isNumber() : Boolean{
    return try{
        this.toBigInteger()
        true
    }catch (e : NumberFormatException){
        false
    }
}

class MainActivity : AppCompatActivity() {
    private val expressiontTextView : TextView by lazy{
        findViewById(R.id.expressionTextView)
    }
    private val resultTextView : TextView by lazy {
        findViewById(R.id.resultTextView)
    }
    private val historyLayout : View by lazy{
        findViewById<View>(R.id.historyLayout)
    }
    private val historyLinearLayout : LinearLayout by lazy{
        findViewById<LinearLayout>(R.id.historyLinearLayout)
    }
    private var isOperator = false
    private var hasOperator = false

    private lateinit var db:AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "historyDB"
        ).build()

    }
    fun buttonClicked(v:View){
        when(v.id){
            R.id.button0 ->numberButtonClickeed("0")
            R.id.button1 ->numberButtonClickeed("1")
            R.id.button2 ->numberButtonClickeed("2")
            R.id.button3 ->numberButtonClickeed("3")
            R.id.button4 ->numberButtonClickeed("4")
            R.id.button5 ->numberButtonClickeed("5")
            R.id.button6 ->numberButtonClickeed("6")
            R.id.button7 ->numberButtonClickeed("7")
            R.id.button8 ->numberButtonClickeed("8")
            R.id.button9 ->numberButtonClickeed("9")
            R.id.buttonAdd ->operatorButtonClicked("+")
            R.id.buttonSubtract ->operatorButtonClicked("-")
            R.id.buttonModulo ->operatorButtonClicked("%")
            R.id.buttonMutiple ->operatorButtonClicked("*")
            R.id.buttonDivision ->operatorButtonClicked("/")
        }
    }
    fun numberButtonClickeed(number : String){
        if(isOperator){
            expressiontTextView.append(" ")
        }
        isOperator = false

        val expressionText = expressiontTextView.text.split(" ")
        if(expressionText.isNotEmpty()&&expressionText.last().length>=15) {
            Toast.makeText(this,"15자리 이상의 수는 계산할 수 없습니다.",Toast.LENGTH_SHORT)
            return
        }else if(expressionText.isEmpty() && number=="0"){
            Toast.makeText(this,"0이 제일 앞에 올 수 없습니다.",Toast.LENGTH_SHORT)
            return
        }
        expressiontTextView.append(number)
        resultTextView.text = calculateExpression()
    }
    fun operatorButtonClicked(operator : String){
        if(expressiontTextView.text.isEmpty()){
            Toast.makeText(this,"연산자가 제일 앞에 올 수 없습니다.",Toast.LENGTH_SHORT)
            return
        }
        when{
            isOperator -> {
                val text = expressiontTextView.text.toString()
                expressiontTextView.text = text.dropLast(1) + operator
                return
            }
            hasOperator ->{
                Toast.makeText(this,"연산자는 한번만 사용할 수 있습니다.",Toast.LENGTH_SHORT)
                return
            }
            else -> {
                expressiontTextView.append(" $operator")
            }
        }
        val ssb = SpannableStringBuilder(expressiontTextView.text)
        ssb.setSpan(
            ForegroundColorSpan(getColor(R.color.greenButton)),
            expressiontTextView.text.length -1 ,
            expressiontTextView.text.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        expressiontTextView.text = ssb

        isOperator = true
        hasOperator = true
    }
    fun historyButtonClicked(v:View){
      historyLayout.isVisible=true
        historyLinearLayout.removeAllViews()

        Thread(Runnable {
            db.historyDao().getAll().reversed().forEach{
                val historyView = LayoutInflater.from(this).inflate(R.layout.history_row,null,false)
                historyView.findViewById<TextView>(R.id.expressionTextView).text = it.expression
                historyView.findViewById<TextView>(R.id.resultTextView).text = "= ${it.result}"

                historyLinearLayout.addView(historyView)
            }
        }).start()

    }

    fun closeButtonClicked(v:View){
      historyLayout.isVisible=false
    }
    fun historyClearButtonClicked(v:View){
      historyLinearLayout.removeAllViews()

        Thread(Runnable {
            db.historyDao().deleteAll()
        }).start()
    }
    fun resultButtonClicked(v:View){
        val expressionTexts = expressiontTextView.text.split(" ")
        if(expressiontTextView.text.isEmpty()||expressionTexts.size==1){
            return
        }
        if(expressionTexts.size!=3&&hasOperator){
            Toast.makeText(this, "아직 완성되지 않은 수식입니다.", Toast.LENGTH_SHORT)
            return
        }
        if(expressionTexts[0].isNumber().not()|| expressionTexts[2].isNumber().not()){
            Toast.makeText(this, "오류가 발생했습니다.", Toast.LENGTH_SHORT)
            return
        }
        val expresionText =expressiontTextView.text.toString()
        val resultText = calculateExpression()

        Thread(Runnable {
            db.historyDao().insertHistory(History(null,expresionText , resultText))
        }).start()

        resultTextView.text = ""
        expressiontTextView.text = resultText

        isOperator = false
        hasOperator = false
    }
    private fun calculateExpression() : String {
        val expressionTexts = expressiontTextView.text.split(" ")
        if(!hasOperator || expressionTexts.size!=3)
            return ""
        else if(expressionTexts[0].isNumber().not() || expressionTexts[2].isNumber().not())
            return ""
        val exp1 = expressionTexts[0].toBigInteger()
        val exp2 = expressionTexts[2].toBigInteger()
        val operator = expressionTexts[1]

        return when(operator){
            "+" ->  (exp1 + exp2).toString()
            "-" ->  (exp1 - exp2).toString()
            "*" ->  (exp1 * exp2).toString()
            "/" -> (exp1 / exp2).toString()
            "%" -> (exp1 % exp2).toString()
            else -> ""
        }
    }


    fun clearButtonClicked(v:View){
        expressiontTextView.text =""
        resultTextView.text=""
        isOperator=false
        hasOperator=false
    }
}