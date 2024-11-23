package com.c.collectivefinanceapp.ui.acitivities.add_edit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.c.collectivefinanceapp.R;
import com.c.collectivefinanceapp.ui.acitivities.CollectiveFinanceActivity;

public class AddEditCollectiveIncomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_collective_income);
        setTitle("Коллективные доходы");

        Button btnSave = findViewById(R.id.btnSave);
        EditText etAmount = findViewById(R.id.etAmount);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        etAmount.setText(String.valueOf(sharedPref.getFloat("income", 0)) + " руб.");

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPref.edit().putFloat("income", Float.parseFloat(etAmount.getText().toString())).apply();
                Intent intent = new Intent(AddEditCollectiveIncomeActivity.this, CollectiveFinanceActivity.class);
                startActivity(intent);
            }
        });
    }
}