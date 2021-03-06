package com.example.evaconnolly.electronicsstore.Objects;

public class Product {

    public String title;
    public String manufacturer;
    public String price;
    public String imageUrl;
    public String quantity;
    public String category;

    public Product(){
    }

    public Product( String title, String manufacturer, String price, String imageUrl, String quantity, String category){
        this.title = title;
        this.manufacturer = manufacturer;
        this.price = price;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
        this.category = category;
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
