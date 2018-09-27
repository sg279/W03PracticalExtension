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

    //Instantiate an array of LoyaltyCard objects as a property called loyaltyCards
    private ArrayList<ILoyaltyCard> loyaltyCards = new ArrayList<ILoyaltyCard>();

    @Override
    public void registerOwner(ILoyaltyCardOwner loyaltyCardOwner) throws OwnerAlreadyRegisteredException {
        //For each loyalty card in the array, if the card's owner's email address is the same as the email address of the owner parsed as a parameter
        //throw OwnerAlreadyRegisteredException
        for (ILoyaltyCard loyaltyCard
                : loyaltyCards
             ) {
            if (loyaltyCard.getOwner().getEmail().equals(loyaltyCardOwner.getEmail())) {
                throw new OwnerAlreadyRegisteredException();
            }
        }
        //Add a loyalty card to the array, calling the factory to make a new ILoyaltyCard
        loyaltyCards.add(getFactory().makeLoyaltyCard(loyaltyCardOwner));
    }

    @Override
    public void unregisterOwner(ILoyaltyCardOwner loyaltyCardOwner) throws OwnerNotRegisteredException {
        //Instantiate a boolean called ownerRegistered as false
        boolean ownerRegistered = false;
        //For each item in the loyaltyCards array do the following
        for (int i = 0; i < loyaltyCards.size(); i++) {
            //If the loyalty card's owner's email is the same as the email of the owner parsed as a parameter do the following
            if (loyaltyCards.get(i).getOwner().getEmail().equals(loyaltyCardOwner.getEmail())) {
                //Remove the loyalty card from the array
                loyaltyCards.remove(i);
                //set ownerRegistered to true
                ownerRegistered = true;
            }
        }
        //If ownerRegistered is false, throw OwnerNotRegisteredException
        if (!ownerRegistered) {
            throw new OwnerNotRegisteredException();
        }
    }

    @Override
    public void processMoneyPurchase(String ownerEmail, int pence) throws OwnerNotRegisteredException {
        //Instantiate a boolean called ownerRegistered as false
        boolean ownerRegistered = false;
        //For each loyaltyCard in the loyaltyCards property, do the following
        for (ILoyaltyCard loyaltyCard
                : loyaltyCards
                ) {
            //If the loyalty card's owner's email is the same as the email parsed as a parameter, add the amount
            //spent divided by 100 to the card's points property and set ownerRegistered to true
            if (loyaltyCard.getOwner().getEmail().equals(ownerEmail)) {
                loyaltyCard.addPoints(pence / 100);
                ownerRegistered = true;
            }
        }
        //If ownerRegistered is false, throw OwnerNotRegisteredException
        if (!ownerRegistered) {
            throw new OwnerNotRegisteredException();
        }
    }

    @Override
    public void processPointsPurchase(String ownerEmail, int pence)
            throws InsufficientPointsException, OwnerNotRegisteredException {
        //Instantiate a boolean called ownerRegistered as false
        boolean ownerRegistered = false;
        //For each loyalty card in the loyaltyCards property do the following
        for (ILoyaltyCard loyaltyCard
                : loyaltyCards
                ) {
            //If the loyalty card's owner's email is the same as the email parsed as a parameter call the usepoints method with the pence parameter
            //and set ownerRegistered to true
            if (loyaltyCard.getOwner().getEmail().equals(ownerEmail)) {
                loyaltyCard.usePoints(pence);
                ownerRegistered = true;
            }
        }
        //If ownerRegistered is false, throw OwnerNotRegisteredException
        if (!ownerRegistered) {
            throw new OwnerNotRegisteredException();
        }
    }

    @Override
    public int getNumberOfCustomers() {
        //Return the size of the loyaltyCards array
        return loyaltyCards.size();
    }

    @Override
    public int getTotalNumberOfPoints() {
        //Instantiate a variable called number of points as 0
        int numberOfPoints = 0;
        //For each loyaltyCard in the loyaltyCards property do the following
        for (ILoyaltyCard loyaltyCard
                : loyaltyCards
                ) {
            //Add the number of points from the loyalty card to the total number of points
            numberOfPoints += loyaltyCard.getNumberOfPoints();
        }
        //Return the numberOfPoints variable
        return numberOfPoints;
    }

    @Override
    public int getNumberOfPoints(String ownerEmail) throws OwnerNotRegisteredException {
        //Instantiate a boolean called ownerRegistered as false
        boolean ownerRegistered = false;
        //Instantiate an integer called numberOfPoints as 0
        int numberOfPoints = 0;
        //For each loyaltyCard in the loyaltyCards property do the following
        for (ILoyaltyCard loyaltyCard
                : loyaltyCards
                ) {
            //If the loyalty card's owner's email is the same as the email parsed as a parameter set the numberOfPoints variable to the getNumberOfPoints method
            //called on the loyaltyCard and set ownerRegistered to true
            if (loyaltyCard.getOwner().getEmail().equals(ownerEmail)) {
                numberOfPoints = loyaltyCard.getNumberOfPoints();
                ownerRegistered = true;
            }
        }
        //If ownerRegistered is false, throw OwnerNotRegisteredException
        if (!ownerRegistered) {
            throw new OwnerNotRegisteredException();
        }
        //Return the numberOfPoints variable
        return numberOfPoints;
    }

    @Override
    public int getNumberOfUses(String ownerEmail) throws OwnerNotRegisteredException {
        //Instantiate a boolean called ownerRegistered as false
        boolean ownerRegistered = false;
        //Instantiate an integer called numberOfUses as 0
        int numberOfUses = 0;
        //For each loyaltyCard in the loyaltyCards property do the following
        for (ILoyaltyCard loyaltyCard
                : loyaltyCards
                ) {
            //If the loyalty card's owner's email is the same as the email parsed as a parameter set the numberOfUses variable to the getNumberOfUses method
            //called on the loyaltyCard and set ownerRegistered to true
            if (loyaltyCard.getOwner().getEmail().equals(ownerEmail)) {
                numberOfUses = loyaltyCard.getNumberOfUses();
                ownerRegistered = true;
            }
        }
        //If ownerRegistered is false, throw OwnerNotRegisteredException
        if (!ownerRegistered) {
            throw new OwnerNotRegisteredException();
        }
        //Return the numberOfUses variable
        return numberOfUses;
    }

    @Override
    public ILoyaltyCardOwner getMostUsed() throws OwnerNotRegisteredException {
        //If there are no items in the loyaltyCards array throw OwnerNotRegisteredException
        if (loyaltyCards.size() == 0) {
            throw new OwnerNotRegisteredException();
        }
        //Otherwise do the following
        else {
            //Instantiate an IloyaltyCard object called mostUsed as the first item in the loyaltyCards array
            ILoyaltyCard mostUsed = loyaltyCards.get(0);
            //For each loyaltyCard in the loyaltyCards property do the following
            for (ILoyaltyCard loyaltyCard
                    : loyaltyCards
            ) {
                //If the loyaltyCard's number of uses is more than the number of uses of the mostUsed card set mostUsed to the loyaltyCard
                if (loyaltyCard.getNumberOfUses() > mostUsed.getNumberOfUses()) {
                    mostUsed = loyaltyCard;
                }
            }
            //Return the getOwner method called on the mostUsed card
            return mostUsed.getOwner();
        }
    }

}
