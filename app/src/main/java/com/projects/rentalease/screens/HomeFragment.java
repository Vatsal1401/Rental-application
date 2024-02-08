package com.projects.rentalease.screens;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.projects.rentalease.R;
import com.projects.rentalease.adapters.CategoryListAdapter;
import com.projects.rentalease.data.Category;
import com.projects.rentalease.models.MainViewModel;




public class HomeFragment extends Fragment implements CategoryListAdapter.CategoryListListeners {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference categorysRef = db.collection("categories");

    private RecyclerView recyclerView;
    private CategoryListAdapter categoryListAdapter;
    private MainViewModel mainViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FirestoreRecyclerOptions<Category> fireStoreRecyclerOption =
                new FirestoreRecyclerOptions.Builder<Category>()
                        .setQuery(categorysRef, Category.class)
                        .build();

        categoryListAdapter = new CategoryListAdapter(fireStoreRecyclerOption,getContext(),this);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {


        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.category_recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        recyclerView.setAdapter(categoryListAdapter);

    }

    @Override
    public void onStart() {
        categoryListAdapter.startListening();
        super.onStart();
    }

    @Override
    public void onStop() {
        categoryListAdapter.stopListening();
        super.onStop();
    }

    @Override
    public void onCategoryClick(Category category) {
        mainViewModel.setCategory(category);
        Navigation.findNavController(requireView()).navigate(R.id.action_home_to_productsFragment);
    }
}