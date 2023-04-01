package com.example.mocoapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddMocoGroupFstActivity extends AppCompatActivity {

    private TextInputEditText titleEdt, contentEdt;
    private Button nextBtn;
    private ProgressBar loadingPB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_moco_group);

        titleEdt = findViewById(R.id.idEdtTitle);
        contentEdt = findViewById(R.id.idEdtContent);
        nextBtn = findViewById(R.id.idBtnNext);
        loadingPB = findViewById(R.id.idPBLoading);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingPB.setVisibility(View.VISIBLE);
                String title = titleEdt.getText().toString();
                String content = contentEdt.getText().toString();

                if (TextUtils.isEmpty(title) && TextUtils.isEmpty(content)) {
                    Toast.makeText(AddMocoGroupFstActivity.this, "모든 항목을 입력해주십시오", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Intent intent = new Intent(AddMocoGroupFstActivity.this, AddMocoGroup2ndActivity.class);
                    intent.putExtra("title", title);
                    intent.putExtra("content", content);
                    startActivity(intent);
                }
            }
        });
    }
}