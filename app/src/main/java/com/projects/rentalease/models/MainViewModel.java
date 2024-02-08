package com.projects.rentalease.models;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.projects.rentalease.data.Category;

public class MainViewModel extends ViewModel {

    private final MutableLiveData<Category> _activeCategory = new MutableLiveData<>();
    public final LiveData<Category> activeCategory = _activeCategory;


    public void setCategory(Category category) {
        _activeCategory.setValue(category);
    }
}
