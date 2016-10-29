package com.example.ashis.inventory;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ashis.inventory.data.InventoryContract;
import com.example.ashis.inventory.data.InventoryDbHelper;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private InventoryDbHelper inventoryDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView=(TextView) findViewById(R.id.textView);
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inventoryDb = new InventoryDbHelper(getApplicationContext());
                SQLiteDatabase db = inventoryDb.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(InventoryContract.InventoryEntry.COLLUMN_PROD_NAME,"pixel");
                values.put(InventoryContract.InventoryEntry.COLLUMN_PROD_PRICE,"67000");
                values.put(InventoryContract.InventoryEntry.COLLUMN_PROD_QTY,"1000");
                values.put(InventoryContract.InventoryEntry.COLLUMN_PROD_SUPPLIER,"GOOGLE");
                values.put(InventoryContract.InventoryEntry.COLLUMN_PROD_PICTURE,"kjkj");
                long newRow =   db.insert(InventoryContract.InventoryEntry.TABLE_NAME,null,values);
                displayDbData();
            }
        });

    }

    private void displayDbData() {
        inventoryDb = new InventoryDbHelper(this);
        SQLiteDatabase db = inventoryDb.getReadableDatabase();
        Cursor cursor = db.query(InventoryContract.InventoryEntry.TABLE_NAME,null,null,null,null,null,null);
        textView.setText(InventoryContract.InventoryEntry.COLLUMN_PROD_NAME + "-"+
                InventoryContract.InventoryEntry.COLLUMN_PROD_PICTURE + "-"+
                InventoryContract.InventoryEntry.COLLUMN_PROD_SUPPLIER + "-"+
                InventoryContract.InventoryEntry.COLLUMN_PROD_QTY + "-"+
                InventoryContract.InventoryEntry.COLLUMN_PROD_PRICE);
        textView.append("\n no of rows inserted "+cursor.getCount());

    }
}
