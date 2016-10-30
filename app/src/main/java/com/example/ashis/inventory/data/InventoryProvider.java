package com.example.ashis.inventory.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by ashis on 10/29/2016.
 */
public class InventoryProvider extends ContentProvider {
    private static final int INVENTORY = 100;
    private static final int INVENTORY_ID = 101;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        sUriMatcher.addURI(InventoryContract.CONTENT_AUTHORITY,InventoryContract.PATH_INVENTORY,INVENTORY);
        sUriMatcher.addURI(InventoryContract.CONTENT_AUTHORITY,InventoryContract.PATH_INVENTORY+"/#",INVENTORY_ID);
    }
    private InventoryDbHelper mDbHelper;
    @Override
    public boolean onCreate() {
        mDbHelper = new InventoryDbHelper(getContext());
        return true;
    }



    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor cursor;
        int match = sUriMatcher.match(uri);
        switch (match){
            case INVENTORY: cursor = db.query(InventoryContract.InventoryEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,null);
                            break;
            case INVENTORY_ID: selection = InventoryContract.InventoryEntry._ID + "=?";
                                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                                cursor=db.query(InventoryContract.InventoryEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,null);
                                break;
            default: throw new IllegalArgumentException("Cannot query Unknown Uri "+uri);
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
      int match = sUriMatcher.match(uri);
        switch (match){
            case INVENTORY: return InventoryContract.CONTENT_LIST_TYPE;
            case INVENTORY_ID: return InventoryContract.CONTENT_ITEM_TYPE;
            default: throw new IllegalArgumentException("Unknown uri "+uri+ " match id "+match);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.i("uri", String.valueOf(uri));
        int match = sUriMatcher.match(uri);
        switch (match){
            case INVENTORY : return insertInventory(uri,values);

            default: throw new IllegalArgumentException("Insertion is not supported for this " + uri);
        }

    }

    private Uri insertInventory(Uri uri, ContentValues values) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        long id = db.insert(InventoryContract.InventoryEntry.TABLE_NAME,null,values);
        if (id == -1) {return null;}
        return ContentUris.withAppendedId(uri,id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        switch (match){
            case INVENTORY: return db.delete(InventoryContract.InventoryEntry.TABLE_NAME,selection,selectionArgs);
            case INVENTORY_ID : selection = InventoryContract.InventoryEntry._ID + "=?";
                                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                                return db.delete(InventoryContract.InventoryEntry.TABLE_NAME,selection,selectionArgs);
            default: throw new IllegalArgumentException("Delete operation not valid for this uri "+uri);
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
       int match = sUriMatcher.match(uri);
        switch (match){
            case INVENTORY: return updateInventory(uri,values,selection,selectionArgs);
            case INVENTORY_ID: selection = InventoryContract.InventoryEntry._ID + "=?";
                                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                                return updateInventory(uri,values,selection,selectionArgs);
            default: throw new IllegalArgumentException("Update is not supported for this uri "+uri);
            }
    }

    private int updateInventory(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        return db.update(InventoryContract.InventoryEntry.TABLE_NAME,values,selection,selectionArgs);

    }
}
