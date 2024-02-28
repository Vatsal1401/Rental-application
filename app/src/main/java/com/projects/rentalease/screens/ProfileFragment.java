package com.projects.rentalease.screens;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.projects.rentalease.R;
import com.projects.rentalease.model.User;


public class ProfileFragment extends Fragment {



    private FirebaseAuth firebaseAuth;
    private DocumentReference userRef;
    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null) {
            String userId = firebaseAuth.getCurrentUser().getUid();
            userRef = FirebaseFirestore.getInstance().collection("users").document(userId);
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
        userRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                assert value != null;
                User userProfile = value.toObject(User.class);

                if (userProfile != null) {
                    TextView userNameTextView = view.findViewById(R.id.userName);
                    userNameTextView.setText(userProfile.name);

                    TextView userEmailTextView = view.findViewById(R.id.user_email);
                    userEmailTextView.setText(userProfile.email);

                    TextView userGenderTextView = view.findViewById(R.id.user_gender);
                    // todo add gender in model/User class and take input of it in RegistrationActivity
//                    userGenderTextView.setText(userProfile.gender);

                    // todo add dob in model/User class and take input of it in RegistrationActivity
                    TextView userDobTextView = view.findViewById(R.id.user_dob);
//                    userDobTextView.setText(userProfile.dob);

                    ImageView userProfilePicImageView = view.findViewById(R.id.user_profile_pic);
                    Glide.with(requireContext()).load(userProfile.image_uri).into(userProfilePicImageView);
                }
            }
        });
    }

}