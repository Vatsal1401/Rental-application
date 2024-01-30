package com.projects.rentalease.screens;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.projects.rentalease.R;
import com.projects.rentalease.adapters.CategoryListAdapter;
import com.projects.rentalease.sample_data.CategoryListData;


public class HomeFragment extends Fragment {



    private RecyclerView recyclerView;
    private CategoryListAdapter categoryListAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.category_recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        categoryListAdapter = new CategoryListAdapter(CategoryListData.getCategoryList(),getContext());

        recyclerView.setAdapter(categoryListAdapter);

    }
}