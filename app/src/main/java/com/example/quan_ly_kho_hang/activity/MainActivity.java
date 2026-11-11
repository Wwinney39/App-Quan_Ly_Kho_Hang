package com.example.quan_ly_kho_hang.activity;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quan_ly_kho_hang.R;
import com.example.quan_ly_kho_hang.adapter.ProductAdapter;
import com.example.quan_ly_kho_hang.database.DatabaseHelper;
import com.example.quan_ly_kho_hang.model.Product;

import java.text.DecimalFormat;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DatabaseHelper db;
    private ListView lvProducts;
    private TextView tvTotal, tvValue, tvLow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(this);
        lvProducts = findViewById(R.id.lvProducts);
        tvTotal = findViewById(R.id.tvTotalProducts);
        tvValue = findViewById(R.id.tvTotalValue);
        tvLow = findViewById(R.id.tvLowStock);

        findViewById(R.id.btnAdd).setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, AddProductActivity.class))
        );

        lvProducts.setOnItemClickListener((parent, view, position, id) -> {
            Product p = (Product) lvProducts.getItemAtPosition(position);
            Intent intent = new Intent(MainActivity.this, ProductDetailActivity.class);
            intent.putExtra("id", p.getId());
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<Product> list = db.getAllProducts();
        lvProducts.setAdapter(new ProductAdapter(this, list));
        tvTotal.setText(list.size() + "\nSản phẩm");
        tvValue.setText(new DecimalFormat("#,###").format(db.getTotalValue()) + " đ\nGiá trị kho");
        tvLow.setText(db.countLowStock() + "\nSắp hết hàng");
    }
}