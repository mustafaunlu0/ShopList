package com.mustafaunlu.shoplist;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mustafaunlu.shoplist.databinding.ActivityMainBinding;
import com.mustafaunlu.shoplist.db.Category;
import com.mustafaunlu.shoplist.viewmodel.MainActivityViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity implements CategoryAdapter.HandleCategoryClick {

    private ActivityMainBinding binding;
    private MainActivityViewModel viewModel;
    private Category categoryForEdit;
    private CategoryAdapter categoryAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().setTitle("ShopList");

        binding.addNewCategoryImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddCategoryDialog(false);
            }
        });
        initViewModel();
        initRecyclerView();
    }
    private void initRecyclerView(){
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        categoryAdapter=new CategoryAdapter(MainActivity.this,this);
        binding.recyclerView.setAdapter(categoryAdapter);
    }

    private void initViewModel() {
        viewModel=new ViewModelProvider(this).get(MainActivityViewModel.class);
        viewModel.getCategoryListObserver().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                if(categories==null){
                    binding.noResult.setVisibility(View.VISIBLE);
                    binding.recyclerView.setVisibility(View.GONE);
                }
                else{
                    binding.recyclerView.setVisibility(View.VISIBLE);
                    categoryAdapter.setCategoryList(categories);
                    binding.noResult.setVisibility(View.GONE);
                }
            }
        });
    }

    private void showAddCategoryDialog(boolean isForEdit) {
        AlertDialog dialogBuilder=new AlertDialog.Builder(this).create();
        //alert xmli oluştur gel
        View dialogView=getLayoutInflater().inflate(R.layout.add_category_layout,null);
        EditText enterCategoryInput=dialogView.findViewById(R.id.enterCategoryInput);
        TextView createButton=dialogView.findViewById(R.id.createButton);
        TextView cancelButton=dialogView.findViewById(R.id.cancelButton);
        if(isForEdit){
            createButton.setText("Update");
            enterCategoryInput.setText(categoryForEdit.categoryName);
        }
            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogBuilder.dismiss();
                }
            });
            createButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String name=enterCategoryInput.getText().toString();
                    if(TextUtils.isEmpty(name)){
                        Toast.makeText(MainActivity.this, "Enter category name", Toast.LENGTH_LONG).show();
                    }
                    if(isForEdit){
                        categoryForEdit.categoryName=name;
                    }
                    else{
                        viewModel.insertCategory(name);
                        dialogBuilder.dismiss();
                    }

                }
            });
            dialogBuilder.setView(dialogView);
            dialogBuilder.show();

    }

    @Override
    public void itemClick(Category category) {
        Intent intent=new Intent(MainActivity.this,ItemsActivity.class);
        intent.putExtra("category_id",category.uid);
        intent.putExtra("category_name",category.categoryName);
        startActivity(intent);
    }

    @Override
    public void removeItem(Category category) {
        viewModel.deleteCategory(category);
    }

    @Override
    public void editItem(Category category) {
        this.categoryForEdit=category;
        showAddCategoryDialog(true);
    }
}