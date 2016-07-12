package main.inventoryapp.st1nger13.me.inventoryapp;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity
{
    /** A custom adapter for showing all products from Database in a ListView **/
    private static CustomListAdapter adapter ;
    /** Instance of current Activity **/
    private static MainActivity instance ;
    /** Class helper for work with Database **/
    private static DBHelper dbh ;
    /** TextView which shows a message if database is empty. In other case it's not visible **/
    private static TextView noProductsView ;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState) ;
        setContentView(R.layout.activity_main) ;

        noProductsView = (TextView) findViewById(R.id.noProductsFoundView) ;
        instance = this ;
        dbh = new DBHelper(this) ;
        setupListViewAdapter() ;
    }

    /**
     * Open Details Activity
     * @param view
     */
    public void openDetails(View view)
    {
        Product productToShowDetails = (Product) view.getTag() ;
        Intent myIntent = new Intent(this, DetatilsActivity.class) ;
        myIntent.putExtra("productID", productToShowDetails.id) ;
        this.startActivity(myIntent) ;
    }

    /**
     * Open Add a new Product Activity
     * @param view
     */
    public void openAddNewProduct(View view)
    {
        Intent myIntent = new Intent(this, AddProductActivity.class) ;
        this.startActivityForResult(myIntent, 0) ;
    }

    /**
     * Get instance of class helper for work with Database
     * @return - Instance of class helper for work with Database
     */
    public static DBHelper getDBH()
    {
        return dbh ;
    }

    /**
     * Method updates all information in ListView according to last changes
     */
    public static void updateListViewAdapter(Product product)
    {
        adapter.notifyDataSetChanged() ;
        instance.updateNoProductsView() ;

        Button saleButton = (Button) instance.findViewById(R.id.listItemSale) ;
        if(saleButton != null)
        {
            if(product.quantity == 0)
            {
                saleButton.setBackgroundResource(R.drawable.button_bg_disabled) ;
                saleButton.setEnabled(false) ;
            }
            else
            {
                saleButton.setBackgroundResource(R.drawable.button_bg) ;
                saleButton.setEnabled(false) ;
            }
        }
    }

    public void saleProduct(View view)
    {
        DBHelper db = MainActivity.getDBH() ;
        Product product = (Product) view.getTag() ;
        AllProducts.updateProductQuantity(db, -1, product) ;
        MainActivity.updateListViewAdapter(product) ;
    }

    /**
     * Method fills ListView up with Products data from Database
     */
    private void setupListViewAdapter()
    {
        SQLiteDatabase db = dbh.getReadableDatabase() ;
        AllProducts.readProductsFromDB(db) ;
        List<Product> products = AllProducts.getProductList() ;

        adapter = new CustomListAdapter(instance, R.layout.listview_item, products) ;
        ListView productsListView = (ListView)instance.findViewById(R.id.productsListView);
        productsListView.setAdapter(adapter) ;
        updateNoProductsView() ;
    }

    /**
     * Method shows message if database is empty and hides message in other case
     */
    private void updateNoProductsView()
    {
        if(noProductsView != null)
        {
            if(AllProducts.getProductsCount() > 0)
                noProductsView.setVisibility(View.GONE) ;
            else
                noProductsView.setVisibility(View.VISIBLE) ;
        }
    }
}
