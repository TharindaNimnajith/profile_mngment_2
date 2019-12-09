package com.example.it18149654_mad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import Database.DBHandler;

public class ProfileManagementActivity extends AppCompatActivity {

    private EditText et_uname, et_pwd, et_dob;
    private Button btn_update_profile;
    private RadioGroup rg_gender;
    private RadioButton rb_male, rb_female;

    private String uname, password, dob, gender;

    private DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_management);

        et_uname = findViewById(R.id.et_un_p);
        et_pwd = findViewById(R.id.et_pw_p);
        et_dob = findViewById(R.id.et_dob_p);

        rg_gender = findViewById(R.id.rg);

        rb_male = findViewById(R.id.rb_male);
        rb_female = findViewById(R.id.rb_female);

        btn_update_profile = findViewById(R.id.btn_up_p);

        dbHandler = new DBHandler(this);

        btn_update_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(et_uname.getText().toString().trim())) {
                    Toast.makeText(ProfileManagementActivity.this, "Please enter username", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(et_pwd.getText().toString().trim())) {
                    Toast.makeText(ProfileManagementActivity.this, "Please enter password", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(et_dob.getText().toString().trim())) {
                    Toast.makeText(ProfileManagementActivity.this, "Please enter date of birth", Toast.LENGTH_SHORT).show();
                } else if (!rb_male.isChecked() && !rb_female.isChecked()) {
                    Toast.makeText(ProfileManagementActivity.this, "Please enter gender", Toast.LENGTH_SHORT).show();
                } else {
                    uname = et_uname.getText().toString().trim();
                    password = et_pwd.getText().toString().trim();
                    dob = et_dob.getText().toString().trim();

                    if (rb_male.isChecked()){
                        gender = "Male";
                    } else {
                        gender = "Female";
                    }

                    long res = dbHandler.addInfo(uname, password, dob, gender);

                    if (res == -1) {
                        Toast.makeText(ProfileManagementActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ProfileManagementActivity.this, "Inserted", Toast.LENGTH_SHORT).show();
                        clearData();

                        Intent intent = new Intent(ProfileManagementActivity.this, HomeActivity.class);
                        startActivity(intent);
                    }
                }
            }
        });
    }

    public void clearData() {
        et_uname.setText("");
        et_pwd.setText("");
        et_dob.setText("");

        rb_male.setChecked(false);
        rb_female.setChecked(false);
    }
}
