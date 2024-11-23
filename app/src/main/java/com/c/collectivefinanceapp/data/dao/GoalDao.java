package com.c.collectivefinanceapp.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.c.collectivefinanceapp.data.entities.Goal;

import java.util.List;

@Dao
public interface GoalDao {
    @Insert
    void insert(Goal goal);

    @Update
    void update(Goal goal);
    @Delete
    void delete(Goal goal);

    @Query("SELECT * FROM goal_table")
    List<Goal> getAll();
}
