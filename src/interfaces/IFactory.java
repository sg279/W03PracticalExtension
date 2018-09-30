package interfaces;


/**
 * Interface for a factory allowing the other interfaces to be instantiated without knowing the implementation classes.
 *
 */
public interface IFactory {

    /**
     * Creates an instance of {@link ILoyaltyCardOwner}.
     * @param email the owner's email
     * @param name the owner's name
     * @return the owner
     */
    ILoyaltyCardOwner makeLoyaltyCardOwner(String email, String name);


    /**
     * Creates an instance of {@link ILoyaltyCard} for the specified owner.
     * @param loyaltyCardOwner the owner of the card
     * @return the LoyaltyCard instance
     */
    ILoyaltyCard makeLoyaltyCard(ILoyaltyCardOwner loyaltyCardOwner);


    /**
     * Creates an instance of {@link ILoyaltyCardOperator}.
     *
     * @return the LoyaltyCardOperator
     */
    ILoyaltyCardOperator makeLoyaltyCardOperator();

    /**
     * Creates an instance of {@Link IProduct}
     * @param name the name of the product
     * @return the Product
     */
    IProduct makeProduct(String name);

    /**
     * Creates an instance of {@Link IProduct}
     * @param name the name of the product
     * @param promotion the promotion multiplier
     * @return the Product
     */
    IProduct makeProduct(String name, double promotion);


}
