package impl;

import interfaces.IProduct;

public class Product implements IProduct {

    //Define properties for the name and promotion multiplier
    private double promotion;
    private String name;

    @Override
    public double getPromotion() {
        return promotion;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setPromotion(double promotion) {
        //If the promotion parameter is more than 0 set the property to the value, otherwise throw an IllegalArgumentException
        if (promotion > 0){
            this.promotion = promotion;
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    //This constructor takes no promotion parameter so the property is set to 1
    public Product(String name) {
        this.promotion = 1;
        this.name = name;
    }

    //This constructor takes a promotion value as a parameter and so sets the promotion property to the parameter
    public Product(String name, double promotion) {
        //If the promotion parameter is more than 0 set the property to the value, otherwise throw an IllegalArgumentException
        if (promotion > 0){
            this.promotion = promotion;
            this.name = name;
        }
        else {
            throw new IllegalArgumentException();
        }
    }
}
