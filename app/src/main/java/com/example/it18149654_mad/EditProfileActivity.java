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

import java.util.List;

import Database.DBHandler;

public class EditProfileActivity extends AppCompatActivity {

    private EditText et_uname, et_pwd, et_dob;
    private Button btn_edit, btn_delete, btn_search;
    private RadioGroup rg_gender;
    private RadioButton rb_male, rb_female;

    private String uname, password, dob, gender;

    private DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        et_uname = findViewById(R.id.et_un_e);
        et_pwd = findViewById(R.id.et_pw_e);
        et_dob = findViewById(R.id.et_dob_e);

        rg_gender = findViewById(R.id.rg_e);

        rb_male = findViewById(R.id.rb_male_e);
        rb_female = findViewById(R.id.rb_female_e);

        btn_edit = findViewById(R.id.btn_edit_e);
        btn_delete = findViewById(R.id.btn_del_e);
        btn_search = findViewById(R.id.btn_search_e);;

        dbHandler = new DBHandler(this);

        String u = getIntent().getStringExtra("uname");

        List<String> list = dbHandler.readAllInfo(u);

        if (list.isEmpty()) {
            Toast.makeText(EditProfileActivity.this, "No user", Toast.LENGTH_SHORT).show();
        } else {
            String username1, dob1, password1, gender1;

            username1 = list.get(1);
            password1 = list.get(2);
            dob1 = list.get(3);
            gender1 = list.get(4);

            et_uname.setText(username1);
            et_pwd.setText(password1);
            et_dob.setText(dob1);

            if (gender1.equals("Male")) {
                rb_male.setChecked(true);
            } else if (gender1.equals("Female")){
                rb_female.setChecked(true);
            }
        }

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(et_uname.getText().toString().trim())) {
                    Toast.makeText(EditProfileActivity.this, "Please enter username", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(et_pwd.getText().toString().trim())) {
                    Toast.makeText(EditProfileActivity.this, "Please enter password", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(et_dob.getText().toString().trim())) {
                    Toast.makeText(EditProfileActivity.this, "Please enter date of birth", Toast.LENGTH_SHORT).show();
                } else if (!rb_male.isChecked() && !rb_female.isChecked()) {
                    Toast.makeText(EditProfileActivity.this, "Please enter gender", Toast.LENGTH_SHORT).show();
                } else {
                    uname = et_uname.getText().toString().trim();
                    password = et_pwd.getText().toString().trim();
                    dob = et_dob.getText().toString().trim();

                    if (rb_male.isChecked()){
                        gender = "Male";
                    } else {
                        gender = "Female";
                    }

                    int res = dbHandler.updateInfo(uname, password, dob, gender);

                    if (res == -1) {
                        Toast.makeText(EditProfileActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(EditProfileActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                        clearData();

                        Intent intent = new Intent(EditProfileActivity.this, HomeActivity.class);
                        startActivity(intent);
                    }
                }
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(et_uname.getText().toString().trim())) {
                    Toast.makeText(EditProfileActivity.this, "Please enter username", Toast.LENGTH_SHORT).show();
                }
                else {
                    uname = et_uname.getText().toString().trim();

                    int res = dbHandler.deleteInfo(uname);

                    if (res == 1) {
                        Toast.makeText(EditProfileActivity.this, "Successfully deleted", Toast.LENGTH_SHORT).show();
                        clearData();
                    } else {
                        Toast.makeText(EditProfileActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        btn_search.setOnClickListener(new View.OnClickListener() {
            String username, dob, password, gender;

            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(et_uname.getText().toString().trim())) {
                    Toast.makeText(EditProfileActivity.this, "Please enter username", Toast.LENGTH_SHORT).show();
                } else {
                    uname = et_uname.getText().toString().trim();

                    List<String> list = dbHandler.readAllInfo(uname);

                    if (list.isEmpty()) {
                        Toast.makeText(EditProfileActivity.this, "No user", Toast.LENGTH_SHORT).show();
                    } else {
                        username = list.get(1);
                        password = list.get(2);
                        dob = list.get(3);
                        gender = list.get(4);

                        et_uname.setText(username);
                        et_pwd.setText(password);
                        et_dob.setText(dob);

                        if (gender.equals("Male")) {
                            rb_male.setChecked(true);
                        } else if (gender.equals("Female")){
                            rb_female.setChecked(true);
                        }
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
