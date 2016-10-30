package com.example.ashis.inventory;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.ashis.inventory.data.InventoryContract;

/**
 * Created by ashis on 10/29/2016.
 */
public class InventoryAdapter extends CursorAdapter {
    public InventoryAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.activity_main,parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView prod_name = (TextView) view.findViewById(R.id.prod_name);
        TextView prod_supp = (TextView) view.findViewById(R.id.prod_supp);

        prod_name.setText(cursor.getString(cursor.getColumnIndex(InventoryContract.InventoryEntry.COLLUMN_PROD_NAME)));
        prod_supp.setText(cursor.getString(cursor.getColumnIndex(InventoryContract.InventoryEntry.COLLUMN_PROD_SUPPLIER)));

    }
}
