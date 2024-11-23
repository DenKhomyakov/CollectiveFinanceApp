package com.c.collectivefinanceapp.ui.acitivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.c.collectivefinanceapp.R;
import com.c.collectivefinanceapp.data.AppDatabase;
import com.c.collectivefinanceapp.data.dao.UserDao;
import com.c.collectivefinanceapp.data.entities.User;
import com.c.collectivefinanceapp.ui.acitivities.add_edit.AddEditAccumulationActivity;
import com.c.collectivefinanceapp.ui.acitivities.add_edit.AddEditCollectiveExpesnseActivity;
import com.c.collectivefinanceapp.ui.acitivities.add_edit.AddEditCollectiveIncomeActivity;
import com.c.collectivefinanceapp.ui.acitivities.add_edit.AddEditUserActivity;
import com.c.collectivefinanceapp.ui.adapters.UserAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class CollectiveFinanceActivity extends AppCompatActivity {

    private List<User> userList;
    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collective_finance);
        setTitle("Совместный бюджет");

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setSelectedItemId(R.id.bm_collective);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.bm_statistics) {
                    Intent intent = new Intent(CollectiveFinanceActivity.this, StatisticsActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    return true;
                } else if (id == R.id.bm_regular_payment) {
                    Intent intent = new Intent(CollectiveFinanceActivity.this, RegularPaymentsActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    return true;
                } else if (id == R.id.bm_goal) {
                    Intent intent = new Intent(CollectiveFinanceActivity.this, GoalActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    return true;
                } else if (id == R.id.bm_accumululation) {
                    Intent intent = new Intent(CollectiveFinanceActivity.this, AccumulationActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    return true;
                } else if (id == R.id.bm_collective) {
                    return true;
                }
                return true;
            }
        });

        FloatingActionButton fabAdd = findViewById(R.id.fab_add);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CollectiveFinanceActivity.this, AddEditUserActivity.class);
                startActivity(intent);
            }
        });

        CardView cardViewIncome = findViewById(R.id.cardIncome);

        cardViewIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CollectiveFinanceActivity.this, AddEditCollectiveIncomeActivity.class);
                startActivity(intent);
            }
        });

        CardView cardViewExpense = findViewById(R.id.cardExpense);
        cardViewExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CollectiveFinanceActivity.this, AddEditCollectiveExpesnseActivity.class);
                startActivity(intent);
            }
        });

        TextView tvExpenseSum = findViewById(R.id.tvExpenseSum);
        TextView tvIncomeSum = findViewById(R.id.tvIncomeSum);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        tvExpenseSum.setText((String.valueOf(sharedPref.getFloat("expense", 0))) + " руб.");
        tvIncomeSum.setText((String.valueOf(sharedPref.getFloat("income", 0)))+ " руб.");

        userDao = AppDatabase.getInstance(this).userDao();
        loadAndShowUsers();
    }

    private void loadAndShowUsers() {
        userList = new ArrayList<User>();

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                userList = userDao.getAll();
                showUsers();
            }
        });
    }

    private void showUsers() {
        RecyclerView rvItems = findViewById(R.id.rv_users);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        UserAdapter adapter = new UserAdapter(userList);
        adapter.setOnClickListener(new UserAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(User user) {
                Intent intent = new Intent(CollectiveFinanceActivity.this, AddEditUserActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });

        rvItems.setAdapter(adapter);
        rvItems.setLayoutManager(linearLayoutManager);
        rvItems.setHasFixedSize(true);
        rvItems.setNestedScrollingEnabled(true);
    }
}