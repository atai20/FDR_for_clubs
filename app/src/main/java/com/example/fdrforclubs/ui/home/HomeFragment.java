package com.example.fdrforclubs.ui.home;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.fdrforclubs.MainActivity2;
import com.example.fdrforclubs.R;
import com.example.fdrforclubs.databinding.FragmentHomeBinding;
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
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    public static String user_name3;
    public static String user_club_name;
    public static String user_status;
    public static String user_name2;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        DatabaseReference usersRef;


        usersRef = database.getReference("Users");
        DatabaseReference user_return;


        DatabaseReference myRef2 = database.getReference("clubs");

        DatabaseReference usersRef2 = database.getReference("Users");

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();



        StorageReference mountainImagesRef = storageRef.child("images/7cbc0f07-3f4b-4249-9bf6-4cc029bc8f8e");




         binding = FragmentHomeBinding.inflate(inflater, container, false);
         View root = binding.getRoot();


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
                            user_name3 = task.getResult().child("name").getValue().toString();
                            user_club_name = task.getResult().child("club").getValue().toString();
                            user_status = task.getResult().child("status").getValue().toString();
                            TextView textView = new TextView(new ContextThemeWrapper(getActivity().getApplicationContext(), R.style.CardView1), null, 0);


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

                                    View cardView = LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.cardview, null);
                                    TextView tv = (TextView)cardView.findViewById(R.id.card_textview);
                                    ImageView imageView2 = (ImageView) cardView.findViewById(R.id.imageView4);
                                    View main_cont = LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.fragment_home, null);

                                    LinearLayout linearLayout = binding.postsLayout;
                                    StorageReference mountainImagesRef = storageRef.child("images").child(dataSnapshot.child("image").getValue().toString());
                                    Toast.makeText(getActivity().getApplicationContext(),
                                            dataSnapshot.child("image").getValue().toString()+ "\nf75d18d9-04ef-47c1-85f7-f0d0fcacd030",
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
                                            Toast.makeText(getActivity().getApplicationContext(),
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




        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}