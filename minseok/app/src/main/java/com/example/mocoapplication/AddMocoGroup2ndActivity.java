package com.example.mocoapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddMocoGroup2ndActivity extends AppCompatActivity {

    private TextView headCntTV, dateTV, timeTV, selectLocTV, locationTV;
    private SeekBar headCntSB;
    private LinearLayout selectLocLL;
    private Button postBtn;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private static int count;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
    private SimpleDateFormat timeFormat = new SimpleDateFormat("H:mm a");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_moco_group2nd);

        headCntTV = findViewById(R.id.idTVHeadCnt);
        dateTV = findViewById(R.id.idTVDate);
        timeTV = findViewById(R.id.idTVTime);
        selectLocTV = findViewById(R.id.idTVSelectLoc);
        locationTV = findViewById(R.id.idTVLocation);
        headCntSB = findViewById(R.id.idSBHeadCnt);
        selectLocLL = findViewById(R.id.idLLSelectLoc);
        postBtn = findViewById(R.id.idBtnPost);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Groups");

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int date = calendar.get(Calendar.DATE);
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                dateTV.setText(year + "/" + monthOfYear + "/" + dayOfMonth);
            }
        }, year, month, date);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                boolean isPM = (hourOfDay >= 12);
                timeTV.setText(String.format("%02d : %02d %s", (hourOfDay == 12 || hourOfDay == 0) ? 12 : hourOfDay % 12, minute, isPM ? "PM" : "AM"));
            }
        }, hour, minute, false);

        headCntSB.setMax(40);
        headCntSB.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int headCnt;

                if (progress % 10 == 0) {
                    headCnt = (progress + 20) / 10;
                    headCntTV.setText(String.valueOf(headCnt) + " / 6");
                } else {
                    headCntSB.setProgress((progress + 5) / 10 * 10);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        dateTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dateTV.isClickable()) {
                    datePickerDialog.show();
                }
            }
        });

        timeTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (timeTV.isClickable()) {
                    timePickerDialog.show();
                }
            }
        });

        postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        Calendar calendar = Calendar.getInstance();
        String date = dateFormat.format(calendar.getTime());
        String time = timeFormat.format(calendar.getTime());

        dateTV.setText(date);
        timeTV.setText(time);
    }
}