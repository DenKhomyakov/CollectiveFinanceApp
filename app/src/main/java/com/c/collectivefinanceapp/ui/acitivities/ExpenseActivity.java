package com.c.collectivefinanceapp.ui.acitivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.c.collectivefinanceapp.R;
import com.c.collectivefinanceapp.data.AppDatabase;
import com.c.collectivefinanceapp.data.dao.ExpenseDao;
import com.c.collectivefinanceapp.data.entities.Accumulation;
import com.c.collectivefinanceapp.data.entities.Expense;
import com.c.collectivefinanceapp.ui.acitivities.add_edit.AddEditExpenseActivity;
import com.c.collectivefinanceapp.ui.acitivities.add_edit.AddEditUserActivity;
import com.c.collectivefinanceapp.ui.adapters.ExpenseAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class ExpenseActivity extends AppCompatActivity {

    private List<Expense> expenseList;
    private ExpenseDao expenseDao;
    private TextView tvAll;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);
        setTitle("Расходы");

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setSelectedItemId(R.id.bm_statistics);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.bm_statistics) {
                    Intent intent = new Intent(ExpenseActivity.this, StatisticsActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    return true;
                } else if (id == R.id.bm_regular_payment) {
                    Intent intent = new Intent(ExpenseActivity.this, RegularPaymentsActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    return true;
                } else if (id == R.id.bm_goal) {
                    Intent intent = new Intent(ExpenseActivity.this, GoalActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    return true;
                } else if (id == R.id.bm_accumululation) {
                    Intent intent = new Intent(ExpenseActivity.this, AccumulationActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    return true;
                } else if (id == R.id.bm_collective) {
                    Intent intent = new Intent(ExpenseActivity.this, CollectiveFinanceActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    return true;
                }
                return true;
            }
        });

        FloatingActionButton fabAdd = findViewById(R.id.fab_add);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ExpenseActivity.this, AddEditExpenseActivity.class);
                startActivity(intent);
            }
        });

        tvAll = findViewById(R.id.tvAll);

        expenseDao = AppDatabase.getInstance(this).expenseDao();
        loadAndShowExpenses();
    }

    private void loadAndShowExpenses(){
        expenseList = new ArrayList<Expense>();

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                expenseList = expenseDao.getAll();
                showExpenses();
            }
        });
    }

    private void showExpenses(){
        RecyclerView rvItems = findViewById(R.id.rv_expenses);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        ExpenseAdapter expenseAdapter = new ExpenseAdapter(expenseList);

        double sum = 0;
        for (Expense expense : expenseList) {
            sum += expense.getAmount();
        }
        tvAll.setText(String.valueOf(sum) + " руб.");

        expenseAdapter.setOnClickListener(new ExpenseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Expense expense) {
                Intent intent = new Intent(ExpenseActivity.this, AddEditExpenseActivity.class);
                intent.putExtra("expense", expense);
                startActivity(intent);
            }
        });

        rvItems.setAdapter(expenseAdapter);
        rvItems.setLayoutManager(linearLayoutManager);
        rvItems.setHasFixedSize(true);
        rvItems.setNestedScrollingEnabled(true);
    }
}