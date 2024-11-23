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
import com.c.collectivefinanceapp.data.dao.UserDao;
import com.c.collectivefinanceapp.data.entities.Income;
import com.c.collectivefinanceapp.data.entities.User;
import com.c.collectivefinanceapp.ui.acitivities.CollectiveFinanceActivity;
import com.c.collectivefinanceapp.ui.acitivities.IncomeActivity;

public class AddEditUserActivity extends AppCompatActivity {

    private EditText etTitle;
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_user);

        user = getIntent().getParcelableExtra("user");

        etTitle = findViewById(R.id.etTitle);
        Button btnSave = findViewById(R.id.btnSave);
        Button btnDelete = findViewById(R.id.btnDelete);

        if (user != null) {
            setTitle("Редактирование пользователя");
            btnDelete.setVisibility(View.VISIBLE);
            etTitle.setText(user.getName());

        } else {
            setTitle("Добавление пользователя");
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
                throw new IllegalArgumentException("Необходимо указать имя!");
            }
            String title = etTitle.getText().toString();

            UserDao userDao = AppDatabase.getInstance(getApplication()).userDao();

            if (user != null) {
                user.setName(title);
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        userDao.update(user);
                    }
                });
            } else {
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        userDao.insert(new User(title));
                    }
                });
            }

            Intent intent = new Intent(AddEditUserActivity.this, CollectiveFinanceActivity.class);
            startActivity(intent);
        } catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void delete(){
        UserDao userDao = AppDatabase.getInstance(getApplication()).userDao();
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                userDao.delete(user);
            }
        });
        Intent intent = new Intent(AddEditUserActivity.this, CollectiveFinanceActivity.class);
        startActivity(intent);
    }
}