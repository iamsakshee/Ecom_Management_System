package com.springboot.ecom.dto;

import java.util.List;

import com.springboot.ecom.model.ProductImage;

public class ProductResponseDto {

    private int id;
    private String name;
    private Double price;
    private int stock;
    private String description;

    private String brand;
    private List<ProductImage> images;
    

    public ProductResponseDto(int id, String name, Double price, int stock) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.stock = stock;
	}

	public ProductResponseDto() {
    }

    public List<ProductImage> getImages() {
        return images;
    }

    public void setImages(List<ProductImage> images) {
        this.images = images;
    }

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

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
    
    

}