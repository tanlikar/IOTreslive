package com.example.asdasd;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity{
    Button btnLogout;

    TextView pirstatus, pulsebpm, camerastatus;
    DatabaseReference dref;
    Switch mSwitch;
    ArrayList<DataStrucString> status = new ArrayList<>();
    ArrayList<DataStrucBPM> bpm = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        pirstatus = findViewById(R.id.textView6);
        pulsebpm = findViewById(R.id.textView7);
        camerastatus = findViewById(R.id.textView8);
        dref = FirebaseDatabase.getInstance().getReference();
        mSwitch = findViewById(R.id.OnSwitch);

        dref.child("PIR_Sensor").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                if(dataSnapshot.exists()) {
                    status.add(dataSnapshot.getValue(DataStrucString.class));
                    Log.d("test", "onDataChange: " + status);

                    if (mSwitch.isChecked()) {
                        pirstatus.setText(status.get(status.size()-1).getStatus());

                        Log.d("int", "onCheckedChanged: " + status.get(status.size()-1).getStatus());
                    } else {
                        pirstatus.setText("Loading...");
                    }

                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

               dref.child("Pulse_Sensor").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(dataSnapshot.exists()){
                    bpm.add(dataSnapshot.getValue(DataStrucBPM.class));
                    Log.d("test", "onDataChange: " + bpm);
                    if (mSwitch.isChecked()) {
                        pulsebpm.setText(bpm.get(bpm.size()-1).getBPM().toString());
                        Log.d("int", "onCheckedChanged: " + bpm.get(bpm.size()-1).getBPM().toString());
                    } else {
                        pulsebpm.setText("Loading...");
                    }


                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (mSwitch.isChecked()) {
                    pulsebpm.setText(bpm.get(bpm.size()-1).getBPM().toString());
                    pirstatus.setText(status.get(status.size()-1).getStatus());
                    Log.d("int", "onCheckedChanged: " + bpm.get(bpm.size()-1).getBPM().toString());
                } else {
                    pulsebpm.setText("Loading...");
                    pirstatus.setText("Loading...");
                }
            }
        });

    }
}
