package com.example.fdrforclubs;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class Club_Name extends AppCompatActivity {
    static String club_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.club_page);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("clubs");
        Query club_list = myRef.child(club_id);
        TextView club_name = (TextView)findViewById(R.id.club_name);
        TextView club_description = (TextView)findViewById(R.id.club_description);
        TextView club_participants = (TextView)findViewById(R.id.club_participants);
        club_name.setText(club_list.toString());
    }
}
