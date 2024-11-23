package com.c.collectivefinanceapp.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.c.collectivefinanceapp.data.entities.RegularPayment;
import com.c.collectivefinanceapp.data.entities.User;

import java.util.List;

@Dao
public interface UserDao {
    @Insert
    void insert(User user);

    @Update
    void update(User user);
    @Delete
    void delete(User user);

    @Query("SELECT * FROM user_table")
    List<User> getAll();
}
