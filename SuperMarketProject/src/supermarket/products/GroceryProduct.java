package supermarket.products;


public class GroceryProduct
{
    private String name;
    private double price;
    private double discount;

    public GroceryProduct(String name, double price, double discount)
    {
        this.name = name;
        this.price = price;
        this.discount = discount;
    }

    public final String getName()
    {
        return name;
    }

    public final double getDiscount()
    {
        return discount;
    }

    public final double getPrice()
    {
        return price;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setDiscount(double discount)
    {
        this.discount = discount;
    }

    public void setPrice(double price)
    {
        this.price = price;
    }

    public final double getActualPrice()
    {
        return (price * (price - discount / 100));
    }    

    public String toString()
    {
        return "name: " + name + "\n" +
                "Price: " + price + "\n" +
                "Discount: " + discount + "\n";
    }
}