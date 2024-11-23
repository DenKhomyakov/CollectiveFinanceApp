package com.c.collectivefinanceapp.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.c.collectivefinanceapp.data.entities.Accumulation;

import java.util.List;

@Dao
public interface AccumulationDao {
    @Insert
    void insert(Accumulation accumulation);

    @Update
    void update(Accumulation accumulation);

    @Delete
    void delete(Accumulation accumulation);

    @Query("SELECT * FROM accumulation_table")
    List<Accumulation> getAll();
}
