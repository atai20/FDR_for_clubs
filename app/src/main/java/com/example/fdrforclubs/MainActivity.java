package com.example.fdrforclubs;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.UUID;
import com.squareup.picasso.Picasso;
public class MainActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 22;
    private Button btnSelect, btnUpload;



    // Override onActivityResult method

    // UploadImage method





    public static class Post {

        public String desc_text;

        public String nickname;
        public String image_path;
        public Post() {
            // Default constructor required for calls to DataSnapshot.getValue(User.class)
        }

        public Post(String desc_text, String nickname, String image_path) {
            this.desc_text = desc_text;
            this.nickname = nickname;
            this.image_path = image_path;
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
    static String user_club_name;
    static String user_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button2 = (Button)findViewById(R.id.button2);

        Button button_reg = (Button)findViewById(R.id.button_register);
        Button button_log = (Button)findViewById(R.id.button_login);

        Button button_cr_post = (Button)findViewById(R.id.butt_create_post);
        Button button_join = (Button)findViewById(R.id.join_butt);
        Button button_info = (Button)findViewById(R.id.how_look_like);
        Button button_conc = (Button)findViewById(R.id.button9);


        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        DatabaseReference usersRef;

        ImageView imageView = findViewById(R.id.imageView3);

        usersRef = database.getReference("Users");
        DatabaseReference user_return;


        DatabaseReference myRef2 = database.getReference("clubs");

        DatabaseReference usersRef2 = database.getReference("Users");

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();



        StorageReference mountainImagesRef = storageRef.child("images/7cbc0f07-3f4b-4249-9bf6-4cc029bc8f8e");

        if (currentUser!=null) {
            usersRef2.child(currentUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (!task.isSuccessful()) {
                        Log.e("firebaseno", "Error getting data", task.getException());

                    } else {
                        Log.e("firebaseno", task.getResult().child("status").getValue().toString(), task.getException());
                        String stat = task.getResult().child("status").getValue().toString();
                        if (!stat.equals(null)) {
                            user_club_name = task.getResult().child("club").getValue().toString();
                            user_status = task.getResult().child("status").getValue().toString();
                            TextView textView = new TextView(new ContextThemeWrapper(getApplicationContext(), R.style.CardView1), null, 0);


                            FirebaseStorage storage = FirebaseStorage.getInstance();
                            StorageReference storageRef = storage.getReference();


                            StorageReference mountainImagesRef = storageRef.child("images/mountains.jpg");


                            DatabaseReference myRef = database.getReference("clubs").child("chess").child("posts");
                            Query club_list = myRef.orderByValue();
                            StorageReference listRef = storage.getReference().child("images/");



                            // loading that data into rImage
                            // variable which is ImageView
                                myRef.addChildEventListener(new ChildEventListener() {
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

                                    View cardView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.cardview, null);
                                    TextView tv = (TextView)cardView.findViewById(R.id.card_textview);
                                    ImageView imageView2 = (ImageView) cardView.findViewById(R.id.imageView4);
                                        LinearLayout linearLayout = findViewById(R.id.layout1);
                                    StorageReference mountainImagesRef = storageRef.child("images").child(dataSnapshot.child("image").getValue().toString());
                                    Toast.makeText(MainActivity.this,
                                            dataSnapshot.child("image").getValue().toString(),
                                            Toast.LENGTH_SHORT).show();
                                    mountainImagesRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            // Got the download URL for 'users/me/profile.png'
                                            // Pass it to Picasso to download, show in ImageView and caching
                                            if (uri!=null){
                                            Picasso.get().load(uri.toString()).into(imageView2);
                                        }}
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception exception) {
                                            Toast.makeText(MainActivity.this,
                                                    "fail",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                        tv.setText(dataSnapshot.child("nickname").getValue().toString()+": "+dataSnapshot.child("desc_text").getValue().toString());
                                        linearLayout.addView(cardView);




                                }

                            });

                        }

                    }
                }
            });



        }

// Create a Cloud Storage reference from the app

// Create a reference to "mountains.jpg"


// Register observers to listen for when the download is done or if it fails



        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                FirebaseAuth.getInstance().signOut();
                Toast.makeText(MainActivity.this, "You have logged out",
                        Toast.LENGTH_SHORT).show();
            }
        });
        button_reg.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent main_page = new Intent (MainActivity.this, RegisterPage.class);
                startActivity(main_page);
            }
        });
        button_cr_post.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent main_page = new Intent (MainActivity.this, CreatePost.class);
                startActivity(main_page);
            }
        });
        button_conc.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent main_page = new Intent (MainActivity.this, First_script.class);
                startActivity(main_page);
            }
        });
        button_info.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                setContentView(R.layout.club_profile);

            }
        });
        button_join.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent main_page = new Intent (MainActivity.this, Club_Join.class);
                startActivity(main_page);
            }
        });
        button_log.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent main_page = new Intent (MainActivity.this, LoginPage.class);
                startActivity(main_page);
            }
        });



    }

}