package com.example.ashis.inventory;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.ashis.inventory.data.InventoryContract;
import com.example.ashis.inventory.data.InventoryProvider;

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
    public void bindView(View view, final Context context, final Cursor cursor) {
         prod_name = (TextView) view.findViewById(R.id.prod_name);
         prod_qty = (TextView) view.findViewById(R.id.prod_qty);
         prod_price = (TextView) view.findViewById(R.id.prod_price);
        final Button buttonSell = (Button) view.findViewById(R.id.buttonSales);
        prod_name.setText(cursor.getString(cursor.getColumnIndex(InventoryContract.InventoryEntry.COLLUMN_PROD_NAME)));
        prod_qty.setText(String.valueOf(cursor.getInt(cursor.getColumnIndex(InventoryContract.InventoryEntry.COLLUMN_PROD_QTY))));
        prod_price.setText(String.valueOf(cursor.getInt(cursor.getColumnIndex(InventoryContract.InventoryEntry.COLLUMN_PROD_PRICE))));
        qtyRem = Integer.parseInt(prod_qty.getText().toString());
        buttonSell.setTag(cursor.getPosition());
            buttonSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (Integer) v.getTag();
                Log.i("num",String.valueOf(position));
                qtyRem--;
                prod_qty.setText(String.valueOf(qtyRem));

                 }
        });

    }


}
