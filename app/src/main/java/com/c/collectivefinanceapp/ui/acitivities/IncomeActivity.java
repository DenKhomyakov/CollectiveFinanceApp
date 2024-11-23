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
import com.c.collectivefinanceapp.data.dao.IncomeDao;
import com.c.collectivefinanceapp.data.entities.Income;
import com.c.collectivefinanceapp.ui.acitivities.add_edit.AddEditAccumulationActivity;
import com.c.collectivefinanceapp.ui.acitivities.add_edit.AddEditGoalAcitivity;
import com.c.collectivefinanceapp.ui.acitivities.add_edit.AddEditIncomeActivity;
import com.c.collectivefinanceapp.ui.adapters.IncomeAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class IncomeActivity extends AppCompatActivity {

    private List<Income> incomeList;

    private IncomeDao incomeDao;

    private TextView tvAll;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income);
        setTitle("Доходы");

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setSelectedItemId(R.id.bm_statistics);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.bm_statistics) {
                    Intent intent = new Intent(IncomeActivity.this, StatisticsActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    return true;
                } else if (id == R.id.bm_regular_payment) {
                    Intent intent = new Intent(IncomeActivity.this, RegularPaymentsActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    return true;
                } else if (id == R.id.bm_goal) {
                    Intent intent = new Intent(IncomeActivity.this, GoalActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    return true;
                } else if (id == R.id.bm_accumululation) {
                    Intent intent = new Intent(IncomeActivity.this, AccumulationActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    return true;
                } else if (id == R.id.bm_collective) {
                    Intent intent = new Intent(IncomeActivity.this, CollectiveFinanceActivity.class);
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
                Intent intent = new Intent(IncomeActivity.this, AddEditIncomeActivity.class);
                startActivity(intent);
            }
        });

        tvAll = findViewById(R.id.tvAll);

        incomeDao = AppDatabase.getInstance(this).incomeDao();
        loadAndShowIncomeList();
    }

    private void loadAndShowIncomeList() {
        incomeList = new ArrayList<Income>();

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                incomeList = incomeDao.getAll();
                showIncome();
            }
        });
    }

    private void showIncome() {
        RecyclerView rvItems = findViewById(R.id.rv_income);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        IncomeAdapter adapter = new IncomeAdapter(incomeList);

        double sum = 0;
        for (Income income : incomeList) {
            sum += income.getAmount();
        }
        tvAll.setText(String.valueOf(sum) + " руб.");

        adapter.setOnClickListener(new IncomeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Income income) {
                Intent intent = new Intent(IncomeActivity.this, AddEditIncomeActivity.class);
                intent.putExtra("income", income);
                startActivity(intent);
            }
        });

        rvItems.setAdapter(adapter);
        rvItems.setLayoutManager(linearLayoutManager);
        rvItems.setHasFixedSize(true);
        rvItems.setNestedScrollingEnabled(true);
    }
}