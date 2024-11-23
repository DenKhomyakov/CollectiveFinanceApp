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
import com.c.collectivefinanceapp.data.dao.RegularPaymentDao;
import com.c.collectivefinanceapp.data.entities.Accumulation;
import com.c.collectivefinanceapp.data.entities.RegularPayment;
import com.c.collectivefinanceapp.ui.acitivities.add_edit.AddEditAccumulationActivity;
import com.c.collectivefinanceapp.ui.acitivities.add_edit.AddEditIncomeActivity;
import com.c.collectivefinanceapp.ui.acitivities.add_edit.AddEditRegularPaymentActivity;
import com.c.collectivefinanceapp.ui.adapters.RegularPaymentAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class RegularPaymentsActivity extends AppCompatActivity {

    private List<RegularPayment> regularPaymentList;

    private RegularPaymentDao regularPaymentDao;

    private TextView tvAll;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regular_payments);
        setTitle("Регулярные платежи");

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setSelectedItemId(R.id.bm_regular_payment);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.bm_statistics) {
                    Intent intent = new Intent(RegularPaymentsActivity.this, StatisticsActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    return true;
                } else if (id == R.id.bm_regular_payment) {
                    return true;
                } else if (id == R.id.bm_goal) {
                    Intent intent = new Intent(RegularPaymentsActivity.this, GoalActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    return true;
                } else if (id == R.id.bm_accumululation) {
                    Intent intent = new Intent(RegularPaymentsActivity.this, AccumulationActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    return true;
                } else if (id == R.id.bm_collective) {
                    Intent intent = new Intent(RegularPaymentsActivity.this, CollectiveFinanceActivity.class);
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
                Intent intent = new Intent(RegularPaymentsActivity.this, AddEditRegularPaymentActivity.class);
                startActivity(intent);
            }
        });

        tvAll = findViewById(R.id.tvAll);

        regularPaymentDao =  AppDatabase.getInstance(this).regularPaymentDao();
        loadAndShowRegularPayments();
    }

    private void loadAndShowRegularPayments(){
        regularPaymentList = new ArrayList<RegularPayment>();

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                regularPaymentList = regularPaymentDao.getAll();
                showRegularPayments();
            }
        });
    }

    private void showRegularPayments(){
        RecyclerView rvItems = findViewById(R.id.rv_regular_payments);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        RegularPaymentAdapter adapter = new RegularPaymentAdapter(regularPaymentList);

        double sum = 0;
        for (RegularPayment regularPayment : regularPaymentList) {
            sum += regularPayment.getAmount();
        }
        tvAll.setText(String.valueOf(sum) + " руб.");

        adapter.setOnClickListener(new RegularPaymentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RegularPayment regularPayment) {
                Intent intent = new Intent(RegularPaymentsActivity.this, AddEditRegularPaymentActivity.class);
                intent.putExtra("regularPayment", regularPayment);
                startActivity(intent);
            }
        });

        rvItems.setAdapter(adapter);
        rvItems.setLayoutManager(linearLayoutManager);
        rvItems.setHasFixedSize(true);
        rvItems.setNestedScrollingEnabled(true);
    }
}