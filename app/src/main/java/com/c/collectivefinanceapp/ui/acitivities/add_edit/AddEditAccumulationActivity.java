package com.c.collectivefinanceapp.ui.acitivities.add_edit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.c.collectivefinanceapp.R;
import com.c.collectivefinanceapp.data.AppDatabase;
import com.c.collectivefinanceapp.data.dao.AccumulationDao;
import com.c.collectivefinanceapp.data.dao.GoalDao;
import com.c.collectivefinanceapp.data.dao.IncomeDao;
import com.c.collectivefinanceapp.data.entities.Accumulation;
import com.c.collectivefinanceapp.data.entities.Income;
import com.c.collectivefinanceapp.ui.acitivities.AccumulationActivity;
import com.c.collectivefinanceapp.ui.acitivities.IncomeActivity;

public class AddEditAccumulationActivity extends AppCompatActivity {

    private EditText etTitle;
    private EditText etAmount;

    private Accumulation accumulation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_accumulation);

        accumulation = getIntent().getParcelableExtra("accumulation");

        etTitle = findViewById(R.id.etTitle);
        etAmount = findViewById(R.id.etAmount);
        Button btnSave = findViewById(R.id.btnSave);
        Button btnDelete = findViewById(R.id.btnDelete);

        if (accumulation != null) {
            setTitle("Редактирование накопления");
            btnDelete.setVisibility(View.VISIBLE);
            etTitle.setText(accumulation.getName());
            etAmount.setText(String.valueOf(accumulation.getAmount()));

        } else {
            setTitle("Добавление накопления");
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete();
            }
        });
    }

    private void save(){
        try {
            if (etTitle.equals("")) {
                throw new IllegalArgumentException("Необходимо указать название!");
            }
            if (etAmount.getText().toString().equals("")) {
                throw new IllegalArgumentException("Необходимо ввести сумму!");
            }
            String title = etTitle.getText().toString();
            double amount = Double.parseDouble(etAmount.getText().toString());

            AccumulationDao accumulationDao = AppDatabase.getInstance(getApplication()).accumulationDao();

            if (accumulation != null) {
                accumulation.setName(title);
                accumulation.setAmount(amount);
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        accumulationDao.update(accumulation);
                    }
                });
            } else {
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        accumulationDao.insert(new Accumulation(title, amount));
                    }
                });
            }

            Intent intent = new Intent(AddEditAccumulationActivity.this, AccumulationActivity.class);
            startActivity(intent);
        } catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void delete(){
        AccumulationDao accumulationDao = AppDatabase.getInstance(getApplication()).accumulationDao();
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                accumulationDao.delete(accumulation);
            }
        });
        Intent intent = new Intent(AddEditAccumulationActivity.this, AccumulationActivity.class);
        startActivity(intent);
    }
}