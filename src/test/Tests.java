package test;

import common.AbstractFactoryClient;
import common.InsufficientPointsException;
import common.OwnerAlreadyRegisteredException;
import common.OwnerNotRegisteredException;
import impl.LoyaltyCardOperator;
import interfaces.ILoyaltyCardOwner;
import interfaces.ILoyaltyCard;
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
    private LoyaltyCardOperator loyaltyCardOperator;
    private ILoyaltyCardOwner loyaltyCardOwner;
    private ILoyaltyCardOwner loyaltyCardOwner2;

    //Before each test set the loyaltyCardOperator and loyaltyCardOwner properties to new instances of the operator and owner classes respectively
    @BeforeEach
    public void createOperator() {
        this.loyaltyCardOperator = new LoyaltyCardOperator();
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


    @Test
    public void registerOwner() {
        try {
            loyaltyCardOperator.registerOwner(loyaltyCardOwner);
            assertTrue(loyaltyCardOperator.getNumberOfCustomers()== 1, "Customer added");
        }
        catch (OwnerAlreadyRegisteredException e){

        }
    }

    @Test
    public void ownerRegistered() {
        boolean exceptionThrown = false;
        try {
            loyaltyCardOperator.registerOwner(loyaltyCardOwner);
            loyaltyCardOperator.registerOwner(loyaltyCardOwner);
        }
        catch (OwnerAlreadyRegisteredException e){
            exceptionThrown = true;
        }
        assertTrue(exceptionThrown);
    }

    @Test
    public void unregister() {
        try {
            loyaltyCardOperator.registerOwner(loyaltyCardOwner);
        }
        catch (OwnerAlreadyRegisteredException e){
        }
        try {
            loyaltyCardOperator.unregisterOwner(loyaltyCardOwner);
        }
        catch (OwnerNotRegisteredException e){
            assertFalse(true);
        }
    }

    @Test
    public void ownerNotRegistered() {
        boolean exceptionThrown = false;
        try {
            loyaltyCardOperator.unregisterOwner(loyaltyCardOwner);
        }
        catch (OwnerNotRegisteredException e){
            exceptionThrown = true;
        }
        assertTrue(exceptionThrown);
    }

    @Test
    public void processMoneyPurchase() {
        try {
            loyaltyCardOperator.registerOwner(loyaltyCardOwner);
            loyaltyCardOperator.processMoneyPurchase(loyaltyCardOwner.getEmail(),100);
        }
        catch (OwnerNotRegisteredException e){
        }
        catch (OwnerAlreadyRegisteredException e){
        }
        ILoyaltyCard loyaltyCard;
        for (int i = 0; i<loyaltyCardOperator.loyaltyCards.size(); i++){
            if (loyaltyCardOperator.loyaltyCards.get(i).getOwner().getEmail().equals(loyaltyCardOwner.getEmail())){
                loyaltyCard = loyaltyCardOperator.loyaltyCards.get(i);
                assertFalse(loyaltyCard.getNumberOfPoints()!=1);
            }
        }
    }

    @Test
    public void processMoneyPurchaseUnregistered() {
        boolean exceptionThrown = false;
        try {
            loyaltyCardOperator.processMoneyPurchase(loyaltyCardOwner.getEmail(),100);
        }
        catch (OwnerNotRegisteredException e){
            exceptionThrown = true;
        }
        assertFalse(exceptionThrown == false);
    }

    @Test
    public void processPointsPurchase() {
        try {
            loyaltyCardOperator.registerOwner(loyaltyCardOwner);
            loyaltyCardOperator.processMoneyPurchase(loyaltyCardOwner.getEmail(),1000);
            loyaltyCardOperator.processPointsPurchase(loyaltyCardOwner.getEmail(), 5);
        }
        catch (OwnerNotRegisteredException e){
        }
        catch (OwnerAlreadyRegisteredException e){
        }
        catch (InsufficientPointsException e) {
        }
        ILoyaltyCard loyaltyCard;
        for (int i = 0; i<loyaltyCardOperator.loyaltyCards.size(); i++){
            if (loyaltyCardOperator.loyaltyCards.get(i).getOwner().getEmail().equals(loyaltyCardOwner.getEmail())){
                loyaltyCard = loyaltyCardOperator.loyaltyCards.get(i);
                assertFalse(loyaltyCard.getNumberOfPoints()!=5);
                assertFalse(loyaltyCard.getNumberOfUses() != 1);
            }
        }
    }

    @Test
    public void spendInsufficientpoints(){
        boolean exceptionThrown = false;
        try {
            loyaltyCardOperator.registerOwner(loyaltyCardOwner);
            loyaltyCardOperator.processPointsPurchase(loyaltyCardOwner.getEmail(),100);
        }
        catch (OwnerNotRegisteredException e){
        }
        catch (OwnerAlreadyRegisteredException e){
        }
        catch (InsufficientPointsException e){
            exceptionThrown = true;
        }
        assertTrue(exceptionThrown);

    }

    @Test
    public void processPointsPurchaseUnregistered() {
        boolean exceptionThrown = false;
        try {
            loyaltyCardOperator.processPointsPurchase(loyaltyCardOwner.getEmail(),100);
        }
        catch (InsufficientPointsException e){
        }
        catch (OwnerNotRegisteredException e){
            exceptionThrown = true;
        }
        assertFalse(exceptionThrown == false);
    }

    @Test
    public void getCustomers(){
        try {
            loyaltyCardOperator.registerOwner(loyaltyCardOwner);
            assertFalse(loyaltyCardOperator.getNumberOfCustomers()!=1);
        }
        catch (OwnerAlreadyRegisteredException e){
        }
    }

    @Test
    public void getTotalPoints(){
        try {
            loyaltyCardOperator.registerOwner(loyaltyCardOwner);
            loyaltyCardOperator.registerOwner(loyaltyCardOwner2);
            loyaltyCardOperator.processMoneyPurchase("jon@jon.com", 1000);
            loyaltyCardOperator.processMoneyPurchase("bill@bill.com", 2000);
            assertFalse(loyaltyCardOperator.getTotalNumberOfPoints()!=30);
        }
        catch (OwnerAlreadyRegisteredException e){
        }
        catch (OwnerNotRegisteredException e){
        }
    }

    @Test
    public void getPoints(){
        try {
            loyaltyCardOperator.registerOwner(loyaltyCardOwner);
            loyaltyCardOperator.processMoneyPurchase("jon@jon.com", 1000);
            assertFalse(loyaltyCardOperator.getNumberOfPoints("jon@jon.com")!=10);
        }
        catch (OwnerAlreadyRegisteredException e){
        }
        catch (OwnerNotRegisteredException e){
        }
    }

    @Test
    public void getPointsUnregistered(){
        boolean exceptionThrown = false;
        try {
            loyaltyCardOperator.getNumberOfPoints("jon@jon.com");
        }
        catch (OwnerNotRegisteredException e){
            exceptionThrown = true;
        }
        assertFalse(exceptionThrown==false);
    }

    @Test
    public void getNumberOfUses(){
        try {
            loyaltyCardOperator.registerOwner(loyaltyCardOwner);
            loyaltyCardOperator.processMoneyPurchase("jon@jon.com", 1000);
            loyaltyCardOperator.processPointsPurchase("jon@jon.com", 10);
            assertFalse(loyaltyCardOperator.getNumberOfUses("jon@jon.com")!=1);
        }
        catch (OwnerAlreadyRegisteredException e){
        }
        catch (OwnerNotRegisteredException e){
        }
        catch (InsufficientPointsException e){
        }
    }

    @Test
    public void getNumberOfUsesUnregistered(){
        boolean exceptionThrown = false;
        try {
            assertFalse(loyaltyCardOperator.getNumberOfUses("jon@jon.com")!=1);
        }
        catch (OwnerNotRegisteredException e){
            exceptionThrown = true;
        }
        assertFalse(exceptionThrown==false);
    }

    @Test
    public void getMostUsed(){
        try {
            loyaltyCardOperator.registerOwner(loyaltyCardOwner);
            loyaltyCardOperator.registerOwner(loyaltyCardOwner2);
            loyaltyCardOperator.processMoneyPurchase("jon@jon.com", 1000);
            loyaltyCardOperator.processPointsPurchase("jon@jon.com", 10);
            assertFalse(loyaltyCardOperator.getMostUsed().getEmail()!=loyaltyCardOwner.getEmail());
        }
        catch (OwnerAlreadyRegisteredException e){
        }
        catch (OwnerNotRegisteredException e){
        }
        catch (InsufficientPointsException e){
        }
    }

    @Test
    public void getMostUsed2(){
        try {
            loyaltyCardOperator.registerOwner(loyaltyCardOwner);
            loyaltyCardOperator.registerOwner(loyaltyCardOwner2);
            loyaltyCardOperator.processMoneyPurchase("jon@jon.com", 1000);
            loyaltyCardOperator.processMoneyPurchase("bill@bill.com", 2000);
            loyaltyCardOperator.processPointsPurchase("jon@jon.com", 10);
            loyaltyCardOperator.processPointsPurchase("bill@bill.com", 10);
            assertFalse(loyaltyCardOperator.getMostUsed().getEmail()!=loyaltyCardOwner.getEmail());
        }
        catch (OwnerAlreadyRegisteredException e){
        }
        catch (OwnerNotRegisteredException e){
        }
        catch (InsufficientPointsException e){
        }
    }

    @Test
    public void getMostUsedException(){
        boolean exceptionThrown = false;
        try {
            loyaltyCardOperator.getMostUsed();
        }
        catch (OwnerNotRegisteredException e){
            exceptionThrown = true;
        }
        assertFalse(exceptionThrown == false);
    }



}
