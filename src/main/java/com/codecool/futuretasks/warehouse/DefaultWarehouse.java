package com.codecool.futuretasks.warehouse;

import com.codecool.futuretasks.ProductIds;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class DefaultWarehouse implements Warehouse {

    private final List<ProductCounts> counts = List.of(
            new ProductCounts(ProductIds.IPHONE_12_ID, 12),
            new ProductCounts(ProductIds.IPHONE_11_ID, 24),
            new ProductCounts(ProductIds.GALAXY_S8_ID, 166)
    );

    @Override
    public ProductCounts getProductCountsForId(int id) {
        return counts
                .stream()
                .filter(pc -> pc.getId() == id)
                .findFirst()
                .orElseThrow();
    }

    @Override
    public List<ProductCounts> getAllProductCounts() {
        return counts;
    }

    @Override
    public CompletableFuture<ProductCounts> scheduleGettingProductCountsForId(int id) {
        return CompletableFuture.supplyAsync(() -> getProductCountsForId(id));
    }

    @Override
    public CompletableFuture<List<ProductCounts>> scheduleGettingAllProductCounts() {
        return CompletableFuture.completedFuture(getAllProductCounts());
    }
}
