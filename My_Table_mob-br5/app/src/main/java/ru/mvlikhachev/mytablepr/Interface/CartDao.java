package ru.mvlikhachev.mytablepr.Interface;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ru.mvlikhachev.mytablepr.Domain.RestoranDomain;

@Dao
public interface CartDao {
    @Query("SELECT * FROM cart_items1")
    List<RestoranDomain> getAll();

    @Query("SELECT * FROM cart_items1 WHERE id = :id")
    RestoranDomain getById(int id);

    @Query("SELECT * FROM cart_items1 WHERE name LIKE :name LIMIT 1")
    RestoranDomain findByTitle(String name);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(RestoranDomain cartItem);

    @Update
    void update(RestoranDomain cartItem);

    @Delete
    void delete(RestoranDomain cartItem);

    @Query("DELETE FROM cart_items1")
    void deleteAll();
}