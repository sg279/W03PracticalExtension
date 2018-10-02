package test;

import common.AbstractFactoryClient;
import common.InsufficientPointsException;
import common.OwnerAlreadyRegisteredException;
import common.OwnerNotRegisteredException;
import impl.LoyaltyCardOperator;
import interfaces.ILoyaltyCardOperator;
import interfaces.ILoyaltyCardOwner;
import interfaces.ILoyaltyCard;
import interfaces.IProduct;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * This is a JUnit test class for the loyalty card ADT.
 *
 */
public class Tests extends AbstractFactoryClient {

    //Define a LoyaltyCardOperator and two ILoyaltyCardOwner properties
    private ILoyaltyCardOperator loyaltyCardOperator;
    private ILoyaltyCardOwner loyaltyCardOwner;
    private ILoyaltyCardOwner loyaltyCardOwner2;

    //Before each test set the loyaltyCardOperator and loyaltyCardOwner properties to new instances of the operator and owner classes respectively
    @BeforeEach
    public void createOperator() {
        this.loyaltyCardOperator = getFactory().makeLoyaltyCardOperator();
        this.loyaltyCardOwner = getFactory().makeLoyaltyCardOwner("jon@jon.com", "Jon");
        this.loyaltyCardOwner2 = getFactory().makeLoyaltyCardOwner("bill@bill.com", "Bill");
    }

    /**
     * This checks that the factory was able to call a sensible constructor to get a non-null instance of ILoyaltyCardOwner.
     */
    @Test
    public void loyaltyCardOwnerCreationNonNull() {
        ILoyaltyCardOwner loyaltyCardOwner = getFactory().makeLoyaltyCardOwner("jon@jon.com", "Jon");
        assertFalse(loyaltyCardOwner == null);
    }

    /**
     * This checks that an owner can't be created if the values parsed are null and that an exception is through
     */
    @Test
    public void createOwnerNullValues() {
        boolean exceptionThrown = false;
        try {
            ILoyaltyCardOwner nullOwner = getFactory().makeLoyaltyCardOwner(null, null);
        }
        catch (NullPointerException e) {
            exceptionThrown = true;
        }
        assertTrue(exceptionThrown);
    }

    /**
     * This checks that the card operator can register an owner
     */
    @Test
    public void registerOwner() {
        try {
            loyaltyCardOperator.registerOwner(loyaltyCardOwner);
            assertTrue(loyaltyCardOperator.getNumberOfCustomers() == 1);
        }
        catch (OwnerAlreadyRegisteredException e) {

        }
    }

    /**
     * This checks that the card operator can't register an owner if the owner object is null and an exception is thrown
     */
    @Test
    public void registerOwnerNull() {
        boolean exceptionThrown = false;
        try {
            loyaltyCardOperator.registerOwner(null);
            assertFalse(loyaltyCardOperator.getNumberOfCustomers() == 1);
        }
        catch (OwnerAlreadyRegisteredException e) {

        }
        catch (NullPointerException e) {
            exceptionThrown = true;
        }
        assertTrue(exceptionThrown);
    }

    /**
     * This checks that if the same owner is registered twice, an OwnerAlreadyRegisteredException is thrown
     */
    @Test
    public void ownerRegistered() {
        boolean exceptionThrown = false;
        try {
            loyaltyCardOperator.registerOwner(loyaltyCardOwner);
            loyaltyCardOperator.registerOwner(loyaltyCardOwner);
        }
        catch (OwnerAlreadyRegisteredException e) {
            exceptionThrown = true;
        }
        assertTrue(exceptionThrown);
    }

    /**
     * This tests that the number of customers is 0 after the unregisterOwner method is called on the operator after registering an owner
     */
    @Test
    public void unregister() {
        try {
            loyaltyCardOperator.registerOwner(loyaltyCardOwner);
            loyaltyCardOperator.unregisterOwner(loyaltyCardOwner);
        }
        catch (OwnerAlreadyRegisteredException e) {
        }
        catch (OwnerNotRegisteredException e) {
        }
        assertFalse(loyaltyCardOperator.getNumberOfCustomers() != 0);
    }

    /**
     * This tests that the OwnerNotRegisteredException is thrown if the unregisterOwner method is called for an owner that isn't registered
     */
    @Test
    public void ownerNotRegistered() {
        boolean exceptionThrown = false;
        try {
            loyaltyCardOperator.unregisterOwner(loyaltyCardOwner);
        }
        catch (OwnerNotRegisteredException e) {
            exceptionThrown = true;
        }
        assertTrue(exceptionThrown);
    }

