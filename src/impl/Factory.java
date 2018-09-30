package impl;

import interfaces.*;


/**
 * This class implements a singleton factory.
 *
 */
public final class Factory implements IFactory {

    private static IFactory factoryInstance = null;

    private Factory() {

    }

    /**
     * Method which returns an instance of the singleton Factory class.
     * @return the instance of the Factory
     */
    public static IFactory getInstance() {
        if (factoryInstance == null) {
            factoryInstance = new Factory();
        }
        return factoryInstance;
    }

    @Override
    public ILoyaltyCardOwner makeLoyaltyCardOwner(String email, String name) {
        //Create a new LoyaltyCardOwner called loyaltyCardOwner by calling the LoyaltyCardOwner constructor with the email and string parameters parsed as parameters
        LoyaltyCardOwner loyaltyCardOwner = new LoyaltyCardOwner(email, name);
        //Return the loyaltyCardOwner
        return loyaltyCardOwner;
    }

    @Override
    public ILoyaltyCard makeLoyaltyCard(ILoyaltyCardOwner loyaltyCardOwner) {
        //Create a new LoyaltyCard called loyaltyCard by calling the LoyaltyCard constructor with the ILoyaltyCard parameter parsed as a parameter
        LoyaltyCard loyaltyCard = new LoyaltyCard(loyaltyCardOwner);
        //Return the loyaltyCard
        return loyaltyCard;
    }

    @Override
    public ILoyaltyCardOperator makeLoyaltyCardOperator() {
        //Create a new LoyaldyCardOperator called loyaltyCardOperator by calling the LoyaltyCardOperator constructor
        LoyaltyCardOperator loyaltyCardOperator = new LoyaltyCardOperator();
        //Return the loyaltyCardOperator
        return loyaltyCardOperator;
    }

    @Override
    public IProduct makeProduct(String name) {
        //Create a new product object called product by calling the product constructor with name as a parameter
        Product product = new Product(name);
        //Return the product object
        return product;
    }

    @Override
    public IProduct makeProduct(String name, double promotion) {
        //Create a new product object called product by calling the product constructor with name and promotion parsed as parameters
        Product product = new Product(name, promotion);
        //Return the product object
        return product;
    }
}
