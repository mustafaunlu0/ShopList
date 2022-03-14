package com.mustafaunlu.shoplist.db;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = {Category.class,Items.class},version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ShopListDao shopListDao();
    public static AppDatabase INSTANCE;
    public static AppDatabase getDBInstance(Context context){

        if(INSTANCE==null){
            INSTANCE= Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class,"AppDB").allowMainThreadQueries().build();
        }
        return INSTANCE;


    }

}