    /**
     * This tests that the number of points on a loyalty card increases by 1 if the processMoneyPurchase method is called on a loyalty card owner
     */
    @Test
    public void processMoneyPurchase() {
        try {
            loyaltyCardOperator.registerOwner(loyaltyCardOwner);
            loyaltyCardOperator.processMoneyPurchase(loyaltyCardOwner.getEmail(), 100);
            assertFalse(loyaltyCardOperator.getNumberOfPoints(loyaltyCardOwner.getEmail()) != 1);
        }
        catch (OwnerNotRegisteredException e) {
        }
        catch (OwnerAlreadyRegisteredException e) {
        }
    }

    /**
     * This tests that if the processMoneyPurchase is called for an unregistered owner, OwnerNotRegisteredException is thrown
     */
    @Test
    public void processMoneyPurchaseUnregistered() {
        boolean exceptionThrown = false;
        try {
            loyaltyCardOperator.processMoneyPurchase(loyaltyCardOwner.getEmail(), 100);
        }
        catch (OwnerNotRegisteredException e) {
            exceptionThrown = true;
        }
        assertTrue(exceptionThrown);
    }

    /**
     *This tests that if the processPointsPurchase method is called for a customer with enough points, the points reduces by the correct amount
     *and that the number of uses increases by 1
     */
    @Test
    public void processPointsPurchase() {
        try {
            loyaltyCardOperator.registerOwner(loyaltyCardOwner);
            loyaltyCardOperator.processMoneyPurchase(loyaltyCardOwner.getEmail(), 1000);
            loyaltyCardOperator.processPointsPurchase(loyaltyCardOwner.getEmail(),  5);
            assertFalse(loyaltyCardOperator.getNumberOfPoints(loyaltyCardOwner.getEmail()) != 5);
            assertFalse(loyaltyCardOperator.getNumberOfUses(loyaltyCardOwner.getEmail()) != 1);
        }
        catch (OwnerNotRegisteredException e) {
        }
        catch (OwnerAlreadyRegisteredException e) {
        }
        catch (InsufficientPointsException e) {
        }
    }

    /**
     * This tests that if the processPointsPurchase method is called for an owner with insufficient points, InsufficientPointsException is thrown
     */
    @Test
    public void spendInsufficientPoints() {
        boolean exceptionThrown = false;
        try {
            loyaltyCardOperator.registerOwner(loyaltyCardOwner);
            loyaltyCardOperator.processPointsPurchase(loyaltyCardOwner.getEmail(), 100);
        }
        catch (OwnerNotRegisteredException e) {
        }
        catch (OwnerAlreadyRegisteredException e) {
        }
        catch (InsufficientPointsException e) {
            exceptionThrown = true;
        }
        assertTrue(exceptionThrown);

    }

    /**
     * This tests that if the processPointsPurchase method is called for an owner that isn't registered, OwnerNotRegisteredException is thrown
     */
    @Test
    public void processPointsPurchaseUnregistered() {
        boolean exceptionThrown = false;
        try {
            loyaltyCardOperator.processPointsPurchase(loyaltyCardOwner.getEmail(), 100);
        }
        catch (InsufficientPointsException e) {
        }
        catch (OwnerNotRegisteredException e) {
            exceptionThrown = true;
        }
        assertTrue(exceptionThrown);
    }

    /**
     * This tests that the getNumberOfCustomers method get's the correct number of customers
     */
    @Test
    public void getCustomers() {
        try {
            loyaltyCardOperator.registerOwner(loyaltyCardOwner);
            assertFalse(loyaltyCardOperator.getNumberOfCustomers() != 1);
        }
        catch (OwnerAlreadyRegisteredException e) {
        }
    }

    /**
     * This tests that the getTotalPoints method returns the correct number of total points
     */
    @Test
    public void getTotalPoints() {
        try {
            loyaltyCardOperator.registerOwner(loyaltyCardOwner);
            loyaltyCardOperator.registerOwner(loyaltyCardOwner2);
            loyaltyCardOperator.processMoneyPurchase("jon@jon.com", 1000);
            loyaltyCardOperator.processMoneyPurchase("bill@bill.com", 2000);
            assertFalse(loyaltyCardOperator.getTotalNumberOfPoints() != 30);
        }
        catch (OwnerAlreadyRegisteredException e) {
        }
        catch (OwnerNotRegisteredException e) {
        }
    }

    /**
     * This tests that the getPoints method returns the correct number of points
     */
    @Test
    public void getPoints() {
        try {
            loyaltyCardOperator.registerOwner(loyaltyCardOwner);
            loyaltyCardOperator.processMoneyPurchase("jon@jon.com", 1000);
            assertFalse(loyaltyCardOperator.getNumberOfPoints("jon@jon.com") != 10);
        }
        catch (OwnerAlreadyRegisteredException e) {
        }
        catch (OwnerNotRegisteredException e) {
        }
    }

