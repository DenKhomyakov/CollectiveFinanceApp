package com.c.collectivefinanceapp.data;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.c.collectivefinanceapp.data.dao.AccumulationDao;
import com.c.collectivefinanceapp.data.dao.ExpenseDao;
import com.c.collectivefinanceapp.data.dao.GoalDao;
import com.c.collectivefinanceapp.data.dao.IncomeDao;
import com.c.collectivefinanceapp.data.dao.RegularPaymentDao;
import com.c.collectivefinanceapp.data.dao.UserDao;
import com.c.collectivefinanceapp.data.entities.Accumulation;
import com.c.collectivefinanceapp.data.entities.Expense;
import com.c.collectivefinanceapp.data.entities.Goal;
import com.c.collectivefinanceapp.data.entities.Income;
import com.c.collectivefinanceapp.data.entities.RegularPayment;
import com.c.collectivefinanceapp.data.entities.User;

@Database(entities = {Accumulation.class, Expense.class, Goal.class, Income.class, RegularPayment.class, User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

    public abstract AccumulationDao accumulationDao();

    public abstract ExpenseDao expenseDao();

    public abstract GoalDao goalDao();

    public abstract IncomeDao incomeDao();

    public abstract RegularPaymentDao regularPaymentDao();

    public abstract UserDao userDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "collective_finance_app_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

}
