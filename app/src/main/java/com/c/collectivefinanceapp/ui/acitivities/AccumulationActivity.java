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
import com.c.collectivefinanceapp.data.dao.AccumulationDao;
import com.c.collectivefinanceapp.data.entities.Accumulation;
import com.c.collectivefinanceapp.data.entities.Expense;
import com.c.collectivefinanceapp.ui.acitivities.add_edit.AddEditAccumulationActivity;
import com.c.collectivefinanceapp.ui.acitivities.add_edit.AddEditExpenseActivity;
import com.c.collectivefinanceapp.ui.adapters.AccumulationAdapter;
import com.c.collectivefinanceapp.ui.adapters.ExpenseAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class AccumulationActivity extends AppCompatActivity {

    private List<Accumulation> accumulationList;
    private AccumulationDao accumulationDao;

    private TextView tvAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accumulation);
        setTitle("Накопления");

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setSelectedItemId(R.id.bm_accumululation);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.bm_statistics) {
                    Intent intent = new Intent(AccumulationActivity.this, StatisticsActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    return true;
                } else if (id == R.id.bm_regular_payment) {
                    Intent intent = new Intent(AccumulationActivity.this, RegularPaymentsActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    return true;
                } else if (id == R.id.bm_goal) {
                    Intent intent = new Intent(AccumulationActivity.this, GoalActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    return true;
                } else if (id == R.id.bm_accumululation) {
                    return true;
                } else if (id == R.id.bm_collective) {
                    Intent intent = new Intent(AccumulationActivity.this, CollectiveFinanceActivity.class);
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
                Intent intent = new Intent(AccumulationActivity.this, AddEditAccumulationActivity.class);
                startActivity(intent);
            }
        });

        tvAll = findViewById(R.id.tvAll);

        accumulationDao = AppDatabase.getInstance(this).accumulationDao();
        loadAndShowAccumulations();
    }

    private void loadAndShowAccumulations() {
        accumulationList = new ArrayList<Accumulation>();

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                accumulationList = accumulationDao.getAll();
                showAccumulations();
            }
        });
    }

    private void showAccumulations() {
        RecyclerView rvItems = findViewById(R.id.rv_accumulation);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        AccumulationAdapter adapter = new AccumulationAdapter(accumulationList);

        double sum = 0;
        for (Accumulation accumulation : accumulationList) {
            sum += accumulation.getAmount();
        }
        tvAll.setText(String.valueOf(sum) + " руб.");

        adapter.setOnClickListener(new AccumulationAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Accumulation accumulation) {
                Intent intent = new Intent(AccumulationActivity.this, AddEditAccumulationActivity.class);
                intent.putExtra("accumulation", accumulation);
                startActivity(intent);
            }
        });

        rvItems.setAdapter(adapter);
        rvItems.setLayoutManager(linearLayoutManager);
        rvItems.setHasFixedSize(true);
        rvItems.setNestedScrollingEnabled(true);
    }
}