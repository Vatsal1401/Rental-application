package com.projects.rentalease.screens;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.projects.rentalease.R;


public class ProfileFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference userRef;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null) {
            String userId = firebaseAuth.getCurrentUser().getUid();
            userRef = FirebaseDatabase.getInstance().getReference("users").child(userId);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        if (userRef != null) {
            loadUserData(view);
        }

        return view;
    }

    private void loadUserData(View view) {
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User userProfile = dataSnapshot.getValue(User.class);

                if (userProfile != null) {
                    TextView userNameTextView = view.findViewById(R.id.userName);
                    userNameTextView.setText(userProfile.name);

                    TextView userEmailTextView = view.findViewById(R.id.user_email);
                    userEmailTextView.setText(userProfile.email);

                    TextView userGenderTextView = view.findViewById(R.id.user_gender);
                    userGenderTextView.setText(userProfile.gender);

                    TextView userDobTextView = view.findViewById(R.id.user_dob);
                    userDobTextView.setText(userProfile.dob);

                    ImageView userProfilePicImageView = view.findViewById(R.id.user_profile_pic);
                    Picasso.get().load(userProfile.profilePic).into(userProfilePicImageView);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("ProfileFragment", "loadUserData:onCancelled", databaseError.toException());
            }
        });
    }

    private static class User {
        public String name;
        public String email;
        public String gender;
        public String dob;
        public String profilePic;

        public User() {
        }

        public User(String name, String email, String gender, String dob, String profilePic) {
            this.name = name;
            this.email = email;
            this.gender = gender;
            this.dob = dob;
            this.profilePic = profilePic;
        }
    }
}
``}