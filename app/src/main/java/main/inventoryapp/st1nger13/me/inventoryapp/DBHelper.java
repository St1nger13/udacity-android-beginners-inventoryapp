package main.inventoryapp.st1nger13.me.inventoryapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by St1nger13 on 29.06.2016.
 * Class Helper for easy work with Database
 */
public class DBHelper extends SQLiteOpenHelper
{

    public DBHelper(Context context)
    {
        super(context, "inventoryDB", null, 1) ;
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(DBContract.ProductsTable.CREATE_TABLE_IF_NOT_EXIST) ;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {}

    /**
     * Insert a new Product in Database. Data should be validated before this method
     * @param name - Product name
     * @param quantity - Product quantity
     * @param price - A price of Product
     * @param supplierMail - An owner's mail
     * @return - ID of the Product in Database. ID is a primary key in Products table
     */
    public int insertProduct(String name, int quantity, int price, String supplierMail)
    {
        ContentValues cv = new ContentValues() ;
        cv.put(DBContract.ProductsTable.COL_PRODUCT_NAME.NAME, name) ;
        cv.put(DBContract.ProductsTable.COL_QUANTITY.NAME, quantity) ;
        cv.put(DBContract.ProductsTable.COL_PRICE.NAME, price) ;
        cv.put(DBContract.ProductsTable.COL_SUPPLIER_MAIL.NAME, supplierMail) ;

        SQLiteDatabase db = getWritableDatabase() ;
        long rowID = db.insert(DBContract.ProductsTable.TABLE_NAME, null, cv) ;
        int productID = getProductID(name) ;

        return productID ;
    }

    /**
     * Update some column in Database with some value
     * @param col - Name of column to be updated
     * @param value - A new value of column
     * @param productID - Product ID
     */
    public void updateDBColumn(String col, String value, int productID)
    {
        SQLiteDatabase db = getWritableDatabase() ;

        db.execSQL("UPDATE "
                + DBContract.ProductsTable.TABLE_NAME + " SET "
                + col + " = " + value + " WHERE "
                + DBContract.ProductsTable.COL_ID.NAME + " = "
                + productID) ;
    }

    /**
     * Get Product ID by its name. Method returns -1 as result if something was wrong
     * @param name - Product name
     * @return - Product ID from Database
     */
    public int getProductID(String name)
    {
        SQLiteDatabase db = getWritableDatabase() ;
        Cursor cursor = null ;
        int id = -1 ;

        try
        {
            cursor = db.rawQuery("SELECT "
                    + DBContract.ProductsTable.COL_ID.NAME + " FROM "
                    + DBContract.ProductsTable.TABLE_NAME + " WHERE "
                    + DBContract.ProductsTable.COL_PRODUCT_NAME.NAME + "=?",
                    new String[] {name + ""}) ;

            if(cursor.getCount() > 0)
            {
                cursor.moveToFirst() ;
                id = cursor.getInt(cursor.getColumnIndex(DBContract.ProductsTable.COL_ID.NAME)) ;
            }
            return id ;
        }
        finally
        {
            cursor.close() ;
        }
    }
}
