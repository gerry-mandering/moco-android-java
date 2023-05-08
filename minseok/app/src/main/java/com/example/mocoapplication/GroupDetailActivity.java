package com.example.mocoapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class GroupDetailActivity extends AppCompatActivity {

    private final static String TAG = "GroupDetailActivity";

    TextView titleTV, locationTV, dateTV, timeTV, headCntTV, contentTV;
    TextView[] participantTV = new TextView[6];
    Button joinBtn;
    ProgressBar loadingPB;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_detail);

        //init
        titleTV = findViewById(R.id.idTVTitle);
        locationTV = findViewById(R.id.idTVLocation);
        dateTV = findViewById(R.id.idTVDate);
        timeTV = findViewById(R.id.idTVTime);
        headCntTV = findViewById(R.id.idTVHeadCnt);
        contentTV = findViewById(R.id.idTVContent);
        for (int i = 0; i < 6; i++) {
            String idTVParticipant = "idTVParticipant" + (i + 1);
            int resId = getResources().getIdentifier(idTVParticipant, "id", getPackageName());
            participantTV[i] = findViewById(resId);
        }
        joinBtn = findViewById(R.id.idBtnJoin);
        loadingPB = findViewById(R.id.idPBLoading);

        Intent mainIntent = getIntent();
        GroupParcel groupParcel = mainIntent.getParcelableExtra("group");

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Groups");
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();

        titleTV.setText(groupParcel.getTitle());
        locationTV.setText(groupParcel.getLocation());
        dateTV.setText(groupParcel.getDate());
        timeTV.setText(groupParcel.getTime());
        contentTV.setText(groupParcel.getContent());

        int currentHeadCnt = groupParcel.getCurrentHeadCnt();
        headCntTV.setText(currentHeadCnt + " / " + groupParcel.getTotalHeadCnt());

        String[] participantDisplayNames = new String[currentHeadCnt];

        Log.d(TAG, "onCreate: " + groupParcel.getGroupID());
        databaseReference.child(groupParcel.getGroupID()).child("Participants").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int i = 0;

                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    participantDisplayNames[i] = childSnapshot.getKey(); // Get the key of the child node
                    Log.d(TAG, "onDataChange: participantDisplayNames[" + i + "] :" + participantDisplayNames[i]);
                    participantTV[i].setText(participantDisplayNames[i]);
                    if (i != 0)
                        participantTV[i].setVisibility(View.VISIBLE);
                    i++;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingPB.setVisibility(View.VISIBLE);
                String userDisplayName = firebaseUser.getDisplayName();

                boolean overlapedFlag = false;

                for (int i = 0; i < currentHeadCnt; i++) {
                    if (Objects.equals(userDisplayName, participantDisplayNames[i])) {
                        overlapedFlag = true;
                        break;
                    }
                }

                if (overlapedFlag) {
                    loadingPB.setVisibility(View.GONE);
                    Toast.makeText(GroupDetailActivity.this, "중복 참가입니다", Toast.LENGTH_SHORT).show();
                } else if (currentHeadCnt >= groupParcel.getTotalHeadCnt()) {
                    loadingPB.setVisibility(View.GONE);
                    Toast.makeText(GroupDetailActivity.this, "인원을 초과하였습니다", Toast.LENGTH_SHORT).show();
                } else {
                    databaseReference.child(groupParcel.getGroupID()).child("currentHeadCnt").setValue(currentHeadCnt + 1);
                    databaseReference.child(groupParcel.getGroupID()).child("Participants").child(userDisplayName).setValue(true);

                    loadingPB.setVisibility(View.GONE);
                    Toast.makeText(GroupDetailActivity.this, "그룹에 참가하였습니다", Toast.LENGTH_SHORT).show();
                }

                Intent intent = new Intent(GroupDetailActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}