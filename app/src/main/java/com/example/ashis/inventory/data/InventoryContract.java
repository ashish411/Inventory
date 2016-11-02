package com.example.ashis.inventory.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;


public final class InventoryContract implements BaseColumns {
    public static final String CONTENT_AUTHORITY = "com.example.ashis.inventory";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_INVENTORY = "inventory";
    public static final String CONTENT_LIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_INVENTORY;
    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_INVENTORY;


    public static final class InventoryEntry {
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_INVENTORY);
        public static final String _ID = BaseColumns._ID;
        public static final String TABLE_NAME = "inventory";
        public static final String COLLUMN_PROD_NAME = "product";
        public static final String COLLUMN_PROD_PRICE = "price";
        public static final String COLLUMN_PROD_QTY = "in_stock";
        public static final String COLLUMN_PROD_SUPPLIER = "supplier";
        public static final String COLLUMN_PROD_PICTURE = "picture";


    }

}
