package com.example.quan_ly_kho_hang.activity;


import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quan_ly_kho_hang.R;
import com.example.quan_ly_kho_hang.database.DatabaseHelper;
import com.example.quan_ly_kho_hang.model.Product;

public class AddProductActivity extends AppCompatActivity {
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        db = new DatabaseHelper(this);
        EditText etName = findViewById(R.id.etName);
        EditText etPrice = findViewById(R.id.etPrice);
        EditText etQty = findViewById(R.id.etQty);
        EditText etDesc = findViewById(R.id.etDesc);
        Spinner spCat = findViewById(R.id.spCat);
        Switch swActive = findViewById(R.id.swActive);

        String[] cats = {"Điện tử", "Thực phẩm", "Văn phòng phẩm", "Gia dụng", "Khác"};
        spCat.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, cats));

        findViewById(R.id.btnSave).setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String pStr = etPrice.getText().toString().trim();
            String qStr = etQty.getText().toString().trim();

            if (name.isEmpty()) { etName.setError("Không để trống"); return; }
            if (pStr.isEmpty() || Integer.parseInt(pStr) <= 0) { etPrice.setError("Không hợp lệ"); return; }
            if (qStr.isEmpty() || Integer.parseInt(qStr) < 0) { etQty.setError("Không hợp lệ"); return; }

            db.insertProduct(new Product(0, name, Integer.parseInt(pStr), Integer.parseInt(qStr),
                    spCat.getSelectedItem().toString(), swActive.isChecked(), etDesc.getText().toString().trim()));
            finish();
        });
    }
}