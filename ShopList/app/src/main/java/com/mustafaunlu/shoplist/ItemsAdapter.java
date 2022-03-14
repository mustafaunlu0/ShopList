package com.mustafaunlu.shoplist;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mustafaunlu.shoplist.databinding.RecyclerRowBinding;
import com.mustafaunlu.shoplist.db.Items;

import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemsViewHolder> {
    private Context context;
    private List<Items> itemsList;
    private HandleItemClick clickListener;
    public ItemsAdapter(Context context,HandleItemClick clickListener){
        this.clickListener=clickListener;
        this.context=context;
    }
    public void setItemsList(List<Items> itemList){
        this.itemsList=itemList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerRowBinding recyclerRowBinding=RecyclerRowBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ItemsViewHolder(recyclerRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsViewHolder holder, int position) {
            holder.recyclerRowBinding.tvCategoryName.setText(this.itemsList.get(position).itemName);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.itemClick(itemsList.get(position));
                }
            });
            holder.recyclerRowBinding.editCategory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.editItem(itemsList.get(position));
                }
            });
            holder.recyclerRowBinding.removeCategory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.removeItem(itemsList.get(position));
                }
            });

            if(this.itemsList.get(position).completed){
                holder.recyclerRowBinding.tvCategoryName.setPaintFlags(holder.recyclerRowBinding.tvCategoryName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            }else{
                holder.recyclerRowBinding.tvCategoryName.setPaintFlags(0);
            }
    }

    @Override
    public int getItemCount() {
        if(itemsList==null || itemsList.size()==0){
            return 0;
        }
        else{
            return  itemsList.size();

        }

    }

    public class ItemsViewHolder extends RecyclerView.ViewHolder{
        RecyclerRowBinding recyclerRowBinding;
        public ItemsViewHolder(RecyclerRowBinding recyclerRowBinding) {
            super(recyclerRowBinding.getRoot());
            this.recyclerRowBinding=recyclerRowBinding;
        }
    }

    public interface HandleItemClick{
        void itemClick(Items item);
        void removeItem(Items item);
        void editItem(Items item);
    }




}
