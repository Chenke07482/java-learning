package org.cxq.learn.samples.java8.stream;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public void streamOfCreate() {
        Product[] productsArr = new Product[]{
                new Product("create1", true, 1.0),
                new Product("create2", false, 2.0),
                new Product("create3", true, 3.0),
        };

        //Stream 静态方法 of()
        Stream<Product> stream = Stream.of(productsArr);

        //Collection 默认方法。。Stream()
        Stream<Product> stream1 = Arrays.asList(productsArr).stream();
    }


    public void streamOfTransfer() {
        Product[] productsArr = new Product[]{
                new Product("create1", true, 1.0),
                new Product("create2", false, 2.0),
                new Product("create3", true, 3.0),
                new Product("create1", true, 1.0),
                new Product("create4", true, 5.0),
        };

        //distinct
        System.out.println("================Distinct============================");
        Arrays.asList(productsArr).stream().distinct().forEach(System.out::println);

        //filter
        System.out.println("================Filter============================");
        Arrays.asList(productsArr).stream().filter(product -> !product.isOnSale()).forEach(System.out::println);

        //map   flatmap--
        System.out.println("================Map============================");
        Arrays.asList(productsArr).stream()
                .map(product -> product.getName() + "_" + product.isOnSale() + "_" + product.getPrice())
                .forEach(System.out::println);

        //limit  skip
        System.out.println("================Limit&Skip============================");
        Arrays.asList(productsArr).stream().skip(1).limit(2).forEach(System.out::println);
    }

    public void streamOfReduce1() {
        Product[] productsArr = new Product[]{
                new Product("create1", true, 1.0),
                new Product("create2", false, 2.0),
                new Product("create3", true, 3.0),
                new Product("create1", true, 1.0),
                new Product("create4", true, 5.0),
        };

//        第一个函数生成一个新的ArrayList实例；
//        第二个函数接受两个参数，第一个是前面生成的ArrayList对象，二个是stream中包含的元素，函数体就是把stream中的元素加入ArrayList对象中。第二个函数被反复调用直到原stream的元素被消费完毕；
//        第三个函数也是接受两个参数，这两个都是ArrayList类型的，函数体就是把第二个ArrayList全部加入到第一个中；

        //collect 通用聚合方法
        List<Product> products = Arrays.asList(productsArr)
                .stream()
                .filter(Product::isOnSale)
                .collect(() -> new ArrayList<Product>(),
                        (list, item) -> list.add(item),
                        (list1, list2) -> list1.addAll(list2));
        System.out.println("==================Collect===========================");
        products.stream().forEach(System.out::println);

        //Collectors  Java8提供
        List<Product> products1 = Arrays.asList(productsArr)
                .stream()
                .filter(Product::isOnSale)
                .collect(Collectors.toList());
        System.out.println("==================Collectors.toList===========================");
        products1.stream().forEach(System.out::println);


        Set<Product> products2 = Arrays.asList(productsArr)
                .stream()
                .collect(Collectors.toSet());
        System.out.println("==================Collectors.toSet===========================");
        products2.stream().forEach(System.out::println);

        //map操作，tomap groupingBy  partionBy
        Map<Boolean, List<Product>> productsMap = Arrays.asList(productsArr)
                .stream().collect(Collectors.groupingBy(Product::isOnSale));
        System.out.println("==================Collectors.tomap===========================");
        productsMap.entrySet().stream().map(entry -> entry.getKey() + "_" + entry.getValue().size()).forEach(System.out::println);

        String productsStr = Arrays.asList(productsArr).stream()
                .map(product -> product.getName() + "_" + product.isOnSale() + "_" + product.getPrice())
                .collect(Collectors.joining("||"));
        System.out.println("==================Collectors.toString===========================");
        System.out.println(productsStr);
    }


    public void streamOfReduce2() {
        Product[] productsArr = new Product[]{
                new Product("create1", true, 1.0),
                new Product("create2", false, 2.0),
                new Product("create3", true, 3.0),
                new Product("create1", true, 1.0),
                new Product("create4", true, 5.0),
        };
        //reduce  Optional<T> reduce(BinaryOperator<T> accumulator);
        double sumprice = Stream.of(productsArr)
                .map(Product::getPrice)
                .reduce((sum, item) -> sum + item).get();
        System.out.println("==================reduce======================");
        System.out.println(sumprice);

        double sumprice1 = Stream.of(productsArr)
                .mapToDouble(Product::getPrice).sum();
        System.out.println("==================DoubleStream.reduce==============");
        System.out.println(sumprice1);

        DoubleSummaryStatistics statistics = Stream.of(productsArr)
                .mapToDouble(Product::getPrice).summaryStatistics();
        System.out.println("==================DoubleSummaryStatistics==============");
        System.out.println(statistics);

        //搜索类的
        //anyMatch. 是否存在任何一个元素满足条件
        boolean isOnsale = Stream.of(productsArr).anyMatch(Product::isOnSale);
        System.out.println("==================anyMatch.isOnSale==============");
        System.out.println(isOnsale);

        //allMatch. 是否所有元素满足条件
        boolean isAllOnsale = Stream.of(productsArr).allMatch(Product::isOnSale);
        System.out.println("==================allMatch.isOnSale==============");
        System.out.println(isAllOnsale);

        //noneMatch. 是否所有元素不满足条件
        boolean isNotOnsale = Stream.of(productsArr).noneMatch(Product::isOnSale);
        System.out.println("==================noneMatch.isOnSale==============");
        System.out.println(isNotOnsale);

        //findFirst  查询满足条件流的第一个元素
        Product product = Stream.of(productsArr).filter(Product::isOnSale).findFirst().get();
        System.out.println("==================findFirst.isOnSale==============");
        System.out.println(product);

        //findAny  查询满足条件流的任何一个元素
        Product products = Stream.of(productsArr).filter(Product::isOnSale).findAny().get();
        System.out.println("==================findAny.isOnSale==============");
        System.out.println(products);

        //max  查询满足条件并查找最贵的元素---min
        Product product2 = Stream.of(productsArr)
                .filter(Product::isOnSale)
                .max(Comparator.comparingDouble(Product::getPrice)).get();
        System.out.println("==================max.isOnSale.price==============");
        System.out.println(product2);
    }


    public static void main(String[] args) {
        Streams streams = new Streams();
        streams.compare();

        streams.streamOfTransfer();
        streams.streamOfReduce1();
        streams.streamOfReduce2();
    }
}
