package supermarket.products;

public class BeverageProduct extends GroceryProduct implements Drinkable
{
    private SugarLevel sugarLevel;

    public BeverageProduct(String name, double price, double discount, SugarLevel sugarLevel)
    {
        super(name, price, discount);
        this.sugarLevel = sugarLevel;
    }

    public SugarLevel getSugarLevel()
    {
        return sugarLevel;
    }
    public void setSugarLevel(SugarLevel sugarLevel)
    {
        this.sugarLevel = sugarLevel;
    }
    public String toString()
    {
        // return super.toString() + " Sugar Level: " + sugarLevel;
        return super.toString() + "SugarLevel: " + sugarLevel;
    }
    public boolean isHealthy()
    {
        return sugarLevel != SugarLevel.ADDED_SUGAR;
    }
}