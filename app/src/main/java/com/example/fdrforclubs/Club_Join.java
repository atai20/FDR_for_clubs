package com.example.fdrforclubs;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
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
        LinearLayout linearlyout_find_clubs = (LinearLayout) findViewById(R.id.linearlayout_find_clubs);
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

            }

            public void returnVal(DataSnapshot dataSnapshot) {

                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    str = str+":  "+dsp.getKey()+"\n";
                    View cardView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.cardview, null);



                    TextView tv = (TextView)cardView.findViewById(R.id.card_textview);
                    Button check_club = (Button)cardView.findViewById(R.id.go_to_page);
                    check_club.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            Club_Name dred = new Club_Name();
                            dred.club_id = "sdfsdf";
                            Intent club = new Intent (Club_Join.this, Club_Name.class);
                            startActivity(club);
                        }
                    });
                    TextView textView = new TextView(new ContextThemeWrapper(getApplicationContext(), R.style.CardView1), null, 0);
                    tv.setText(str);
                    linearlyout_find_clubs.addView(cardView);
                }


            }

        });
    }
}
