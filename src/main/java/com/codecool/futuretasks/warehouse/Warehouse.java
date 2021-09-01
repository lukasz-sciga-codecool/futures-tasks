package com.codecool.futuretasks.warehouse;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface Warehouse {
    ProductCounts getProductCountsForId(final int id);
    List<ProductCounts> getAllProductCounts();
    CompletableFuture<ProductCounts> scheduleGettingProductCountsForId(final int id);
    CompletableFuture<List<ProductCounts>> scheduleGettingAllProductCounts();
}
