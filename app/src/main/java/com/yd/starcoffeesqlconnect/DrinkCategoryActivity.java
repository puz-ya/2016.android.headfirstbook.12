package com.yd.starcoffeesqlconnect;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class DrinkCategoryActivity extends ListActivity {

    private SQLiteDatabase mDB;
    private Cursor mCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //get View
        ListView listDrinks = getListView();

        try{
            SQLiteOpenHelper sqLiteOpenHelper = new StarCoffeeSQLHelper(this);
            mDB = sqLiteOpenHelper.getReadableDatabase();

            mCursor = mDB.query(StarCoffeeSQLHelper.TABLE_NAME,
                    new String[]{"_id, NAME"},
                    null, null, null, null, null);

            //we need only names
            CursorAdapter cursorAdapter = new SimpleCursorAdapter(this,
                    android.R.layout.simple_list_item_1,
                    mCursor,
                    new String[] {"NAME"},
                    new int[] {android.R.id.text1},
                    0);

            listDrinks.setAdapter(cursorAdapter);

        }catch (SQLException ex){
            Toast.makeText(this, "DB failed: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }

        //set data from Drink.java
        //ArrayAdapter<Drink> listAdapter = new ArrayAdapter<Drink>(this, android.R.layout.simple_list_item_1,Drink.drinks);
        //set Adapter
        //listDrinks.setAdapter(listAdapter);
    }

    @Override
    public void onListItemClick(ListView view, View viewItem, int position, long id){
        Intent intent = new Intent(this, DrinkActivity.class);
        intent.putExtra(DrinkActivity.EXTRA_DRINKNUM, (int)id);
        startActivity(intent);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();

        mCursor.close();
        mDB.close();
    }

}
