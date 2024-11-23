package com.c.collectivefinanceapp.data.dao;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.c.collectivefinanceapp.data.entities.Income;

import java.util.List;

@Dao
public interface IncomeDao {
    @Insert
    void insert(Income income);

    @Update
    void update(Income income);
    @Delete
    void delete(Income income);

    @Query("SELECT * FROM income_table")
    List<Income> getAll();
}
