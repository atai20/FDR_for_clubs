package com.example.fdrforclubs;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button1 = (Button)findViewById(R.id.send_button);
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");
        DatabaseReference myRef2 = database.getReference("message2");
        myRef2.setValue("sdf");
        EditText base_mess = (EditText) findViewById(R.id.editTextText);
        TextView text1 = (TextView) findViewById(R.id.textView2);


        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Map<String, String> users = new HashMap<>();
                users.put("alanisawesome", "sdfsdf");
                myRef.setValue(users);
            }
        });
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                text1.setText("Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                text1.setText("Failed to read value.");
            }
        });
    }

}