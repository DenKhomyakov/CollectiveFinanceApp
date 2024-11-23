package com.c.collectivefinanceapp.ui.acitivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.c.collectivefinanceapp.R;
import com.c.collectivefinanceapp.data.AppDatabase;
import com.c.collectivefinanceapp.data.dao.ExpenseDao;
import com.c.collectivefinanceapp.data.dao.IncomeDao;
import com.c.collectivefinanceapp.data.entities.Expense;
import com.c.collectivefinanceapp.data.entities.Income;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class StatisticsActivity extends AppCompatActivity {

    private TextView tvExpenseSum;
    private TextView tvIncomeSum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        setTitle("Статистика");

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setSelectedItemId(R.id.bm_statistics);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.bm_statistics) {
                    return true;
                } else if (id == R.id.bm_regular_payment) {
                    Intent intent = new Intent(StatisticsActivity.this, RegularPaymentsActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    return true;
                } else if (id == R.id.bm_goal) {
                    Intent intent = new Intent(StatisticsActivity.this, GoalActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    return true;
                } else if (id == R.id.bm_accumululation) {
                    Intent intent = new Intent(StatisticsActivity.this, AccumulationActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    return true;
                } else if (id == R.id.bm_collective) {
                    Intent intent = new Intent(StatisticsActivity.this, CollectiveFinanceActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    return true;
                }
                return true;
            }
        });

        tvExpenseSum = findViewById(R.id.tvExpenseSum);
        tvIncomeSum = findViewById(R.id.tvIncomeSum);

        CardView cardIncome = findViewById(R.id.cardIncome);
        CardView cardExpense = findViewById(R.id.cardExpense);

        AppDatabase appDatabase = AppDatabase.getInstance(this);
        IncomeDao incomeDao = appDatabase.incomeDao();
        ExpenseDao expenseDao = appDatabase.expenseDao();

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                List<Income> incomeList = incomeDao.getAll();
                double incomeSum = 0;
                for (Income income : incomeList) {
                    incomeSum += income.getAmount();
                }
                tvIncomeSum.setText(String.valueOf(incomeSum) + " руб.");
            }
        });

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                List<Expense> expenseList = expenseDao.getAll();
                double expenseSum = 0;
                for (Expense expense : expenseList) {
                    expenseSum += expense.getAmount();
                }
                tvExpenseSum.setText(String.valueOf(expenseSum) + " руб.");
            }
        });

        cardExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StatisticsActivity.this, ExpenseActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        cardIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StatisticsActivity.this, IncomeActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });
    }
}