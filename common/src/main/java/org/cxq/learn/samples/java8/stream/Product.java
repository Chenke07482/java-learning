package org.cxq.learn.samples.java8.stream;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by cxq on 2017/6/13.
 */
public class Product {
    private String name;
    private boolean isOnSale = false;
    private Double price = 0.0;

    public Product(String name, boolean isOnSale, Double price) {
        this.name = name;
        this.isOnSale = isOnSale;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOnSale() {
        return isOnSale;
    }

    public void setOnSale(boolean onSale) {
        isOnSale = onSale;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return ((Product) obj).getName().equalsIgnoreCase(this.name);
    }

    @Override
    public String toString() {
        return "{name=" + name + ",onSale=" + isOnSale + ",price=" + price + "}";
    }

    static List<Product> genarateList(int num) {
        List<Product> products = new ArrayList<>();
        for (int i = 1; i <= num; i++) {
            products.add(new Product("product_" + i, i % 3 != 0, 1.2 * i));
        }
        return products;
    }
}
