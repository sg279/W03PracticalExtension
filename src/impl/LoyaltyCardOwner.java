package impl;

import interfaces.ILoyaltyCardOwner;

/**
 * This class represents loyalty card owners.
 *
 */
public class LoyaltyCardOwner implements ILoyaltyCardOwner {

    //Create two string properties called email and name
    private String email;
    private String name;

    public LoyaltyCardOwner(String email, String name) {
        //Set the email and name properties to the email and name parameters
        this.email = email;
        this.name = name;
    }

    @Override
    public String getEmail() {
        //Return the email property
        return this.email;
    }

    @Override
    public String getName() {
        //Return the name property
        return this.name;
    }

}
