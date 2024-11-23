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
import com.c.collectivefinanceapp.data.dao.GoalDao;
import com.c.collectivefinanceapp.data.entities.Goal;
import com.c.collectivefinanceapp.ui.acitivities.add_edit.AddEditAccumulationActivity;
import com.c.collectivefinanceapp.ui.acitivities.add_edit.AddEditExpenseActivity;
import com.c.collectivefinanceapp.ui.acitivities.add_edit.AddEditGoalAcitivity;
import com.c.collectivefinanceapp.ui.adapters.GoalAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class GoalActivity extends AppCompatActivity {

    private List<Goal> goalList;

    private GoalDao goalDao;

    private TextView tvAll;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal);
        setTitle("Цели");


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setSelectedItemId(R.id.bm_goal);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.bm_statistics) {
                    Intent intent = new Intent(GoalActivity.this, StatisticsActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    return true;
                } else if (id == R.id.bm_regular_payment) {
                    Intent intent = new Intent(GoalActivity.this, RegularPaymentsActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    return true;
                } else if (id == R.id.bm_goal) {
                    return true;
                } else if (id == R.id.bm_accumululation) {
                    Intent intent = new Intent(GoalActivity.this, AccumulationActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    return true;
                } else if (id == R.id.bm_collective) {
                    Intent intent = new Intent(GoalActivity.this, CollectiveFinanceActivity.class);
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
                Intent intent = new Intent(GoalActivity.this, AddEditGoalAcitivity.class);
                startActivity(intent);
            }
        });

        tvAll = findViewById(R.id.tvAll);

        goalDao = AppDatabase.getInstance(this).goalDao();
        loadAndShowGoals();
    }

    private void loadAndShowGoals() {
        goalList = new ArrayList<Goal>();

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                goalList = goalDao.getAll();
                showGoals();
            }
        });
    }

    private void showGoals() {
        RecyclerView rvItems = findViewById(R.id.rv_goals);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        GoalAdapter adapter = new GoalAdapter(goalList);

        double sum = 0;
        for (Goal goal : goalList) {
            sum += goal.getAmount();
        }
        tvAll.setText(String.valueOf(sum) + " руб.");

        adapter.setOnClickListener(new GoalAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Goal goal) {
                Intent intent = new Intent(GoalActivity.this, AddEditGoalAcitivity.class);
                intent.putExtra("goal", goal);
                startActivity(intent);
            }
        });

        rvItems.setAdapter(adapter);
        rvItems.setLayoutManager(linearLayoutManager);
        rvItems.setHasFixedSize(true);
        rvItems.setNestedScrollingEnabled(true);
    }
}