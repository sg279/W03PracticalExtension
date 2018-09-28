package impl;

import common.InsufficientPointsException;
import interfaces.ILoyaltyCard;
import interfaces.ILoyaltyCardOwner;

/**
 * This class represents a Loyalty Card, recording information relating to an owners use of the card.
 *
 */
public class LoyaltyCard implements ILoyaltyCard {

    //Create an ILoyaltyCardOwner property called loyaltyCardOwner
    private ILoyaltyCardOwner loyaltyCardOwner;
    //Establish two integer properties called numberOfUses and numberOfPoints
    private int numberOfUses;
    private int numberOfPoints;

    @Override
    public ILoyaltyCardOwner getOwner() {
        //Return the loyaltyCardOwner property
        return this.loyaltyCardOwner;
    }

    @Override
    public int getNumberOfUses() {
        //Return the numberOfUses property
        return this.numberOfUses;
    }

    @Override
    public int getNumberOfPoints() {
        //Return the numberOfPoints property
        return this.numberOfPoints;
    }

    @Override
    public void addPoints(int points) {
        //Increase the numberOfPoints by the value parsed as a parameter
        this.numberOfPoints += points;
    }

    @Override
    public void usePoints(int points) throws InsufficientPointsException {
        //If the points parameter is more than the numberOfPoints property throw InsufficientPointsException
        if (points > numberOfPoints) {
            throw new InsufficientPointsException();
        }
        //Otherwise decrease the number of points property by the points parameter and increment number of uses
        else {
            this.numberOfPoints -= points;
            this.numberOfUses++;
        }
    }

    public LoyaltyCard(ILoyaltyCardOwner loyaltyCardOwner) {
        //Set the loyaltyCardOwner property to the loyaltyCardOwner parameter
        this.loyaltyCardOwner = loyaltyCardOwner;
        //Set the numberOfUses and numberOfPoints properties to 0
        this.numberOfUses = 0;
        this.numberOfPoints = 0;
    }

}
