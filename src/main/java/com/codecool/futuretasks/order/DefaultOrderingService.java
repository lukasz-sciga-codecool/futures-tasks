package com.codecool.futuretasks.order;

import com.codecool.futuretasks.Product;

import java.util.concurrent.CompletableFuture;

public class DefaultOrderingService implements OrderingService {

    public static final OrderingResult SUCCESS = new OrderingResult(true);

    @Override
    public CompletableFuture<OrderingResult> order(final Product product, final int quantity) {
        return CompletableFuture.completedFuture(SUCCESS);
    }
}
