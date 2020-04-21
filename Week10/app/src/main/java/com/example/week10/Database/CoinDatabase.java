package com.example.week10.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.week10.Entities.Coin;

@Database(entities = {Coin.class}, version = 1, exportSchema = false)
public abstract class CoinDatabase extends RoomDatabase {
    public abstract CoinDao coinDao();

    private static CoinDatabase instance;
    public static CoinDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context, CoinDatabase.class,
                    "FavouriteCatDb").allowMainThreadQueries().build();
        }
        return instance;
    }
}
