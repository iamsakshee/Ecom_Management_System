package com.springboot.ecom.model;

import com.springboot.ecom.enums.FeaturedRequest;
import jakarta.persistence.*;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

    @Column(length = 500)
    private String description;

    private double price;

    private int stock;
    @ManyToOne
    private User user;

    @ManyToOne
    private Category category;
    @Enumerated(EnumType.STRING)
    private FeaturedRequest featuredRequest;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public FeaturedRequest getFeaturedRequest() {
        return featuredRequest;
    }

    public void setFeaturedRequest(FeaturedRequest featuredRequest) {
        this.featuredRequest = featuredRequest;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

}
