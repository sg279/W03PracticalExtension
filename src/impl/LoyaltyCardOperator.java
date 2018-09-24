package impl;

import common.AbstractFactoryClient;
import common.InsufficientPointsException;
import common.OwnerAlreadyRegisteredException;
import common.OwnerNotRegisteredException;
import interfaces.ILoyaltyCard;
import interfaces.ILoyaltyCardOperator;
import interfaces.ILoyaltyCardOwner;

import java.util.ArrayList;


/**
 * This class represents a simple loyalty card operator.
 *
 */
public class LoyaltyCardOperator extends AbstractFactoryClient implements ILoyaltyCardOperator {

    private ArrayList<LoyaltyCard> loyaltyCards = new ArrayList<LoyaltyCard>();

    @Override
    public void registerOwner(ILoyaltyCardOwner loyaltyCardOwner) throws OwnerAlreadyRegisteredException {
        for (LoyaltyCard loyaltyCard:
                loyaltyCards
             ) {
            if(loyaltyCard.getOwner().getEmail().equals(loyaltyCardOwner.getEmail())){
                throw new OwnerAlreadyRegisteredException();
            }
        }
        loyaltyCards.add(new LoyaltyCard(getFactory().makeLoyaltyCardOwner(loyaltyCardOwner.getEmail(), loyaltyCardOwner.getName())));
    }

    @Override
    public void unregisterOwner(ILoyaltyCardOwner loyaltyCardOwner) throws OwnerNotRegisteredException {
        boolean ownerRegistered = false;
        for (int i = 0; i<loyaltyCards.size(); i++) {
            if(loyaltyCards.get(i).getOwner().getEmail().equals(loyaltyCardOwner.getEmail())){
                loyaltyCards.remove(i);
                ownerRegistered = true;
            }
        }
        if (!ownerRegistered){
            throw new OwnerNotRegisteredException();
        }
    }

    @Override
    public void processMoneyPurchase(String ownerEmail, int pence) throws OwnerNotRegisteredException {
        boolean ownerRegistered = false;
        for (LoyaltyCard loyaltyCard:
                loyaltyCards
                ) {
            if(loyaltyCard.getOwner().getEmail().equals(ownerEmail)){
                loyaltyCard.addPoints(pence/100);
                ownerRegistered = true;
            }
        }
        if (!ownerRegistered){
            throw new OwnerNotRegisteredException();
        }
    }

    @Override
    public void processPointsPurchase(String ownerEmail, int pence)
            throws InsufficientPointsException, OwnerNotRegisteredException {
        boolean ownerRegistered = false;
        for (LoyaltyCard loyaltyCard:
                loyaltyCards
                ) {
            if(loyaltyCard.getOwner().getEmail().equals(ownerEmail)){
                loyaltyCard.usePoints(pence);
                ownerRegistered = true;
            }
        }
        if (!ownerRegistered){
            throw new OwnerNotRegisteredException();
        }
    }

    @Override
    public int getNumberOfCustomers() {
        return loyaltyCards.size();
    }

    @Override
    public int getTotalNumberOfPoints() {
        int numberOfPoints = 0;
        for (LoyaltyCard loyaltyCard:
                loyaltyCards
                ) {
            numberOfPoints+=loyaltyCard.getNumberOfPoints();
        }
        return numberOfPoints;
    }

    @Override
    public int getNumberOfPoints(String ownerEmail) throws OwnerNotRegisteredException {
        boolean ownerRegistered = false;
        int numberOfPoints=0;
        for (LoyaltyCard loyaltyCard:
                loyaltyCards
                ) {
            if(loyaltyCard.getOwner().getEmail().equals(ownerEmail)){
                numberOfPoints=loyaltyCard.getNumberOfPoints();
                ownerRegistered = true;
            }
        }
        if (!ownerRegistered){
            throw new OwnerNotRegisteredException();
        }
        return numberOfPoints;
    }

    @Override
    public int getNumberOfUses(String ownerEmail) throws OwnerNotRegisteredException {
        boolean ownerRegistered = false;
        int numberOfUses=0;
        for (LoyaltyCard loyaltyCard:
                loyaltyCards
                ) {
            if(loyaltyCard.getOwner().getEmail().equals(ownerEmail)){
                numberOfUses=loyaltyCard.getNumberOfUses();
                ownerRegistered = true;
            }
        }
        if (!ownerRegistered){
            throw new OwnerNotRegisteredException();
        }
        return numberOfUses;
    }

    @Override
    public ILoyaltyCardOwner getMostUsed() throws OwnerNotRegisteredException {
        LoyaltyCard mostUsed = loyaltyCards.get(0);
        for (LoyaltyCard loyaltyCard:
                loyaltyCards
                ) {
            if(loyaltyCard.getNumberOfUses()>mostUsed.getNumberOfUses()){
                mostUsed=loyaltyCard;
            }
        }
        return null;
    }

}
