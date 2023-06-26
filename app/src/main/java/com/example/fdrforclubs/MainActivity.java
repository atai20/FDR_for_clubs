package com.example.fdrforclubs;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    public static class User {

        public String date_of_birth;
        public String full_name;
        public String nickname;

        public User(String dateOfBirth, String fullName) {
            // ...
        }

        public User(String dateOfBirth, String fullName, String nickname) {
            // ...
        }

    }
    public void onButtonShowPopupWindowClick(View view) {

        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.create_post, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button1 = (Button)findViewById(R.id.send_button);

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("clubs");
        EditText base_mess = (EditText) findViewById(R.id.editTextText);
        TextView text1 = (TextView) findViewById(R.id.textView2);


        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                myRef.child("chess").push().setValue(base_mess.getText().toString());
            }
        });
        Query chess_list = myRef.orderByValue().limitToFirst(3);
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
                text1.setText(error.toString());
            }

            public void returnVal(DataSnapshot dataSnapshot) {
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    str = str+"  "+dsp.getValue().toString()+"   \n";
                }
                text1.setText(str);

            }

        });


    }

}