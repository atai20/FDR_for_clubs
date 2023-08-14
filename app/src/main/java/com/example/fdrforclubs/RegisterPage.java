package com.example.fdrforclubs;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterPage extends AppCompatActivity {
    private FirebaseAuth mAuth;
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.

    }
    public static class User {

        public String email;
        public String home_lang;
        public String club;
        public String description;




        public User() {
            // Default constructor required for calls to DataSnapshot.getValue(User.class)
        }

        public User(String email) {
            this.email = email;

        }


    }
    protected void create_account(String email, String password){
        mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseDatabase database = FirebaseDatabase.getInstance();

                            DatabaseReference myRef = database.getReference("clubs");
                            DatabaseReference myRefUsers = database.getReference();

                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            FirebaseAuth mAuth;
                            mAuth = FirebaseAuth.getInstance();
                            User new_pos = new User(email);
                            myRefUsers.child("Users").push().setValue(new_pos);
                            detailing_register();
                                //put new storage uploading
                                // Defining Implicit Intent to mobile gallery

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterPage.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void detailing_register(){
        setContentView(R.layout.register);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        Button button = (Button) findViewById(R.id.button);
        EditText email = (EditText) findViewById(R.id.editTextText3);
        EditText password = (EditText) findViewById(R.id.editTextTextPassword);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                create_account(email.getText().toString(), password.getText().toString());
            }
        });
        // Initialize Firebase Auth


    }
}
