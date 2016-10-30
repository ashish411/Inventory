package com.example.ashis.inventory;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ashis.inventory.data.InventoryContract;
import com.example.ashis.inventory.data.InventoryDbHelper;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private InventoryDbHelper inventoryDb;
    private Cursor cursor;
    private ListView listView;
    private InventoryAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView=(ListView) findViewById(R.id.list);
        mAdapter = new InventoryAdapter(this,cursor);
        listView.setAdapter(mAdapter);

    }

    private void displayDbData() {
        inventoryDb = new InventoryDbHelper(this);

        String[] projection = {InventoryContract.InventoryEntry.COLLUMN_PROD_NAME,
                InventoryContract.InventoryEntry.COLLUMN_PROD_SUPPLIER};
        cursor = getContentResolver().query(InventoryContract.InventoryEntry.CONTENT_URI,projection,null,null,null);
    }
    private void insert(){
        ContentValues values = new ContentValues();
        values.put(InventoryContract.InventoryEntry.COLLUMN_PROD_NAME,"iphone");
        values.put(InventoryContract.InventoryEntry.COLLUMN_PROD_SUPPLIER,"Apple");
        Uri newUri = getContentResolver().insert(InventoryContract.InventoryEntry.CONTENT_URI,values);
    }
}
