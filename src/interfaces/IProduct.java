package interfaces;

public interface IProduct {

    /**
     * Returns the multiplier for points to be added to a card with a purchase (i.e 1.5 means that the number
     * of points added will be multiplied by 1.5)
     * @return The multiplier for the points to be added
     */
    double getPromotion();

    /**
     * Returns the name of the product
     * @return the name of the product
     */
    String getName();

    /**
     * Sets the extra points promotion for the product
     * @param promotion the multiplier for points that are added to a loyalty card
     */
    void setPromotion(double promotion);
}
