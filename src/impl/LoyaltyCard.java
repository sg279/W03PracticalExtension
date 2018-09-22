package impl;

import common.InsufficientPointsException;
import interfaces.ILoyaltyCard;
import interfaces.ILoyaltyCardOwner;

/**
 * This class represents a Loyalty Card, recording information relating to an owners use of the card.
 *
 */
public class LoyaltyCard implements ILoyaltyCard {

    private ILoyaltyCardOwner loyaltyCardOwner;
    private int numberOfUses;
    private int numberOfPoints;

    @Override
    public ILoyaltyCardOwner getOwner() {

        return this.loyaltyCardOwner;
    }

    @Override
    public int getNumberOfUses() {

        return this.numberOfUses;
    }

    @Override
    public int getNumberOfPoints() {

        return this.numberOfPoints;
    }

    @Override
    public void addPoints(int points) {
        this.numberOfPoints+=points;
    }

    @Override
    public void usePoints(int points) throws InsufficientPointsException {
        if (points>numberOfPoints){
            throw new InsufficientPointsException();
        }
        else{
            this.numberOfPoints-=points;
            this.numberOfUses++;
        }
    }

    public LoyaltyCard(ILoyaltyCardOwner loyaltyCardOwner) {
        this.loyaltyCardOwner=loyaltyCardOwner;
        this.numberOfUses=0;
        this.numberOfPoints=0;
    }

}
