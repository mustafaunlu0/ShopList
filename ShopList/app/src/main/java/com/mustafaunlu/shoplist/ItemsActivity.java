package com.mustafaunlu.shoplist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.mustafaunlu.shoplist.databinding.ActivityItemsBinding;
import com.mustafaunlu.shoplist.db.Items;
import com.mustafaunlu.shoplist.viewmodel.ItemsActivityViewModel;

import java.util.List;

public class ItemsActivity extends AppCompatActivity implements ItemsAdapter.HandleItemClick {
    private int category_id;
    private ItemsAdapter itemsAdapter;
    private ItemsActivityViewModel viewModel;
    private Items itemToUpdate=null;
    private ActivityItemsBinding binding;
    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityItemsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        category_id=getIntent().getIntExtra("category_id",0);
        String categoryName=getIntent().getStringExtra("category_name");

        getSupportActionBar().setTitle(categoryName);

        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        binding.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String itemName=binding.addNewItemInput.getText().toString();
                if(TextUtils.isEmpty(itemName)){
                    Toast.makeText(ItemsActivity.this, "Enter item name", Toast.LENGTH_LONG).show();
                    return;
                }
                if(itemToUpdate==null){
                    saveNewItem(itemName);
                }
                else{
                    updateNewItem(itemName);
                }
            }
        });
        initRecyclerView();
        initViewModel();
        viewModel.getAllItemsList(category_id);
    }

    private void initViewModel() {
        viewModel=new ViewModelProvider(this).get(ItemsActivityViewModel.class);
        viewModel.getItemsListObserver().observe(this, new Observer<List<Items>>() {
            @Override
            public void onChanged(List<Items> items) {
                if(items==null){
                    binding.recyclerView.setVisibility(View.GONE);
                    binding.noResult.setVisibility(View.VISIBLE);
                }
                else{
                    itemsAdapter.setItemsList(items);
                    binding.noResult.setVisibility(View.GONE);
                    binding.recyclerView.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void initRecyclerView(){
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(ItemsActivity.this));
        itemsAdapter=new ItemsAdapter(this,this);
        binding.recyclerView.setAdapter(itemsAdapter);
    }

    private void updateNewItem(String itemName) {
        itemToUpdate.itemName=itemName;
        viewModel.updateItems(itemToUpdate);
        binding.addNewItemInput.setText("");
        itemToUpdate=null;

    }

    private void saveNewItem(String itemName) {
        Items item=new Items();
        item.itemName=itemName;
        item.categoryId=category_id;
        viewModel.insertItems(item);
        binding.addNewItemInput.setText("");
    }

    @Override
    public void itemClick(Items item) {
        if(item.completed){
            item.completed=false;
        }
        else{
            item.completed=true;
        }
        viewModel.updateItems(item);
    }

    @Override
    public void removeItem(Items item) {
        viewModel.deleteItems(item);

    }

    @Override
    public void editItem(Items item) {
        this.itemToUpdate=item;
        binding.addNewItemInput.setText(item.itemName);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
        }

        return super.onOptionsItemSelected(item);
    }
}