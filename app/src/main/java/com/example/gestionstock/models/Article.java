package com.example.gestionstock.models;

import java.io.Serializable;

public class Article implements Serializable {

    private int id;
    private String label;
    private double price;
    private Category category;

    public Article() {
    }

    public Article(int id, String label, double price, Category category
                    ) {
        this.id = id;
        this.label = label;
        this.price = price;
        this.category = category;
    }

    public Article(String label, double price, Category category
    ) {
        this.label = label;
        this.price = price;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory_id(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", label='" + label + '\'' +
                ", price=" + price +
                '}';
    }
}
