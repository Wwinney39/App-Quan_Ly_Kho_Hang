package com.example.quan_ly_kho_hang.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.quan_ly_kho_hang.R;
import com.example.quan_ly_kho_hang.model.Product;

import java.text.DecimalFormat;
import java.util.List;

public class ProductAdapter extends ArrayAdapter<Product> {
    public ProductAdapter(Context context, List<Product> products) {
        super(context, 0, products);
    }

    @Override
    public View getView(int pos, View cv, ViewGroup parent) {
        if (cv == null) {
            cv = LayoutInflater.from(getContext()).inflate(R.layout.item_product, parent, false);
        }
        Product p = getItem(pos);
        TextView tvName = cv.findViewById(R.id.tvName);
        TextView tvPrice = cv.findViewById(R.id.tvPrice);
        TextView tvCategory = cv.findViewById(R.id.tvCategory);
        TextView tvQuantity = cv.findViewById(R.id.tvQuantity);
        TextView tvWarning = cv.findViewById(R.id.tvWarning);

        tvName.setText(p.getName());
        tvCategory.setText(p.getCategory());
        tvPrice.setText("Giá bán: " + new DecimalFormat("#,###").format(p.getPrice()) + " đ");
        tvQuantity.setText("SL: " + p.getQuantity());

        if (p.getQuantity() < 5) {
            tvWarning.setVisibility(View.VISIBLE);
            tvQuantity.setTextColor(Color.RED);
        } else {
            tvWarning.setVisibility(View.GONE);
            tvQuantity.setTextColor(Color.BLACK);
        }
        return cv;
    }
}