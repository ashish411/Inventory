package com.example.ashis.inventory.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ashis on 10/29/2016.
 */
public class InventoryDbHelper extends SQLiteOpenHelper {
    public static final int DATABSE_VERSION = 5;
    public static final String DATABSE_NAME = "inventory.db";

    public InventoryDbHelper(Context context) {
        super(context,DATABSE_NAME,null,DATABSE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String InventoryCreateTable = "CREATE TABLE "+ InventoryContract.InventoryEntry.TABLE_NAME + " (" +
                // INTEGER PRIMARY KEY AUTOINCREMENT
                InventoryContract.InventoryEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                InventoryContract.InventoryEntry.COLLUMN_PROD_NAME + " TEXT NOT NULL , "+
                InventoryContract.InventoryEntry.COLLUMN_PROD_PRICE + " INTEGER  , "+
                InventoryContract.InventoryEntry.COLLUMN_PROD_QTY + " INTEGER DEFAULT 0 , "+
                InventoryContract.InventoryEntry.COLLUMN_PROD_SUPPLIER + " TEXT , "+
                InventoryContract.InventoryEntry.COLLUMN_PROD_PICTURE + " BLOB );";
        db.execSQL(InventoryCreateTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + InventoryContract.InventoryEntry.TABLE_NAME);
        onCreate(db);
    }
}
