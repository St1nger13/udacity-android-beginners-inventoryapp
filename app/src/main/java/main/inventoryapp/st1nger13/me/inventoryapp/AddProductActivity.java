package main.inventoryapp.st1nger13.me.inventoryapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddProductActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
    }

    /** Add new project into Database and show it in MainActivity
     *  This method checks if all inputs from user correct, and in this
     *  case create a new Product. In other case the fielss with incorrect
     *  values will be highlighted with red color.
     * @param view
     */
    public void addProduct(View view)
    {
        DBHelper db = MainActivity.getDBH() ;

        EditText nameEdit = (EditText) findViewById(R.id.addProductNameEdit) ;
        EditText quantityEdit = (EditText) findViewById(R.id.addProductQuantityEdit) ;
        EditText priceEdit = (EditText) findViewById(R.id.addProductPriceEdit) ;
        EditText supplierMailEdit = (EditText) findViewById(R.id.addProductSupplierMailEdit) ;

        String name = nameEdit.getText().toString() ;
        String quantityStr = quantityEdit.getText().toString() ;
        String priceStr = priceEdit.getText().toString() ;
        String supplierMail = supplierMailEdit.getText().toString() ;

        boolean isNameValid = InputsHelper.checkAddProductInputName(name) ;
        boolean isQuantityValid = InputsHelper.checkIsStringValidInteger(quantityStr) ;
        boolean isPriceValid = InputsHelper.checkIsStringValidInteger(priceStr) ;
        boolean isSupplierMailValid = InputsHelper.checkAddProductInputMailAddress(supplierMail) ;

        int editTextColorNormal = getResources().getColor(R.color.colorTextSecondary) ;
        int editTextColorSuccess = getResources().getColor(R.color.colorTextSuccess) ;
        int editTextColorError = getResources().getColor(R.color.colorTextError) ;

        nameEdit.setTextColor(editTextColorNormal) ;
        quantityEdit.setTextColor(editTextColorNormal) ;
        priceEdit.setTextColor(editTextColorNormal) ;
        supplierMailEdit.setTextColor(editTextColorNormal) ;

        if(isNameValid && isQuantityValid && isPriceValid && isSupplierMailValid)
        {
            int quantity = Integer.parseInt(quantityStr) ;
            int price = Integer.parseInt(priceStr) ;

            Product product = AllProducts.addProduct(name, quantity, price, supplierMail, db) ;
            if(product != null)
                MainActivity.updateListViewAdapter(product) ;

            openMainActivity(view) ;
        }
        else
        {
            if(!isNameValid)
                nameEdit.setTextColor(editTextColorError) ;
            else
                nameEdit.setTextColor(editTextColorSuccess) ;

            if(!isQuantityValid)
                quantityEdit.setTextColor(editTextColorError) ;
            else
                quantityEdit.setTextColor(editTextColorSuccess) ;

            if(!isPriceValid)
                priceEdit.setTextColor(editTextColorError) ;
            else
                priceEdit.setTextColor(editTextColorSuccess) ;

            if(!isSupplierMailValid)
                supplierMailEdit.setTextColor(editTextColorError) ;
            else
                supplierMailEdit.setTextColor(editTextColorSuccess) ;
        }
    }

    /**
     * Close current Activity and return to MainActivity
     * @param view
     */
    public void openMainActivity(View view)
    {
        this.finish() ;
    }
}
