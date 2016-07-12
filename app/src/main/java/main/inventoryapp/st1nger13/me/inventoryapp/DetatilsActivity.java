package main.inventoryapp.st1nger13.me.inventoryapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class DetatilsActivity extends AppCompatActivity
{
    /** Current product to show **/
    private Product currentProduct ;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState) ;
        setContentView(R.layout.activity_detatils) ;

        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            int id = extras.getInt("productID") ;
            currentProduct = AllProducts.getProductByID(id) ;
            if(currentProduct != null)
            {
                updateInformationAboutProduct() ;
            }
        }
        else
            this.finish() ;
    }

    @Override
    public void finish()
    {
        this.currentProduct = null ;
        super.finish() ;
    }


    /**
     * Sends Email to Product owner
     * @param view
     */
    public void orderProduct(View view)
    {
        if(currentProduct != null && currentProduct.supplierMail != null)
        {
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO,
                    Uri.fromParts("mailto", currentProduct.supplierMail, null)) ;
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Order the Product.") ;
            String orderBody = "Product to order: "
                    + currentProduct.name + "\n\n"
                    + "Quantity: 1\n"
                    + "Price: " + currentProduct.price ;
            emailIntent.putExtra(Intent.EXTRA_TEXT, orderBody) ;
            startActivity(Intent.createChooser(emailIntent, "Order product...")) ;
            updateQuantityByArg(-1, true) ;
        }
    }

    /**
     * Method deletes the current Product from Database only if user agree
     * @param view
     */
    public void deleteProductFromDB(View view)
    {
        new AlertDialog.Builder(this)
                .setTitle("Delete current Product")
                .setMessage("Are you sure you want to delete this Product?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        DetatilsActivity.this.deleteCurrentProduct() ;
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {}
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show() ;
    }

    /**
     * Method increases the current Product quantity by some argument from input
     * @param view
     */
    public void increaseQuantityByArg(View view)
    {
        updateQuantityByArg(1, false) ;
    }

    /**
     * Method decreases the current Product quantity by some argument from input
     * @param view
     */
    public void decreaseQuantityByArg(View view)
    {
        updateQuantityByArg(-1, false) ;
    }

    private void deleteCurrentProduct()
    {
        SQLiteDatabase db = MainActivity.getDBH().getWritableDatabase() ;
        AllProducts.deleteProductFromDB(db, currentProduct) ;
        MainActivity.updateListViewAdapter(currentProduct) ;
        this.finish() ;
    }

    private void updateQuantityByArg(int arg, boolean isUseInputValue)
    {
        EditText quantityArgEdit = (EditText) findViewById(R.id.detailsProductQuantityUpdateEdit) ;
        if(quantityArgEdit != null)
        {
            /** Hide virtual keyboard **/
            InputMethodManager inputMgr = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE) ;
            inputMgr.hideSoftInputFromWindow(quantityArgEdit.getWindowToken(), 0) ;

            if(!isUseInputValue)
            {
                String value = quantityArgEdit.getText().toString() ;
                boolean isValueValidInt = InputsHelper.checkIsStringValidInteger(value) ;

                if(!isValueValidInt)
                {
                    quantityArgEdit.setTextColor(getResources().getColor(R.color.colorTextError)) ;
                }
                else
                {
                    quantityArgEdit.setTextColor(getResources().getColor(R.color.colorTextSuccess)) ;
                    int quantityArgValue = Integer.parseInt(value) ;
                    quantityArgValue *= arg ;
                    updateQuantity(quantityArgValue) ;
                }
            }
            else
            {
                updateQuantity(arg) ;
            }
        }
    }

    /**
     * Updates all information about current Product on current Activity
     */
    private void updateInformationAboutProduct()
    {
        TextView productName = (TextView) findViewById(R.id.detailsProductNameContentView) ;
        TextView productQuantity = (TextView) findViewById(R.id.detailsProductQuantityContentView) ;
        TextView productPrice = (TextView) findViewById(R.id.detailsProductPriceContentView) ;
        TextView productSupplierMail = (TextView) findViewById(R.id.detailsProductMailContentView) ;

        if(productName != null && productQuantity != null
                && productPrice != null && productSupplierMail != null)
        {
            productName.setText(currentProduct.name.trim()) ;
            productQuantity.setText(String.valueOf(currentProduct.quantity)) ;
            productPrice.setText(String.valueOf(currentProduct.price)) ;
            productSupplierMail.setText(currentProduct.supplierMail.trim()) ;
        }

        Button orderButton = (Button) findViewById(R.id.detailsProductButtonOrder) ;
        if(orderButton != null)
        {
            if(currentProduct.quantity == 0)
            {
                orderButton.setBackgroundResource(R.drawable.button_bg_disabled) ;
                orderButton.setEnabled(false) ;
            }
            else
            {
                orderButton.setBackgroundResource(R.drawable.button_bg) ;
                orderButton.setEnabled(false) ;
            }
        }
    }

    private void updateQuantity(int arg)
    {
        DBHelper db = MainActivity.getDBH() ;
        AllProducts.updateProductQuantity(db, arg, currentProduct) ;
        updateInformationAboutProduct() ;
        MainActivity.updateListViewAdapter(currentProduct) ;
    }
}
