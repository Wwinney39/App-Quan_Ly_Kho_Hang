package com.example.quan_ly_kho_hang.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quan_ly_kho_hang.R;
import com.example.quan_ly_kho_hang.database.DatabaseHelper;

public class LoginActivity extends AppCompatActivity {
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = new DatabaseHelper(this);
        EditText etUser = findViewById(R.id.etUsername);
        EditText etPass = findViewById(R.id.etPassword);
        TextView tvErr = findViewById(R.id.tvLoginError);
        Button btn = findViewById(R.id.btnLogin);

        btn.setOnClickListener(v -> {
            String u = etUser.getText().toString().trim();
            String p = etPass.getText().toString().trim();
            if (u.isEmpty() || p.isEmpty() || !db.checkLogin(u, p)) {
                tvErr.setVisibility(View.VISIBLE);
                tvErr.setText("Sai tên đăng nhập hoặc mật khẩu");
            } else {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }
        });
    }
}