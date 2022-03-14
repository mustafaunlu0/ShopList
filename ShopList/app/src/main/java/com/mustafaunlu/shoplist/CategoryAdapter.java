package com.mustafaunlu.shoplist;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mustafaunlu.shoplist.databinding.RecyclerRowBinding;
import com.mustafaunlu.shoplist.db.Category;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private Context context;
    private List<Category> categoryList;
    private HandleCategoryClick clickListener;

    public CategoryAdapter(Context context,HandleCategoryClick clickListener) {
        this.context = context;
        this.clickListener=clickListener;
    }

    public void setCategoryList(List<Category> categoryList){
        this.categoryList=categoryList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerRowBinding recyclerRowBinding=RecyclerRowBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new CategoryViewHolder(recyclerRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.recyclerRowBinding.tvCategoryName.setText(this.categoryList.get(position).categoryName);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.itemClick(categoryList.get(position));
            }
        });
        holder.recyclerRowBinding.editCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.editItem(categoryList.get(position));
            }
        });
        holder.recyclerRowBinding.removeCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.removeItem(categoryList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        if(categoryList==null || categoryList.size()==0){
            return 0;
        }
        else{
            return categoryList.size();
        }

    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        RecyclerRowBinding recyclerRowBinding;
        public CategoryViewHolder(RecyclerRowBinding recyclerRowBinding) {
            super(recyclerRowBinding.getRoot());
            this.recyclerRowBinding=recyclerRowBinding;
        }
    }
    public interface HandleCategoryClick{
        void itemClick(Category category);
        void removeItem(Category category);
        void editItem(Category category);
    }
}
