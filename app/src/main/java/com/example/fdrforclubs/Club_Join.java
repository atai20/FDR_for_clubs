package com.example.fdrforclubs;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class Club_Join extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.club_join_lay);
        FirebaseDatabase database = FirebaseDatabase.getInstance();


        DatabaseReference myRef = database.getReference();
        Query chess_list = myRef.orderByValue();
        TextView text1 = (TextView)findViewById(R.id.clubs_list);
        chess_list.addChildEventListener(new ChildEventListener() {
            String str = "";
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                str = "";
                returnVal(dataSnapshot);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                str = "";

                returnVal(snapshot);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                str = "";
                returnVal(snapshot);
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                str = "";
                returnVal(snapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                str = "";
                text1.setText("database error (object canceled)");
            }

            public void returnVal(DataSnapshot dataSnapshot) {

                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    str = str+":  "+dsp.child("clubs")+"\n";
                }
                text1.setText(str);

            }

        });
    }
}