    /**
     * This tests that if the getPoints method is called for an owner that isn't registered, OwnerNotRegisteredException is thrown
     */
    @Test
    public void getPointsUnregistered() {
        boolean exceptionThrown = false;
        try {
            loyaltyCardOperator.getNumberOfPoints("jon@jon.com");
        }
        catch (OwnerNotRegisteredException e) {
            exceptionThrown = true;
        }
        assertTrue(exceptionThrown);
    }

    /**
     * This tests that the getNumberOfUses method returns the correct number of uses
     */
    @Test
    public void getNumberOfUses()  {
        try  {
            loyaltyCardOperator.registerOwner(loyaltyCardOwner);
            loyaltyCardOperator.processMoneyPurchase("jon@jon.com", 1000);
            loyaltyCardOperator.processPointsPurchase("jon@jon.com", 10);
            assertFalse(loyaltyCardOperator.getNumberOfUses("jon@jon.com") != 1);
        }
        catch (OwnerAlreadyRegisteredException e) {
        }
        catch (OwnerNotRegisteredException e) {
        }
        catch (InsufficientPointsException e) {
        }
    }

    /**
     * This tests that if the getNumberOfUses method is called for an owner that isn't registered, OwnerNotRegisteredException is thrown
     */
    @Test
    public void getNumberOfUsesUnregistered() {
        boolean exceptionThrown = false;
        try {
            loyaltyCardOperator.getNumberOfUses("jon@jon.com");
        }
        catch (OwnerNotRegisteredException e) {
            exceptionThrown = true;
        }
        assertTrue(exceptionThrown);
    }

    /**
     * This checks that the getMostUsed method returns the correct owner
     */
    @Test
    public void getMostUsed() {
        try {
            loyaltyCardOperator.registerOwner(loyaltyCardOwner);
            loyaltyCardOperator.registerOwner(loyaltyCardOwner2);
            loyaltyCardOperator.processMoneyPurchase("jon@jon.com", 1000);
            loyaltyCardOperator.processPointsPurchase("jon@jon.com", 10);
            assertFalse(loyaltyCardOperator.getMostUsed().getEmail() != loyaltyCardOwner.getEmail());
        }
        catch (OwnerAlreadyRegisteredException e) {
        }
        catch (OwnerNotRegisteredException e) {
        }
        catch (InsufficientPointsException e) {
        }
    }

    /**
     * This checks that the getMostUsed2 method returns the first owner if there are multiple with the most uses
     */
    @Test
    public void getMostUsed2() {
        try {
            loyaltyCardOperator.registerOwner(loyaltyCardOwner);
            loyaltyCardOperator.registerOwner(loyaltyCardOwner2);
            loyaltyCardOperator.processMoneyPurchase("jon@jon.com", 1000);
            loyaltyCardOperator.processMoneyPurchase("bill@bill.com", 2000);
            loyaltyCardOperator.processPointsPurchase("jon@jon.com", 10);
            loyaltyCardOperator.processPointsPurchase("bill@bill.com", 10);
            assertFalse(loyaltyCardOperator.getMostUsed().getEmail() != loyaltyCardOwner.getEmail());
        }
        catch (OwnerAlreadyRegisteredException e) {
        }
        catch (OwnerNotRegisteredException e) {
        }
        catch (InsufficientPointsException e) {
        }
    }

    /**
     * This tests that the getMostUsed method throws OwnerNotRegisteredException if there are no registered owners
     */
    @Test
    public void getMostUsedException() {
        boolean exceptionThrown = false;
        try {
            loyaltyCardOperator.getMostUsed();
        }
        catch (OwnerNotRegisteredException e) {
            exceptionThrown = true;
        }
        assertTrue(exceptionThrown);
    }

    /**
     * This tests that the processMoneyPurchase method called with a product that has a promotion adds the correct amount of loyalty points
     */
    @Test
    public void processMoneyPurchasePromotion() {
        try {
            loyaltyCardOperator.registerOwner(loyaltyCardOwner);
            loyaltyCardOperator.processMoneyPurchase(loyaltyCardOwner.getEmail(), 1000, getFactory().makeProduct("TV", 1.5));
            assertFalse(loyaltyCardOperator.getNumberOfPoints(loyaltyCardOwner.getEmail()) != 15);
        }
        catch (OwnerNotRegisteredException e) {
        }
        catch (OwnerAlreadyRegisteredException e) {
        }
    }

