package com.example.week10.Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.week10.Entities.Coin;

import java.util.List;



@Dao
public interface CoinDao {


    // Query and return all results currently in the DB
    @Query("SELECT * FROM coin")
    List<Coin> getAllCoins();

    // Insert all received records into the DB
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllCoin(List<Coin> coins);

    // Query and return ONE coin’s results based on the received ID
    @Query("SELECT * FROM coin WHERE id = :id")
    Coin getCoinById(String id);

    @Query("DELETE FROM coin WHERE id = :id")
    void removeCoinbyId(String id);

    // Delete all records currently in the DB
    @Query("DELETE FROM coin")
    void clearTable();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCoin(Coin coin);
}
