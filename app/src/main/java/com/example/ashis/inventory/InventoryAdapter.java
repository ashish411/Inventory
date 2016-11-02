package com.example.ashis.inventory;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ashis.inventory.data.InventoryContract;
import com.example.ashis.inventory.data.InventoryProvider;

import java.sql.Blob;

/**
 * Created by ashis on 10/29/2016.
 */
public class InventoryAdapter extends CursorAdapter {
    public int qtyRem;

    public InventoryAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.inventory_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);
        return view;
    }


    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {
        int qtyrem;
        final ViewHolder viewHolder = (ViewHolder) view.getTag();
        final String name = cursor.getString(cursor.getColumnIndex(InventoryContract.InventoryEntry.COLLUMN_PROD_NAME));
        final String qty = String.valueOf(cursor.getInt(cursor.getColumnIndex(InventoryContract.InventoryEntry.COLLUMN_PROD_QTY)));
        String price = String.valueOf(cursor.getInt(cursor.getColumnIndex(InventoryContract.InventoryEntry.COLLUMN_PROD_PRICE)));
        String supp = cursor.getString(cursor.getColumnIndex(InventoryContract.InventoryEntry.COLLUMN_PROD_SUPPLIER));
        // byte[] imageBlob = cursor.getBlob(cursor.getColumnIndex(InventoryContract.InventoryEntry.COLLUMN_PROD_PICTURE));
        final int id = cursor.getInt(cursor.getColumnIndex(InventoryContract.InventoryEntry._ID));
        Log.i("idAdapter", String.valueOf(id));
        viewHolder.prodName.setText(name);
        viewHolder.prodQty.setText(qty);
        viewHolder.prodPrice.setText(price);
        viewHolder.prodSupp.setText(supp);
        //  viewHolder.prodImage.setImageBitmap(DbBitmapUtility.getImage(imageBlob));
        viewHolder.buttonSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                Uri uri = ContentUris.withAppendedId(InventoryContract.InventoryEntry.CONTENT_URI, id);

                int qtyrem = Integer.parseInt(viewHolder.prodQty.getText().toString());
                qtyrem--;
                if (qtyrem < 1) {
                    viewHolder.buttonSell.setVisibility(View.GONE);
                    viewHolder.prodQty.setText("Out of stock");
                    Toast.makeText(context, "Sorry product is out of stock", Toast.LENGTH_SHORT).show();
                } else {
                    values.put(InventoryContract.InventoryEntry.COLLUMN_PROD_NAME, viewHolder.prodName.getText().toString());
                    values.put(InventoryContract.InventoryEntry.COLLUMN_PROD_QTY, qtyrem);
                    values.put(InventoryContract.InventoryEntry.COLLUMN_PROD_PRICE, Integer.parseInt(viewHolder.prodPrice.getText().toString()));
                    values.put(InventoryContract.InventoryEntry.COLLUMN_PROD_SUPPLIER, viewHolder.prodSupp.getText().toString());

                    context.getContentResolver().update(uri, values, null, null);
                    viewHolder.prodQty.setText(String.valueOf(qtyrem));
                    Toast.makeText(context, "Inventory Updated", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public static class ViewHolder {
        public final TextView prodName;
        public final TextView prodQty;
        public final TextView prodPrice;
        public final Button buttonSell;
        //  public final ImageView prodImage;
        public final TextView prodSupp;

        public ViewHolder(View view) {
            prodName = (TextView) view.findViewById(R.id.prod_name);
            prodQty = (TextView) view.findViewById(R.id.prod_qty);
            prodPrice = (TextView) view.findViewById(R.id.prod_price);
            buttonSell = (Button) view.findViewById(R.id.buttonSales);
            //   prodImage = (ImageView) view.findViewById(R.id.imageAdapter);
            prodSupp = (TextView) view.findViewById(R.id.suppAdapter);

        }
    }
}