    /**
     * This tests that the processMoneyPurchase method called with a null object as a product adds the default amount of loyalty points and doesn't throw an exception
     */
    @Test
    public void processMoneyPurchaseNullPromotion() {
        try {
            loyaltyCardOperator.registerOwner(loyaltyCardOwner);
            loyaltyCardOperator.processMoneyPurchase(loyaltyCardOwner.getEmail(), 1000, null);
            assertFalse(loyaltyCardOperator.getNumberOfPoints(loyaltyCardOwner.getEmail()) != 10);
        }
        catch (OwnerNotRegisteredException e) {
        }
        catch (OwnerAlreadyRegisteredException e) {
        }
    }

    //This tests that creating a product with an invalid promotion property throws an IllegalArgumentException
    @Test
    public void promotionException() {
        boolean exceptionThrown = false;
        try {
            IProduct product = getFactory().makeProduct("test", -1);
        }
        catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        assertTrue(exceptionThrown);
    }

    //This tests that setting a product's promotion value to an invalid amount throws an IllegalArgumentException
    @Test
    public void setPromotionException() {
        boolean exceptionThrown = false;
        try {
            IProduct product = getFactory().makeProduct("test");
            product.setPromotion(-1);
        }
        catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        assertTrue(exceptionThrown);
    }

    //This tests that calling the processMoneyPurchase with a points parameter causes the number of points to be removed from the card and the number of points added
    //to be based on the price with the points used subtracted
    @Test
    void processPartialPointsPurchase() {
        try {
            loyaltyCardOperator.registerOwner(loyaltyCardOwner);
            loyaltyCardOperator.processMoneyPurchase(loyaltyCardOwner.getEmail(), 1000);
            loyaltyCardOperator.processMoneyPurchase(loyaltyCardOwner.getEmail(), 205, 5);
            assertFalse(loyaltyCardOperator.getNumberOfPoints(loyaltyCardOwner.getEmail()) != 7);
        }
        catch (OwnerNotRegisteredException e) {
        }
        catch (OwnerAlreadyRegisteredException e) {
        }
        catch (InsufficientPointsException e) {
        }
    }

    //This tests that calling the processMoneyPurchase with a points parameter that is greater than the cost of the item causes
    //no points to be added to the card and the price of the item purchased to be subtracted from the points on the card
    @Test
    void processPartialPointsPurchase2() {
        try {
            loyaltyCardOperator.registerOwner(loyaltyCardOwner);
            loyaltyCardOperator.processMoneyPurchase(loyaltyCardOwner.getEmail(), 100);
            loyaltyCardOperator.processMoneyPurchase(loyaltyCardOwner.getEmail(), 5, 6);
            assertFalse(loyaltyCardOperator.getNumberOfPoints(loyaltyCardOwner.getEmail()) != 1);
        }
        catch (OwnerNotRegisteredException e) {
        }
        catch (OwnerAlreadyRegisteredException e) {
        }
        catch (InsufficientPointsException e) {
        }
    }

    //This tests that calling the processMoneyPurchase with a points and product parameter causes the number of points to be removed from the card and the number of points added
    //to be based on the price with the points used subtracted multiplied by the promotion
    @Test
    void processPartialPointsPurchase3() {
        try {
            loyaltyCardOperator.registerOwner(loyaltyCardOwner);
            loyaltyCardOperator.processMoneyPurchase(loyaltyCardOwner.getEmail(), 1000);
            loyaltyCardOperator.processMoneyPurchase(loyaltyCardOwner.getEmail(), 205, getFactory().makeProduct("test", 2), 5);
            assertFalse(loyaltyCardOperator.getNumberOfPoints(loyaltyCardOwner.getEmail()) != 9);
        }
        catch (OwnerNotRegisteredException e) {
        }
        catch (OwnerAlreadyRegisteredException e) {
        }
        catch (InsufficientPointsException e) {
        }
    }

    //This tests that calling the processMoneyPurchase method with a points parameter that is greater than the number of points on the card causes
    //an InsufficientPointsException to be thrown
    @Test
    void processPartialPointsPurchaseInsufficient() {
        boolean exceptionThrown = false;
        try {
            loyaltyCardOperator.registerOwner(loyaltyCardOwner);
            loyaltyCardOperator.processMoneyPurchase(loyaltyCardOwner.getEmail(), 100);
            loyaltyCardOperator.processMoneyPurchase(loyaltyCardOwner.getEmail(), 205, 5);
        }
        catch (OwnerNotRegisteredException e) {
        }
        catch (OwnerAlreadyRegisteredException e) {
        }
        catch (InsufficientPointsException e) {
            exceptionThrown = true;
        }
        assertTrue(exceptionThrown);
    }

}
