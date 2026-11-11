package com.example.quan_ly_kho_hang.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quan_ly_kho_hang.R;
import com.example.quan_ly_kho_hang.database.DatabaseHelper;
import com.example.quan_ly_kho_hang.model.Product;

import java.text.DecimalFormat;

public class ProductDetailActivity extends AppCompatActivity {
    private DatabaseHelper db;
    private Product p;
    private int pid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        db = new DatabaseHelper(this);
        pid = getIntent().getIntExtra("id", -1);

        findViewById(R.id.btnAdd1).setOnClickListener(v -> {
            db.updateQuantity(pid, p.getQuantity() + 1);
            loadData();
        });

        findViewById(R.id.btnSell1).setOnClickListener(v -> {
            if(p.getQuantity() > 0) {
                db.updateQuantity(pid, p.getQuantity() - 1);
                loadData();
            } else {
                Toast.makeText(this, "Hết hàng", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.btnDel).setOnClickListener(v -> {
            db.deleteProduct(pid);
            finish();
        });

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        p = db.getProductById(pid);
        if (p == null) return;

        DecimalFormat df = new DecimalFormat("#,###");
        ((TextView)findViewById(R.id.tvDName)).setText(p.getName());
        ((TextView)findViewById(R.id.tvDCat)).setText(p.getCategory());
        ((TextView)findViewById(R.id.tvDPrice)).setText(df.format(p.getPrice()) + " đ");
        ((TextView)findViewById(R.id.tvDQty)).setText(String.valueOf(p.getQuantity()));
        ((TextView)findViewById(R.id.tvDVal)).setText(df.format((long)p.getPrice() * p.getQuantity()) + " đ");
        ((TextView)findViewById(R.id.tvDDesc)).setText(p.getDescription());

        TextView warn = findViewById(R.id.tvDWarn);
        warn.setVisibility(p.getQuantity() < 5 ? View.VISIBLE : View.GONE);
    }
}