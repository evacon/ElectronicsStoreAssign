package com.example.evaconnolly.electronicsstore.Objects;

public class ShoppingCart {

    private String id;
    private String title;
    private String manufacturer;
    private String price;
    private String quantity;

    public ShoppingCart(String productTitle, String productManufacturer, String productPrice, String productQuntity, String productTotal){
    }

    public ShoppingCart(String id, String title, String manufacturer, String price, String quantity, String total){
        this.id = id;
        this.title = title;
        this.manufacturer = manufacturer;
        this.price = price;
        this.quantity = quantity;
        this.total = total;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    private String total;

}
