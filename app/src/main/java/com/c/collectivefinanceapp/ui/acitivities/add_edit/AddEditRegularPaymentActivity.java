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
import com.c.collectivefinanceapp.data.dao.RegularPaymentDao;
import com.c.collectivefinanceapp.data.entities.Income;
import com.c.collectivefinanceapp.data.entities.RegularPayment;
import com.c.collectivefinanceapp.ui.acitivities.IncomeActivity;
import com.c.collectivefinanceapp.ui.acitivities.RegularPaymentsActivity;

public class AddEditRegularPaymentActivity extends AppCompatActivity {

    private EditText etTitle;
    private EditText etAmount;

    private RegularPayment regularPayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_regular_payment);

        regularPayment = getIntent().getParcelableExtra("regularPayment");

        etTitle = findViewById(R.id.etTitle);
        etAmount = findViewById(R.id.etAmount);
        Button btnSave = findViewById(R.id.btnSave);
        Button btnDelete = findViewById(R.id.btnDelete);

        if (regularPayment != null) {
            setTitle("Редактирование регулярного платежа");
            btnDelete.setVisibility(View.VISIBLE);
            etTitle.setText(regularPayment.getName());
            etAmount.setText(String.valueOf(regularPayment.getAmount()));

        } else {
            setTitle("Добавление регулярного платежа");
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

            RegularPaymentDao regularPaymentDao = AppDatabase.getInstance(getApplication()).regularPaymentDao();

            if (regularPayment != null) {
                regularPayment.setName(title);
                regularPayment.setAmount(amount);
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        regularPaymentDao.update(regularPayment);
                    }
                });
            } else {
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        regularPaymentDao.insert(new RegularPayment(title, amount));
                    }
                });
            }

            Intent intent = new Intent(AddEditRegularPaymentActivity.this, RegularPaymentsActivity.class);
            startActivity(intent);
        } catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void delete(){
        RegularPaymentDao regularPaymentDao = AppDatabase.getInstance(getApplication()).regularPaymentDao();
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                regularPaymentDao.delete(regularPayment);
            }
        });
        Intent intent = new Intent(AddEditRegularPaymentActivity.this, RegularPaymentsActivity.class);
        startActivity(intent);
    }
}