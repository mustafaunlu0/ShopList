package com.mustafaunlu.shoplist.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.mustafaunlu.shoplist.db.AppDatabase;
import com.mustafaunlu.shoplist.db.Category;

import java.util.List;


public class MainActivityViewModel extends AndroidViewModel {
    private MutableLiveData<List<Category>> listOfCategory;
    private AppDatabase appDatabase;


    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        listOfCategory=new MutableLiveData<>();
        appDatabase=AppDatabase.getDBInstance(getApplication().getApplicationContext());

    }
    public MutableLiveData<List<Category>> getCategoryListObserver(){
        return listOfCategory;
    }

    public void getAllCategoryList(){
        List<Category> categoryList=appDatabase.shopListDao().getAllCategoriesList();
        //chech null
        if(categoryList.size()>0){
            listOfCategory.postValue(categoryList);

        }
        else{
            listOfCategory.postValue(null);
        }


    }
    public void insertCategory(String catName){
        Category category=new Category();
        category.categoryName=catName;
        appDatabase.shopListDao().insertCategory(category);
        getAllCategoryList();;
    }
    public void updateCategoy(Category category){
        appDatabase.shopListDao().updateCategory(category);
        getAllCategoryList();
    }
    public void deleteCategory(Category category){
        appDatabase.shopListDao().deleteCategory(category);
        getAllCategoryList();
    }
}
