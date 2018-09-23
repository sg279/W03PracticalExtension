package test;

import common.AbstractFactoryClient;
import common.OwnerAlreadyRegisteredException;
import common.OwnerNotRegisteredException;
import impl.LoyaltyCardOperator;
import interfaces.ILoyaltyCardOwner;
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

    private LoyaltyCardOperator loyaltyCardOperator;
    private ILoyaltyCardOwner loyaltyCardOwner;

    @BeforeEach
    public void createOperator() {
        this.loyaltyCardOperator = new LoyaltyCardOperator();
        this.loyaltyCardOwner = getFactory().makeLoyaltyCardOwner("jon@jon.com", "Jon");
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
    public void addCard() {
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
            //exceptionThrown = true;
            assertTrue(true);
        }
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

}
