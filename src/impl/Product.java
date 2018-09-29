package impl;

import interfaces.IProduct;

public class Product implements IProduct {

    private float promotion;

    private String name;

    @Override
    public float getPromotion() {
        return promotion;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setPromotion(float promotion) {
        this.promotion = promotion;
    }

    public Product(String name) {
        this.promotion = 0;
        this.name = name;
    }
}
