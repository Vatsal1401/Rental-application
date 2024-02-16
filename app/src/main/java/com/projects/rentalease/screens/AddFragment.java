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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.projects.rentalease.R;
import com.projects.rentalease.adapters.RentItemImageAdapter;
import com.projects.rentalease.daos.ProductsDao;
import com.projects.rentalease.model.Category;
import com.projects.rentalease.model.Product;
import com.projects.rentalease.databinding.FragmentAddBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class AddFragment extends Fragment {

    private FragmentAddBinding binding;

    private String EMPTY_CATEGORY = "Select Category";
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
        binding = FragmentAddBinding.inflate(inflater,container,false);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        setSpinner();

        imagesList = new ArrayList<>();

        return binding.getRoot();
    }

    private void setSpinner() {

        db.collection("categories").get().addOnCompleteListener(task -> {
            ArrayList<String> list = (ArrayList<String>)
                    task.getResult().toObjects(Category.class).stream().map(
                            category1 -> category1.name
                    ).collect(Collectors.toList());
            list.add(0,EMPTY_CATEGORY);

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    requireContext(),
                    android.R.layout.simple_spinner_item,
                    list
            );

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            binding.setCategoryButton.setAdapter(adapter);
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
            String error = checkInput();
            if(!error.equals("no")){
                Toast.makeText(requireContext(),error, Toast.LENGTH_LONG).show();
                return;
            }
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

    private String checkInput(){
        if(category == EMPTY_CATEGORY) return "You Must select Category";
        else if(imagesList.size() < 3) return "Atlest three image should be uploded";
        else if (binding.Name.getText().toString().isEmpty())return "You Must Add Title";
        else if (binding.description.getText().toString().isEmpty())return "Description must be added";
        else if(binding.price.getText().toString().isEmpty())return "You must provide Price";

        return "no";

    }
    private void clearInput(){
       binding.Name.setText("");
        binding.description.setText("");
        binding.price.setText("");
        binding.setCategoryButton.setSelection(0);
        adapter.clearImages();
    }
}