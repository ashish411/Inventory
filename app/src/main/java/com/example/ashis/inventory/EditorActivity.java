package com.example.ashis.inventory;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ashis.inventory.data.InventoryContract;

/**
 * Created by ashis on 10/30/2016.
 */
public class EditorActivity extends AppCompatActivity {
    private EditText prodName,prodSupp,prodQty,prodPrice;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        prodName = (EditText) findViewById(R.id.inputProdName);
        prodSupp = (EditText) findViewById(R.id.inputProdSupp);
        prodQty=(EditText) findViewById(R.id.inputProditems);
        prodPrice=(EditText) findViewById(R.id.inputProdPrice);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_save : saveInventory();
                                    finish();
                break;

        }
    return super.onOptionsItemSelected(item);
    }

    private void saveInventory() {


        ContentValues values = new ContentValues();
        values.put(InventoryContract.InventoryEntry.COLLUMN_PROD_NAME,prodName.getText().toString());
        values.put(InventoryContract.InventoryEntry.COLLUMN_PROD_SUPPLIER,prodSupp.getText().toString());
        values.put(InventoryContract.InventoryEntry.COLLUMN_PROD_QTY,Integer.parseInt(prodQty.getText().toString()));
        values.put(InventoryContract.InventoryEntry.COLLUMN_PROD_PRICE,Integer.parseInt(prodPrice.getText().toString()));
        values.put(InventoryContract.InventoryEntry.COLLUMN_PROD_PICTURE,"56+566+");
        Uri newUri = getContentResolver().insert(InventoryContract.InventoryEntry.CONTENT_URI,values);
        Toast.makeText(getApplicationContext(),"item inserted",Toast.LENGTH_SHORT).show();
    }
}
