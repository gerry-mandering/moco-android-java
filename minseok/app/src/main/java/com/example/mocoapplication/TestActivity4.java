package com.example.mocoapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;
import java.io.InputStream;

public class TestActivity4 extends AppCompatActivity {

    private Button readBtn;
    private EditText rawEdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test4);

        readBtn = findViewById(R.id.idBtnRead);
        rawEdt = findViewById(R.id.idEdtRaw);

        readBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    InputStream inputStream = getResources().openRawResource(R.raw.raw_test);
                    byte[] txt = new byte[inputStream.available()];
                    inputStream.read(txt);
                    rawEdt.setText(txt.toString());
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}