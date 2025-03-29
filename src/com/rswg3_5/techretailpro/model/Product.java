package com.rswg3_5.techretailpro.model;

public class Product {
    private String productName;
    private String productCategory;
    private double productPrice;
    private int productStockQuantity;
    private String productDescription;

    public Product() {
    }

    public Product(String productName, String productCategory, double productPrice, int productStockQuantity, String productDescription) {
        this.productName = productName;
        this.productCategory = productCategory;
        this.productPrice = productPrice;
        this.productStockQuantity = productStockQuantity;
        this.productDescription = productDescription;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public int getProductStockQuantity() {
        return productStockQuantity;
    }

    public void setProductStockQuantity(int productStockQuantity) {
        this.productStockQuantity = productStockQuantity;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    @Override
    public String toString() {
        return "Product{" + "productName=" + productName + ", productCategory=" + productCategory + ", productPrice=" + productPrice + ", productStockQuantity=" + productStockQuantity + ", productDescription=" + productDescription + '}';
    }
    
    public void addToCart() {
        
    }
    
    public void directOrder() {
        
    }
}
