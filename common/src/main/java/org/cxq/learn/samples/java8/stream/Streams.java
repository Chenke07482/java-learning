package org.cxq.learn.samples.java8.stream;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by cxq on 2017/6/8.
 * Java8特性Stream 测试例子
 */
public class Streams {

    //最昂贵的在售商品前十位
    public List<Product> getProductsByJava7(List<Product> products) {
        //找出在售的
        List<Product> onSaledProducts = new ArrayList<>();
        for (Product product : products) {
            if (product.isOnSale()) {
                onSaledProducts.add(product);
            }
        }

        //按照价格高低排序
        Collections.sort(onSaledProducts, new Comparator<Product>() {
            @Override
            public int compare(Product t1, Product t2) {
                return t2.getPrice().compareTo(t1.getPrice());
            }
        });

        //输出前十位
        List<Product> result = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            result.add(onSaledProducts.get(i));
        }
        return result;
    }

    public List<Product> getProductsByJava8(List<Product> products) {
        return products.stream()
                .filter(Product::isOnSale)//获取在售商品
                .sorted(Comparator.comparingDouble(Product::getPrice).reversed())//按价格排序
                .limit(10)//获取前十
                .collect(Collectors.toList());
    }

    public void compare() {
        List<Product> products = Product.genarateList(100);
        List<Product> java7Products = getProductsByJava7(products);
        System.out.println("======================java7 get products=========================");
        java7Products.forEach(System.out::println);
        List<Product> java8Products = getProductsByJava8(products);
        System.out.println("======================java8 get products=========================");
        java8Products.forEach(System.out::println);
    }

    public static void main(String[] args) {
        Streams streams = new Streams();
        streams.compare();
    }
}
