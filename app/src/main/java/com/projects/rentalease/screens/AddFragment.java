package com.projects.rentalease.screens;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;
import com.projects.rentalease.R;
import com.projects.rentalease.adapters.RentItemImageAdapter;
import com.projects.rentalease.daos.ProductsDao;
import com.projects.rentalease.data.Category;
import com.projects.rentalease.data.Product;
import com.projects.rentalease.databinding.FragmentAddBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class AddFragment extends Fragment {

    private FragmentAddBinding _binding;

    List<Uri> imagesList;
    RentItemImageAdapter adapter;

    RecyclerView recyclerView;

    int SELECT_IMAGE_CODE = 100;
    String category;

    private FirebaseUser user;

    private FirebaseAuth auth;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        _binding = FragmentAddBinding.inflate(inflater,container,false);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        setSpinner();

        imagesList = new ArrayList<>();

        return _binding.getRoot();
    }

    private void setSpinner() {

        db.collection("categories").get().addOnCompleteListener(task -> {
            ArrayList<String> list = (ArrayList<String>)
                    task.getResult().toObjects(Category.class).stream().map(
                            category1 -> category1.name
                    ).collect(Collectors.toList());
            list.add(0,"Select Category");

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    requireContext(),
                    android.R.layout.simple_spinner_item,
                    list
            );

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            _binding.setCategoryButton.setAdapter(adapter);
        });

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.add_image_button).setOnClickListener(view1 -> {
            // open image selection intent
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent, SELECT_IMAGE_CODE);
        });


        adapter = new RentItemImageAdapter(imagesList,getContext());

        recyclerView = view.findViewById(R.id.recyclerView);

        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));


        view.findViewById(R.id.publish).setOnClickListener(view1 -> {

            String name = ((EditText) view.findViewById(R.id.Name)).getText().toString();
            String description = ((EditText) view.findViewById(R.id.description)).getText().toString();
            String price = ((EditText) view.findViewById(R.id.price)).getText().toString();


            Product product = new Product(user.getUid(),category,name,description,price,"");
            new ProductsDao().addProduct(product,imagesList);
            clearInput();
        });

        view.findViewById(R.id.clear_button).setOnClickListener(view1 -> {
            clearInput();
        });

        Spinner spinner = view.findViewById(R.id.set_category_button);



        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == SELECT_IMAGE_CODE && resultCode == getActivity().RESULT_OK){
            assert data != null;
            Uri uri = data.getData();
            assert uri != null;
            imagesList.add(uri);
            adapter.notifyItemInserted(imagesList.size()-1);
        }
    }

    private void clearInput(){
       _binding.Name.setText("");
        _binding.description.setText("");
        _binding.price.setText("");
        adapter.notifyDataSetChanged();
    }
}