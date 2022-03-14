package com.mustafaunlu.shoplist.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.mustafaunlu.shoplist.db.AppDatabase;
import com.mustafaunlu.shoplist.db.Items;

import java.util.List;

public class ItemsActivityViewModel extends AndroidViewModel{
    private MutableLiveData<List<Items>> listOfItems;
    private AppDatabase appDatabase;

    public ItemsActivityViewModel(Application application){
        super(application);
        listOfItems=new MutableLiveData<>();
        appDatabase=AppDatabase.getDBInstance(getApplication().getApplicationContext());

    }
    public MutableLiveData<List<Items>> getItemsListObserver(){
        return listOfItems;
    }

    public void getAllItemsList(int categoryID){
        List<Items> itemsList=appDatabase.shopListDao().getAllItemsList(categoryID);

        if(itemsList.size()>0){
            listOfItems.postValue(itemsList);
        }
        else{
            listOfItems.postValue(null);
        }
    }
    public void insertItems(Items item){
        appDatabase.shopListDao().insertItems(item);
        getAllItemsList(item.categoryId);
    }
    public void updateItems(Items item){
        appDatabase.shopListDao().updateItems(item);
        getAllItemsList(item.categoryId);
    }
    public void deleteItems(Items item){
        appDatabase.shopListDao().deleteItems(item);
        getAllItemsList(item.categoryId);
    }
}
