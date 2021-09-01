package com.codecool.futuretasks.order;

import java.util.Objects;

public class OrderingResult {
    private final boolean success;

    public OrderingResult(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderingResult that = (OrderingResult) o;
        return success == that.success;
    }

    @Override
    public int hashCode() {
        return Objects.hash(success);
    }
}
