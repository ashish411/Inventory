package com.example.ashis.inventory;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ashis.inventory.data.InventoryContract;

/**
 * Created by ashis on 10/30/2016.
 */
public class EditorActivity extends AppCompatActivity {
    private EditText prodName, prodSupp, prodQty, prodPrice;
    private  Uri currentInvUri;
    private ImageView prodImage;
    private Bitmap bp;
    private Button buttonOrder;
    private byte[] imageByte;
    private String name,supp,qty;
    private static final int IMAGE_REQUEST_CODE = 1;
    private boolean itemDataChange = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        prodName = (EditText) findViewById(R.id.inputProdName);
        prodSupp = (EditText) findViewById(R.id.inputProdSupp);
        prodQty = (EditText) findViewById(R.id.inputProditems);
        prodPrice = (EditText) findViewById(R.id.inputProdPrice);
        prodImage = (ImageView) findViewById(R.id.prodImage);
        buttonOrder=(Button) findViewById(R.id.buttonOrder);
        //setting on touch listener for discarding of app
        prodName.setOnTouchListener(mTouchListener);
        prodSupp.setOnTouchListener(mTouchListener);
        prodQty.setOnTouchListener(mTouchListener);
        prodPrice.setOnTouchListener(mTouchListener);
        prodImage.setOnTouchListener(mTouchListener);
        Intent intent = getIntent();
        currentInvUri = intent.getData();
        Log.i("uri", String.valueOf(currentInvUri));
        if (currentInvUri == null) {
            setTitle("Add a Product");
        } else
            setTitle("Edit a product");
        if (currentInvUri != null) {
            String[] projection = {InventoryContract.InventoryEntry._ID,
                    InventoryContract.InventoryEntry.COLLUMN_PROD_NAME,
                    InventoryContract.InventoryEntry.COLLUMN_PROD_SUPPLIER,
                    InventoryContract.InventoryEntry.COLLUMN_PROD_QTY,
                    InventoryContract.InventoryEntry.COLLUMN_PROD_PRICE,
                    InventoryContract.InventoryEntry.COLLUMN_PROD_PICTURE};
            Cursor cursor = getContentResolver().query(currentInvUri, projection, null, null, null);
            if (cursor.moveToFirst()) {
                imageByte = cursor.getBlob(cursor.getColumnIndex(InventoryContract.InventoryEntry.COLLUMN_PROD_PICTURE));
                prodName.setText(cursor.getString(cursor.getColumnIndex(InventoryContract.InventoryEntry.COLLUMN_PROD_NAME)));
                prodSupp.setText(cursor.getString(cursor.getColumnIndex(InventoryContract.InventoryEntry.COLLUMN_PROD_SUPPLIER)));
                prodQty.setText(String.valueOf(cursor.getInt(cursor.getColumnIndex(InventoryContract.InventoryEntry.COLLUMN_PROD_QTY))));
                prodPrice.setText(String.valueOf(cursor.getInt(cursor.getColumnIndex(InventoryContract.InventoryEntry.COLLUMN_PROD_PRICE))));
                if (imageByte != null) {
                    bp = DbBitmapUtility.getImage(imageByte);
                    prodImage.setImageBitmap(bp);
                } else {
                    prodImage.setImageResource(R.drawable.ic_add_circle_outline_white_24dp);
                }
            }
        }
        prodImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, IMAGE_REQUEST_CODE);
            }
        });
        buttonOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("*/*");
                intent.putExtra(Intent.EXTRA_EMAIL,"customercare@amazon.in");
                intent.putExtra(Intent.EXTRA_SUBJECT,"request for the order placement ");
                Log.i("ashish",prodName.getText().toString());
                Log.i("ashish",prodSupp.getText().toString());
                intent.putExtra(Intent.EXTRA_TEXT,"Order Update: /n " +
                        "Name : "+prodName.getText().toString() +
                        "\n Supplier : "+prodSupp.getText().toString()+
                        "\n price :"+prodPrice.getText().toString());
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK) {
            bp = (Bitmap) data.getExtras().get("data");
            prodImage.setImageBitmap(bp);
            imageByte = DbBitmapUtility.getBytes(bp);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                saveInventory();
                finish();
                break;
            case R.id.delete:
                if (currentInvUri != null) {
                    DialogInterface.OnClickListener deletButton = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            NavUtils.navigateUpFromSameTask(EditorActivity.this);
                        }
                    };
                    showDeleteDialog(deletButton);
                   // getContentResolver().delete(currentInvUri, null, null);

                } else
                    Toast.makeText(getApplicationContext(), "delete operstion not supported while insertion of item", Toast.LENGTH_SHORT).show();
                break;
            case android.R.id.home:
                // Navigate back to parent activity (CatalogActivity)
                if (!itemDataChange) {
                    NavUtils.navigateUpFromSameTask(EditorActivity.this);
                    return true;
                }
                DialogInterface.OnClickListener discardButtonClickListener =
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // User clicked "Discard" button, navigate to parent activity.

                            }
                        };
                showUnsavedChangesDialog(discardButtonClickListener);
                return true;


        }
        return super.onOptionsItemSelected(item);
    }

    private void saveInventory() {
        String name = prodName.getText().toString();
        String supp = prodSupp.getText().toString();
        String qty = prodQty.getText().toString();
        String price = prodPrice.getText().toString();
        if (currentInvUri==null &&( TextUtils.isEmpty(name) || TextUtils.isEmpty(supp) || TextUtils.isEmpty(qty) || TextUtils.isEmpty(price))){
            Toast.makeText(getApplicationContext(),"some of the fields are empty",Toast.LENGTH_SHORT).show();
            return;
        }
        int qtyInt =0;
        if (!TextUtils.isEmpty(qty))
            qtyInt=Integer.parseInt(qty);
        int priceInt =0;
        if (!TextUtils.isEmpty(price))
            priceInt=Integer.parseInt(price);
        ContentValues values = new ContentValues();
        values.put(InventoryContract.InventoryEntry.COLLUMN_PROD_NAME, name);
        values.put(InventoryContract.InventoryEntry.COLLUMN_PROD_SUPPLIER, supp);
        values.put(InventoryContract.InventoryEntry.COLLUMN_PROD_QTY, qtyInt);
        values.put(InventoryContract.InventoryEntry.COLLUMN_PROD_PRICE,priceInt);
        values.put(InventoryContract.InventoryEntry.COLLUMN_PROD_PICTURE, imageByte);
        if (currentInvUri == null) {
            Uri newUri = getContentResolver().insert(InventoryContract.InventoryEntry.CONTENT_URI, values);
            Toast.makeText(getApplicationContext(), "product inserted", Toast.LENGTH_SHORT).show();
        } else {
            int updatedRow = getContentResolver().update(currentInvUri, values, null, null);
            Toast.makeText(getApplicationContext(), "product updated", Toast.LENGTH_SHORT).show();
        }

    }

    private void showUnsavedChangesDialog(DialogInterface.OnClickListener discardButtonClickListner) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Discard your changes and quit editing?");
        builder.setPositiveButton("Discard", discardButtonClickListner);
        builder.setNegativeButton("Keep Editing", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (dialog != null)
                    dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    private void showDeleteDialog(DialogInterface.OnClickListener discardButtonClickListner) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you really want to delete");
        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getContentResolver().delete(currentInvUri,null,null);
                Toast.makeText(getApplicationContext(), "product deleted", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (dialog != null)
                    dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    @Override
    public void onBackPressed() {
        if (!itemDataChange) {
            super.onBackPressed();
            return;
        } else {
            DialogInterface.OnClickListener discardButton = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            };
            showUnsavedChangesDialog(discardButton);
        }
    }

    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            itemDataChange = true;
            return false;
        }
    };
}


