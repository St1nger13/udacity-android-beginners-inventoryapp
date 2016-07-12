package main.inventoryapp.st1nger13.me.inventoryapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by St1nger13 on 29.06.2016.
 */
public class AllProducts
{
    private static List<Product> products = new ArrayList<Product>() ;


    /**
     * Update Product Column - Quantity by some argument.
     * @param dbh - You can get it as MainActivity.getDBH() ;
     * @param argument - Can has positive or negative value
     * @param product - Product object
     */
    public static void updateProductQuantity(DBHelper dbh, int argument, Product product)
    {
        product.quantity += argument ;
        if(product.quantity < 0)
            product.quantity = 0 ;
        dbh.updateDBColumn(DBContract.ProductsTable.COL_QUANTITY.NAME,
                String.valueOf(product.quantity), product.id) ;
    }

    /**
     * Read all products from Database, create a Products List and
     * represents it as ListView on Main activity
     * @param db - Database. You can easily get it as MainActivity.getDBH().getReadableDatabase() ;
     */
    public static void readProductsFromDB(SQLiteDatabase db)
    {
        products.clear() ;
        Cursor cursor = db.rawQuery("SELECT * FROM " +DBContract.ProductsTable.TABLE_NAME, null) ;

        if (cursor.moveToFirst())
        {
            while (cursor.isAfterLast() == false)
            {
                int id = cursor.getInt(cursor.getColumnIndex(DBContract.ProductsTable.COL_ID.NAME)) ;
                String name = cursor.getString(cursor.getColumnIndex(DBContract.ProductsTable.COL_PRODUCT_NAME.NAME)) ;
                int quantity = cursor.getInt(cursor.getColumnIndex(DBContract.ProductsTable.COL_QUANTITY.NAME)) ;
                int price = cursor.getInt(cursor.getColumnIndex(DBContract.ProductsTable.COL_PRICE.NAME)) ;
                String supplierMail = cursor.getString(cursor.getColumnIndex(DBContract.ProductsTable.COL_SUPPLIER_MAIL.NAME)) ;

                if(name != null && name.length() > 0)
                {
                    Product p = new Product(id, name, quantity, price, supplierMail) ;
                    addProductOnTop(p) ;
                }
                cursor.moveToNext();
            }
        }
    }

    /**
     * Delete Product from Database
     * @param db - Database. You can easily get it as MainActivity.getDBH().getWritableDatabase() ;
     * @param product - Product object
     */
    public static void deleteProductFromDB(SQLiteDatabase db, Product product)
    {
        int delCount = db.delete(DBContract.ProductsTable.TABLE_NAME,
                DBContract.ProductsTable.COL_ID.NAME + " = " + product.id, null) ;
        if(delCount != -1)
            products.remove(product) ;
    }

    /**
     * Add new product into Database
     * @param name - Product name
     * @param quantity - Product quantity
     * @param price - Product price
     * @param supplierMail - Product owner's email
     * @param dbh - DBHelper instance. You can get it easily by MainActivity.getDBH() ;
     */
    public static Product addProduct(String name, int quantity, int price, String supplierMail, DBHelper dbh)
    {
        int productID = dbh.insertProduct(name, quantity, price, supplierMail) ;
        if(productID != -1)
        {
            Product product = new Product(productID, name, quantity, price, supplierMail) ;
            addProductOnTop(product);
            return product ;
        }
        return null ;
    }

    /**
     * Get Product object from Product List by its ID
     * @param id - Product ID
     * @return - Product object
     */
    public static Product getProductByID(int id)
    {
        for(Product p : products)
            if(p.id == id)
                return p ;
        return null ;
    }

    /**
     * Get count how many Products are stored in Database
     * @return - Products count
     */
    public static int getProductsCount()
    {
        return products.size() ;
    }

    /**
     * Get all Products as ArrayList
     * @return - list
     */
    public static List<Product> getProductList()
    {
        return products ;
    }

    /**
     * Add new Product into the Products List on first position.
     * This way newest products will be always on top in MainActivity
     * @param product - Product object
     */
    private static void addProductOnTop(Product product)
    {
        products.add(0, product) ;
    }
}
