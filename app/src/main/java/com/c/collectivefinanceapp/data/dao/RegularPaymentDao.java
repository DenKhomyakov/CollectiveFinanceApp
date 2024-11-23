package com.c.collectivefinanceapp.data.dao;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.c.collectivefinanceapp.data.entities.RegularPayment;

import java.util.List;

@Dao
public interface RegularPaymentDao {
    @Insert
    void insert(RegularPayment regularPayment);

    @Update
    void update(RegularPayment regularPayment);
    @Delete
    void delete(RegularPayment regularPayment);

    @Query("SELECT * FROM regular_payment_table")
    List<RegularPayment> getAll();
}
