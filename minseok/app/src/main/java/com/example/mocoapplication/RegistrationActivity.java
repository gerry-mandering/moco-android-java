package com.example.mocoapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class RegistrationActivity extends AppCompatActivity {

    private final static String TAG = "RegistrationActivity";
    private TextInputEditText emailEdt, pwdEdt, cnfPwdEdt, userNameEdt;
    private Button registerBtn;
    private TextView loginTV;
    private ProgressBar loadingPB;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        emailEdt = findViewById(R.id.idEdtEmail);
        pwdEdt = findViewById(R.id.idEdtPassword);
        cnfPwdEdt = findViewById(R.id.idEdtCnfPassword);
        userNameEdt = findViewById(R.id.idEdtUserName);
        registerBtn= findViewById(R.id.idBtnRegister);
        loginTV = findViewById(R.id.idTVLogin);
        loadingPB = findViewById(R.id.idPBLoading);

        mAuth = FirebaseAuth.getInstance();

        loginTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingPB.setVisibility(View.VISIBLE);

                String email = emailEdt.getText().toString();
                String pwd = pwdEdt.getText().toString();
                String cnfPwd = cnfPwdEdt.getText().toString();
                String userName = userNameEdt.getText().toString();

                if (!pwd.equals(cnfPwd)) {
                    loadingPB.setVisibility(View.GONE);
                    Toast.makeText(RegistrationActivity.this, "비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(email) && TextUtils.isEmpty(pwd) && TextUtils.isEmpty(cnfPwd) && TextUtils.isEmpty(userName)) {
                    loadingPB.setVisibility(View.GONE);
                    Toast.makeText(RegistrationActivity.this, "모든 항목을 입력해주십시오", Toast.LENGTH_SHORT).show();
                } else {
                    mAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();

                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(userName)
                                        .setPhotoUri(Uri.parse("https://example.com/jane-q-user/profile.jpg"))
                                        .build();

                                user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Log.d(TAG, "User profile updated.");
                                        }
                                    }
                                });

                                loadingPB.setVisibility(View.GONE);
                                Toast.makeText(RegistrationActivity.this, "회원가입 되었습니다", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                loadingPB.setVisibility(View.GONE);
                                Toast.makeText(RegistrationActivity.this, "회원가입에 실패했습니다", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}