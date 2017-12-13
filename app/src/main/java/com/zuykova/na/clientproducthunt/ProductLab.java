package com.zuykova.na.clientproducthunt;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class ProductLab {
    private static ProductLab sProductLab;

    private List<Product> mProducts;

    public static ProductLab get(Context context) {
        if (sProductLab == null) {
            sProductLab = new ProductLab(context);
        }
        return sProductLab;
    }

    private ProductLab(Context context){
        mProducts = new ArrayList<>();

        for (int i = 0; i < 15; i++) {
            Product product = new Product();
            product.setTitle("Продукт" + i);
            mProducts.add(product);
        }

    }

    public List<Product> getProducts() {
        return mProducts;
    }

    public Product getProduct(int id) {
        for (Product product : mProducts) {
            if (product.getId() == id) {
                return product;
            }
        }
        return null;
    }
}
