package com.example.evaconnolly.electronicsstore.Objects;

public class Product {

    private String id;
    private String title;
    private String manufacturer;
    private String price;
    private String imageUrl;
    private String quantity;
    private String category;

    public Product(){
    }

    public Product(String id, String title, String manufacturer, String price, String imageUrl, String quantity){
        this.id = id;
        this.title = title;
        this.manufacturer = manufacturer;
        this.price = price;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
        this.category = category;
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

    public String  getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getCatgeory(){
        return category;}

    public void setCategory(){
        this.category = category;
    }



}
