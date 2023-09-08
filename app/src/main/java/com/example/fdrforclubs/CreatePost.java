package com.example.fdrforclubs;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class CreatePost extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 22;
    private Button btnSelect, btnUpload;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();



    // view for image view
    private ImageView imageView;


    // Create a reference to 'images/mountains.jpg'
    StorageReference mountainImagesRef = storageRef.child("images/mountains.jpg");

// While the file names are the same, the references point to different files
        //mountainsRef.getName().equals(mountainImagesRef.getName());
    // true
        //mountainsRef.getPath().equals(mountainImagesRef.getPath());

    Uri file = Uri.fromFile(new File("path/to/images/rivers.jpg"));
    StorageReference riversRef = storageRef.child("images/"+file.getLastPathSegment());
    UploadTask uploadTask = riversRef.putFile(file);
    // Uri indicates, where the image will be picked from
    private Uri filePath;
    public static class Users {

        public String name;

        public String email;
        public String club;
        public String status;


        public Users() {
            // Default constructor required for calls to DataSnapshot.getValue(User.class)
        }

        public Users(String name, String email, String club, String status) {
            this.name = name;
            this.email = email;
            this.club = club;
            this.status = status;
        }


    }
    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data)
    {

        super.onActivityResult(requestCode,
                resultCode,
                data);

        // checking request code and result code
        // if request code is PICK_IMAGE_REQUEST and
        // resultCode is RESULT_OK
        // then set image in the image view
        if (requestCode == PICK_IMAGE_REQUEST
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {

            // Get the Uri of data
            filePath = data.getData();
            try {

                // Setting image on image view using Bitmap
                Bitmap bitmap = MediaStore
                        .Images
                        .Media
                        .getBitmap(
                                getContentResolver(),
                                filePath);
                imageView.setImageBitmap(bitmap);
            }

            catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }
    }
    private void UploadImage()
    {
        if (filePath != null) {

            // Code for showing progressDialog while uploading
            ProgressDialog progressDialog
                    = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            // Defining the child of storageReference
            StorageReference ref
                    = storageRef
                    .child(
                            "images/"
                                    + UUID.randomUUID().toString());

            // adding listeners on upload
            // or failure of image


            ref.putFile(filePath)
                    .addOnSuccessListener(
                            new OnSuccessListener<UploadTask.TaskSnapshot>() {

                                @Override
                                public void onSuccess(
                                        UploadTask.TaskSnapshot taskSnapshot)
                                {

                                    // Image uploaded successfully
                                    // Dismiss dialog
                                    progressDialog.dismiss();
                                    Toast.makeText(CreatePost.this,
                                                    "Image Uploaded!!",
                                                    Toast.LENGTH_SHORT).show();
                                }
                            })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {

                            // Error, Image not uploaded
                            progressDialog.dismiss();
                            Toast
                                    .makeText(CreatePost.this,
                                            "Failed " + e.getMessage(),
                                            Toast.LENGTH_SHORT)
                                    .show();
                        }
                    })
                    .addOnProgressListener(
                            new OnProgressListener<UploadTask.TaskSnapshot>() {

                                // Progress Listener for loading
                                // percentage on the dialog box
                                @Override
                                public void onProgress(
                                        UploadTask.TaskSnapshot taskSnapshot)
                                {
                                    double progress
                                            = (100.0
                                            * taskSnapshot.getBytesTransferred()
                                            / taskSnapshot.getTotalByteCount());
                                    progressDialog.setMessage(
                                            "Uploaded "
                                                    + (int)progress + "%");
                                }
                            });
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_post);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRefMain = database.getReference("clubs");
        imageView = findViewById(R.id.imageView2);
        Button button1 = (Button)findViewById(R.id.button3);
        Button button_file = (Button)findViewById(R.id.button5);
        Button button_upload = (Button)findViewById(R.id.button6);

        EditText post_text = (EditText)findViewById(R.id.post_text);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
                Toast.makeText(CreatePost.this, "Uploading succeeded.",
                        Toast.LENGTH_SHORT).show();
            }
        });
        button_file.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                // Defining Implicit Intent to mobile gallery
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(
                        Intent.createChooser(
                                intent,
                                "Select Image from here..."), PICK_IMAGE_REQUEST);

            }
        });
        button_upload.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                UploadImage();

            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                FirebaseUser currentUser = mAuth.getCurrentUser();
                if(currentUser!=null){
                    MainActivity.Post new_pos = new MainActivity.Post(post_text.getText().toString(), currentUser.getEmail());

                    DatabaseReference myRef = database.getReference("clubs");
                    Toast.makeText(CreatePost.this, currentUser.getUid(),
                            Toast.LENGTH_SHORT).show();
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference usersRef = database.getReference("Users");
                    DatabaseReference user_return;
                    MainActivity mainActivity = new MainActivity();
                    Toast.makeText(CreatePost.this, mainActivity.user_club_name,
                            Toast.LENGTH_SHORT).show();
                    if(!MainActivity.user_status.equals(null)){
                         new_pos = new MainActivity.Post(post_text.getText().toString(), currentUser.getEmail());
                         myRef.child(MainActivity.user_club_name).child("posts").push().setValue(new_pos);
                         Toast.makeText(CreatePost.this, mainActivity.user_club_name,
                                Toast.LENGTH_SHORT).show();
                    }



                    //put new storage uploading
                    // Defining Implicit Intent to mobile gallery
                }else{
                    Toast.makeText(CreatePost.this, "You need to sign in for posting in clubs",
                            Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

    public void returnVal(DataSnapshot dataSnapshot) {

        for (DataSnapshot dsp : dataSnapshot.getChildren()) {
            if(dsp.getValue()!=null){
                Toast.makeText(CreatePost.this, dsp.child("club").getValue().toString(),
                        Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(CreatePost.this, "no value here",
                        Toast.LENGTH_SHORT).show();
            }
        }}
}
