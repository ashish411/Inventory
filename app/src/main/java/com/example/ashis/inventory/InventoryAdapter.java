package com.example.ashis.inventory;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.ashis.inventory.data.InventoryContract;

/**
 * Created by ashis on 10/29/2016.
 */
public class InventoryAdapter extends CursorAdapter {
    public int qtyRem;
    public InventoryAdapter(Context context, Cursor c) {
        super(context, c);
    }
    public TextView prod_name,prod_qty,prod_price;
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.inventory_list_item,parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
         prod_name = (TextView) view.findViewById(R.id.prod_name);
         prod_qty = (TextView) view.findViewById(R.id.prod_qty);
         prod_price = (TextView) view.findViewById(R.id.prod_price);
        Button buttonSell = (Button) view.findViewById(R.id.buttonSales);
        prod_name.setText(cursor.getString(cursor.getColumnIndex(InventoryContract.InventoryEntry.COLLUMN_PROD_NAME)));
        prod_qty.setText(String.valueOf(cursor.getInt(cursor.getColumnIndex(InventoryContract.InventoryEntry.COLLUMN_PROD_QTY))));
        prod_price.setText(String.valueOf(cursor.getInt(cursor.getColumnIndex(InventoryContract.InventoryEntry.COLLUMN_PROD_PRICE))));
        qtyRem = Integer.parseInt(prod_qty.getText().toString());
        if (qtyRem<0)
            buttonSell.setVisibility(View.GONE);
        buttonSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    qtyRem--;
                prod_qty.setText(String.valueOf(qtyRem));
            }
        });
    }
}
