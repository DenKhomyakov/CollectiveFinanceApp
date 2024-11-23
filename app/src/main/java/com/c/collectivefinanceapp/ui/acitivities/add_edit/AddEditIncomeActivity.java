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
import com.c.collectivefinanceapp.data.dao.IncomeDao;
import com.c.collectivefinanceapp.data.entities.Income;
import com.c.collectivefinanceapp.ui.acitivities.IncomeActivity;

public class AddEditIncomeActivity extends AppCompatActivity {


    private EditText etTitle;
    private EditText etAmount;
    private Income income;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_income);

        income = getIntent().getParcelableExtra("income");


        etTitle = findViewById(R.id.etTitle);
        etAmount = findViewById(R.id.etAmount);
        Button btnSave = findViewById(R.id.btnSave);
        Button btnDelete = findViewById(R.id.btnDelete);

        if (income != null) {
            setTitle("Редактирование дохода");
            btnDelete.setVisibility(View.VISIBLE);
            etTitle.setText(income.getSource());
            etAmount.setText(String.valueOf(income.getAmount()));

        } else {
            setTitle("Добавление дохода");
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

            IncomeDao incomeDao = AppDatabase.getInstance(getApplication()).incomeDao();

            if (income != null) {
                income.setSource(title);
                income.setAmount(amount);
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        incomeDao.update(income);
                    }
                });
            } else {
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        incomeDao.insert(new Income(title, amount));
                    }
                });
            }

            Intent intent = new Intent(AddEditIncomeActivity.this, IncomeActivity.class);
            startActivity(intent);
        } catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void delete(){
        IncomeDao incomeDao = AppDatabase.getInstance(getApplication()).incomeDao();
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                incomeDao.delete(income);
            }
        });
        Intent intent = new Intent(AddEditIncomeActivity.this, IncomeActivity.class);
        startActivity(intent);
    }
}