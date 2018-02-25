package com.divyanshgoenka.headyassessment.android.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.divyanshgoenka.headyassessment.R;
import com.divyanshgoenka.headyassessment.pojo.Product;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductActivity extends AppCompatActivity {

    static final String CURRENT_PRODUCT = "CURRENT_PRODUCT";
    @BindView(R.id.product_image_view)
    ImageView productImageView;
    @BindView(R.id.product_text_view)
    TextView productTextView;
    private Product currentProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        ButterKnife.bind(this);
        Bundle bundleToUse = savedInstanceState == null ? getIntent().getExtras() : savedInstanceState;
        currentProduct = (Product) bundleToUse.getSerializable(ProductActivity.CURRENT_PRODUCT);
        setViews();
    }

    private void setViews() {
        productTextView.setText(currentProduct.getName());
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(ProductActivity.CURRENT_PRODUCT, currentProduct);
    }
}
