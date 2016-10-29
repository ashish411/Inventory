package com.example.ashis.inventory.data;

import android.provider.BaseColumns;

/**
 * Created by ashis on 10/29/2016.
 */
public final class InventoryContract implements BaseColumns {

public static final class InventoryEntry{
    public static final String _ID = BaseColumns._ID;
    public static final String TABLE_NAME = "inventory";
    public static final String COLLUMN_PROD_NAME = "product";
    public static final String COLLUMN_PROD_PRICE = "price";
    public static final String COLLUMN_PROD_QTY="in_stock";
    public static final String COLLUMN_PROD_SUPPLIER = "supplier";
    public static final String COLLUMN_PROD_PICTURE = "picture";


}

}
