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
        //Check that the name and email parameters aren't null
        if (email != null && name != null) {
            //Set the email and name properties to the email and name parameters
            this.email = email;
            this.name = name;
        }
        //Otherwise, throw a null pointer exception
        else {
            throw new NullPointerException();
        }
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
