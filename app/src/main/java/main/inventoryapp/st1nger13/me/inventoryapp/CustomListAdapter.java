package main.inventoryapp.st1nger13.me.inventoryapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

/**
 * Created by St1nger13 on 29.06.2016.
 */
public class CustomListAdapter extends ArrayAdapter<Product>
{
    private List<Product> items ;
    private int layoutResourceId ;
    private Context context ;


    public CustomListAdapter(Context context, int layoutResourceId, List<Product> items)
    {
        super(context, layoutResourceId, items) ;
        this.layoutResourceId = layoutResourceId ;
        this.context = context ;
        this.items = items ;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View row = convertView ;
        InventoryElementHolder holder = null ;

        LayoutInflater inflater = ((Activity) context).getLayoutInflater() ;
        row = inflater.inflate(layoutResourceId, parent, false) ;

        holder = new InventoryElementHolder() ;
        holder.element = items.get(position) ;
        holder.saleButton = (Button) row.findViewById(R.id.listItemSale) ;
        holder.saleButton.setTag(holder.element) ;
        holder.detailsButton = (Button) row.findViewById(R.id.listItemTitle) ;
        holder.detailsButton.setTag(holder.element) ;

        holder.title = (Button) row.findViewById(R.id.listItemTitle) ;
        holder.quantity = (TextView) row.findViewById(R.id.listItemQuantityView) ;
        holder.price = (TextView) row.findViewById(R.id.listItemPriceView) ;

        row.setTag(holder) ;
        setupItem(holder) ;

        return row ;
    }

    private void setupItem(InventoryElementHolder holder)
    {
        holder.title.setText(holder.element.getTitle().trim()) ;
        holder.quantity.setText(context.getString(R.string.quantity) + " " + holder.element.getQuantity()) ;
        holder.price.setText(context.getString(R.string.price) + " " + holder.element.getPrice());
    }

    public static class InventoryElementHolder
    {
        Product element ;
        Button title ;
        TextView quantity ;
        TextView price ;
        Button saleButton ;
        Button detailsButton ;
    }
}
