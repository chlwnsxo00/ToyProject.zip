package com.example.reservation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CalendarView cal = (CalendarView) findViewById(R.id.calenderView);
        cal.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                EditText date = (EditText) findViewById(R.id.textDate);
                date.setText(year+"/"+(month+1)+"/"+dayOfMonth);
            }
        });

        TimePicker time = (TimePicker) findViewById(R.id.timePicker);
        time.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                EditText txtTime = (EditText) findViewById(R.id.textTime);
                txtTime.setText(hourOfDay+":"+minute);
            }
        });
    }
    public void reservation(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        EditText date = (EditText) findViewById(R.id.textDate);
        String str = date.getText().toString();
        String[] dateArr = new String[3];
        dateArr = str.split("/");
        EditText time = (EditText) findViewById(R.id.textTime);
        builder.setTitle("Reservation").setMessage(dateArr[0]+"년 "+dateArr[1]+"월 "+dateArr[2]+"일 "+time.getText()+"분에 예약하시겠습니까?");
        String[] finalDateArr = dateArr;
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id)
            {
                Toast.makeText(getApplicationContext(), finalDateArr[0]+"년 "+ finalDateArr[1]+"월 "+ finalDateArr[2]+"일 "+time.getText()+"분에 예약되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id)
            {
                Toast.makeText(getApplicationContext(), "예약이 취소되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.reservemenu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId()==R.id.clear){
            final Calendar c = Calendar.getInstance();
            CalendarView cal = (CalendarView) findViewById(R.id.calenderView);
            TimePicker time = (TimePicker) findViewById(R.id.timePicker);
            cal.setDate(c.getTimeInMillis());
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int min = c.get(Calendar.MINUTE);
            time.setHour(hour);
            time.setMinute(min);
        }
        return true;
    }

}