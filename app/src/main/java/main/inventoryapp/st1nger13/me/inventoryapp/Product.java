package main.inventoryapp.st1nger13.me.inventoryapp;

/**
 * Created by St1nger13 on 29.06.2016.
 * Class represents one Product from Database for easy manipulating with data
 */
public class Product
{
    public int id ;
    public String name ;
    public int quantity ;
    public int price ;
    public String supplierMail ;


    public Product(int id, String name, int quantity, int price, String supplierMail)
    {
        this.id = id ;
        this.name = name.trim() ;
        this.quantity = quantity ;
        this.price = price ;
        this.supplierMail = supplierMail ;
    }

    public String getTitle()
    {
        return name ;
    }

    public String getQuantity()
    {
        return String.valueOf(quantity) ;
    }

    public String getPrice()
    {
        return String.valueOf(price) ;
    }
}
