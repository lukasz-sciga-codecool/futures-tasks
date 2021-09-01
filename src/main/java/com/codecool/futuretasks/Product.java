package com.codecool.futuretasks;

import java.util.Objects;

public class Product {
    private final int id;
    private final String name;
    private final String brand;

    public Product(int id, String name, String brand) {
        this.id = id;
        this.name = name;
        this.brand = brand;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBrand() {
        return brand;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id && Objects.equals(name, product.name) && Objects.equals(brand, product.brand);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, brand);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                '}';
    }
}
