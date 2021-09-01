package com.codecool.futuretasks.warehouse;

import java.util.Objects;

public class ProductCounts {
    private final int id;
    private final int count;

    public ProductCounts(int id, int count) {
        this.id = id;
        this.count = count;
    }

    public int getId() {
        return id;
    }

    public int getCount() {
        return count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductCounts that = (ProductCounts) o;
        return id == that.id && count == that.count;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, count);
    }
}
