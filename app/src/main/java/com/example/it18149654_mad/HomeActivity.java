package com.example.it18149654_mad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import Database.DBHandler;

public class HomeActivity extends AppCompatActivity {

    private EditText et_uname, et_pwd;
    private Button btn_login, btn_register;

    private String uname, password;

    private DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        et_uname = findViewById(R.id.et_un_h);
        et_pwd = findViewById(R.id.et_pw_h);

        btn_login = findViewById(R.id.btn_login_h);
        btn_register = findViewById(R.id.btn_register_h);

        dbHandler = new DBHandler(this);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(et_uname.getText().toString().trim())) {
                    Toast.makeText(HomeActivity.this, "Please enter username", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(et_pwd.getText().toString().trim())) {
                    Toast.makeText(HomeActivity.this, "Please enter password", Toast.LENGTH_SHORT).show();
                } else {
                    uname = et_uname.getText().toString().trim();
                    password = et_pwd.getText().toString().trim();

                    boolean res = dbHandler.login(uname, password);

                    if (res) {
                        Toast.makeText(HomeActivity.this, "Successfully logged in", Toast.LENGTH_SHORT).show();
                        clearData();

                        Intent intent = new Intent(HomeActivity.this, EditProfileActivity.class);
                        intent.putExtra("uname", uname);
                        startActivity(intent);
                    } else {
                        Toast.makeText(HomeActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, ProfileManagementActivity.class);
                startActivity(intent);
            }
        });
    }

    public void clearData() {
        et_uname.setText("");
        et_pwd.setText("");
    }
}
