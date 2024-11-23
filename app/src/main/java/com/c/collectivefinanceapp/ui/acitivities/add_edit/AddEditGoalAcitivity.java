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
import com.c.collectivefinanceapp.data.dao.GoalDao;
import com.c.collectivefinanceapp.data.dao.IncomeDao;
import com.c.collectivefinanceapp.data.entities.Goal;
import com.c.collectivefinanceapp.data.entities.Income;
import com.c.collectivefinanceapp.ui.acitivities.GoalActivity;
import com.c.collectivefinanceapp.ui.acitivities.IncomeActivity;

public class AddEditGoalAcitivity extends AppCompatActivity {

    private EditText etTitle;
    private EditText etAmount;
    private Goal goal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_goal_acitivity);

        goal = getIntent().getParcelableExtra("goal");


        etTitle = findViewById(R.id.etTitle);
        etAmount = findViewById(R.id.etAmount);
        Button btnSave = findViewById(R.id.btnSave);
        Button btnDelete = findViewById(R.id.btnDelete);

        if (goal != null) {
            setTitle("Редактирование цели");
            btnDelete.setVisibility(View.VISIBLE);
            etTitle.setText(goal.getName());
            etAmount.setText(String.valueOf(goal.getAmount()));

        } else {
            setTitle("Добавление цели");
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

            GoalDao goalDao = AppDatabase.getInstance(getApplication()).goalDao();

            if (goal != null) {
                goal.setName(title);
                goal.setAmount(amount);
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        goalDao.update(goal);
                    }
                });
            } else {
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        goalDao.insert(new Goal(title, amount));
                    }
                });
            }

            Intent intent = new Intent(AddEditGoalAcitivity.this, GoalActivity.class);
            startActivity(intent);
        } catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void delete(){
        GoalDao goalDao = AppDatabase.getInstance(getApplication()).goalDao();
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                goalDao.delete(goal);
            }
        });
        Intent intent = new Intent(AddEditGoalAcitivity.this, GoalActivity.class);
        startActivity(intent);
    }
}