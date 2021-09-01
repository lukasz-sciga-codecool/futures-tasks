package com.codecool.futuretasks.database;

import com.codecool.futuretasks.Product;
import com.codecool.futuretasks.ProductIds;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class DefaultDatabase implements Database {

    private static final Product IPHONE_11 = new Product(ProductIds.IPHONE_11_ID, "iPhone 11", "iPhone");
    private static final Product IPHONE_12 = new Product(ProductIds.IPHONE_12_ID, "iPhone 12", "iPhone");
    private static final Product GALAXY_S8 = new Product(ProductIds.GALAXY_S8_ID, "Galaxy S8", "Samsung");

    private static final long HALF_SECOND = 500;
    private static final long SECOND = 1000;

    private final List<Product> products = List.of(IPHONE_11, IPHONE_12, GALAXY_S8);

    @Override
    public CompletableFuture<Product> scheduleGettingProductById(int id) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                // faking long blocking operations
                Thread.sleep(HALF_SECOND);
                return products.stream()
                        .filter(p -> p.getId() == id)
                        .findFirst()
                        .orElseThrow();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public CompletableFuture<List<Product>> scheduleGettingAllProducts() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                // faking long blocking operations
                Thread.sleep(SECOND);
                return products;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
