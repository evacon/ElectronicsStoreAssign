package com.example.evaconnolly.electronicsstore.Objects;

public class Product {

    private String id;
    private String title;
    private String manufacturer;
    private int price;
    private String imageUrl;
    private int quantity;

    public Product(){
    }

    public Product(String id, String title, String manufacturer, int price, String imageUrl, int quantity){
        this.id = id;
        this.title = title;
        this.manufacturer = manufacturer;
        this.price = price;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }



}
