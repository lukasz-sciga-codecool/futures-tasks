package com.codecool.futuretasks.order;

import com.codecool.futuretasks.Product;

import java.util.concurrent.CompletableFuture;

public interface OrderingService {
    CompletableFuture<OrderingResult> order(Product product, int quantity);
}
