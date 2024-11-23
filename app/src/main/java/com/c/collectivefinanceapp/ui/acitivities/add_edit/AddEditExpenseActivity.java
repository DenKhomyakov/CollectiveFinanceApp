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
import com.c.collectivefinanceapp.data.dao.ExpenseDao;
import com.c.collectivefinanceapp.data.dao.IncomeDao;
import com.c.collectivefinanceapp.data.entities.Expense;
import com.c.collectivefinanceapp.data.entities.Income;
import com.c.collectivefinanceapp.ui.acitivities.ExpenseActivity;
import com.c.collectivefinanceapp.ui.acitivities.IncomeActivity;

public class AddEditExpenseActivity extends AppCompatActivity {

    private EditText etTitle;
    private EditText etAmount;
    private Expense expense;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_expense);

        expense = getIntent().getParcelableExtra("expense");

        etTitle = findViewById(R.id.etTitle);
        etAmount = findViewById(R.id.etAmount);
        Button btnSave = findViewById(R.id.btnSave);
        Button btnDelete = findViewById(R.id.btnDelete);

        if (expense != null) {
            setTitle("Редактирование расхода");
            btnDelete.setVisibility(View.VISIBLE);
            etTitle.setText(expense.getSource());
            etAmount.setText(String.valueOf(expense.getAmount()));

        } else {
            setTitle("Добавление расхода");
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

    private void save() {
        try {
            if (etTitle.equals("")) {
                throw new IllegalArgumentException("Необходимо указать название!");
            }
            if (etAmount.getText().toString().equals("")) {
                throw new IllegalArgumentException("Необходимо ввести сумму!");
            }
            String title = etTitle.getText().toString();
            double amount = Double.parseDouble(etAmount.getText().toString());

            ExpenseDao expenseDao = AppDatabase.getInstance(getApplication()).expenseDao();

            if (expense != null) {
                expense.setSource(title);
                expense.setAmount(amount);
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        expenseDao.update(expense);
                    }
                });
            } else {
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        expenseDao.insert(new Expense(title, amount));
                    }
                });
            }

            Intent intent = new Intent(AddEditExpenseActivity.this, ExpenseActivity.class);
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void delete() {
        ExpenseDao expenseDao = AppDatabase.getInstance(getApplication()).expenseDao();
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                expenseDao.delete(expense);
            }
        });
        Intent intent = new Intent(AddEditExpenseActivity.this, ExpenseActivity.class);
        startActivity(intent);
    }
}