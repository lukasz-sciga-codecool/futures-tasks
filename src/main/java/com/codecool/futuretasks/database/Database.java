package com.codecool.futuretasks.database;

import com.codecool.futuretasks.Product;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface Database {
    CompletableFuture<Product> scheduleGettingProductById(int id);
    CompletableFuture<List<Product>> scheduleGettingAllProducts();
}
