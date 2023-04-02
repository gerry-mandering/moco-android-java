package com.example.mocoapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GetMapLocationActivity extends AppCompatActivity {

    private TextView locationTV;
    private Button submitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_map_location);

        locationTV = findViewById(R.id.idTVLocation);
        submitBtn = findViewById(R.id.idBtnSubmit);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                String location = locationTV.getText().toString();

                intent.putExtra("location", location);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}