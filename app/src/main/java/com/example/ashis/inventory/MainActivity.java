package com.example.ashis.inventory;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ashis.inventory.data.InventoryContract;
import com.example.ashis.inventory.data.InventoryDbHelper;

public class MainActivity extends AppCompatActivity {
    private TextView textView;

    private Cursor cursor;
    private ListView listView;
    private InventoryAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        displayDbData();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),EditorActivity.class);
                startActivity(intent);
            }
        });
    }



    private void displayDbData() {
       InventoryDbHelper mDbHelper = new InventoryDbHelper(this);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        String[] projection = {InventoryContract.InventoryEntry._ID,InventoryContract.InventoryEntry.COLLUMN_PROD_NAME, InventoryContract.InventoryEntry.COLLUMN_PROD_SUPPLIER};
        cursor = db.query(InventoryContract.InventoryEntry.TABLE_NAME,projection,null,null,null,null,null);
        listView = (ListView) findViewById(R.id.list);
        mAdapter = new InventoryAdapter(this,cursor);
        listView.setAdapter(mAdapter);

    }
    private void insert(){
        ContentValues values = new ContentValues();
        values.put(InventoryContract.InventoryEntry.COLLUMN_PROD_NAME,"iphone");
        values.put(InventoryContract.InventoryEntry.COLLUMN_PROD_SUPPLIER,"Apple");
        values.put(InventoryContract.InventoryEntry.COLLUMN_PROD_QTY,100);
        values.put(InventoryContract.InventoryEntry.COLLUMN_PROD_PRICE,57000);
        Uri newUri = getContentResolver().insert(InventoryContract.InventoryEntry.CONTENT_URI,values);

    }
}
