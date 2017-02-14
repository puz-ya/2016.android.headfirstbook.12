package com.yd.starcoffeesqlconnect;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DrinkActivity extends AppCompatActivity {

    public static final String EXTRA_DRINKNUM = "drinkNum";

    private String mName;
    private String mDescr;
    private int mImageID;
    private boolean mFav;

    private SQLiteDatabase mDB;
    private Cursor mCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);

        int drinkNum = getIntent().getIntExtra(EXTRA_DRINKNUM, 0);
        //OR int drinkNo = (Integer)getIntent().getExtras().get(EXTRA_DRINKNO);

        try{
            SQLiteOpenHelper sqLiteOpenHelper = new StarCoffeeSQLHelper(this);
            SQLiteDatabase mDB = sqLiteOpenHelper.getWritableDatabase();

            mCursor = mDB.query(StarCoffeeSQLHelper.TABLE_NAME,
                    new String[] {"NAME", "DESCRIPTION", "IMAGE", "FAVOURITE"},
                    "_id = ?",
                    new String[] {Integer.toString(drinkNum)},
                    null, null, null);

            //get some
            if(mCursor.moveToFirst()){
                mName = mCursor.getString(0);
                mDescr = mCursor.getString(1);
                mImageID = mCursor.getInt(2);
                mFav = (mCursor.getInt(3) == 1);
            }

            //closing all
            mCursor.close();
            mDB.close();

        }catch(SQLException ex){
            Toast.makeText(this, "FAIL to Connect: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }

        //set all Views
        ImageView photo = (ImageView) findViewById(R.id.photo);
        photo.setImageResource(mImageID);
        photo.setContentDescription(mName);

        TextView name = (TextView) findViewById(R.id.name);
        name.setText(mName);

        TextView descr = (TextView) findViewById(R.id.description);
        descr.setText(mDescr);

        //favourite drink
        CheckBox checkBox = (CheckBox) findViewById(R.id.favourite);
        checkBox.setChecked(mFav);

        /*
        //get more info
        Drink drink = Drink.drinks[drinkNum];

        //set all Views
        ImageView photo = (ImageView) findViewById(R.id.photo);
        photo.setImageResource(drink.getImageID());
        photo.setContentDescription(drink.getName());

        TextView name = (TextView) findViewById(R.id.name);
        name.setText(drink.getName());

        TextView descr = (TextView) findViewById(R.id.description);
        descr.setText(drink.getDescrip());
        //*/
    }

    public void onFavourite(View view){
        int drinkNum = getIntent().getIntExtra(EXTRA_DRINKNUM, 0);

        new UpdateDrinkTask().execute(drinkNum);

        /* old version
        CheckBox checkBox = (CheckBox) findViewById(R.id.favourite);

        ContentValues drinks = new ContentValues();
        drinks.put("FAVOURITE", checkBox.isChecked());

        try{
            SQLiteOpenHelper sqLiteOpenHelper = new StarCoffeeSQLHelper(this);
            SQLiteDatabase mDB = sqLiteOpenHelper.getWritableDatabase();

            mDB.update(StarCoffeeSQLHelper.TABLE_NAME,
                    drinks,
                    "_id = ?",
                    new String[] {Integer.toString(drinkNum)});

            //closing all
            mDB.close();

        }catch(SQLiteException ex){
            Toast.makeText(this, "FAIL to Connect: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        } */

    }

    @Override
    public void onDestroy(){
        super.onDestroy();

    }

    private class UpdateDrinkTask extends AsyncTask<Integer, Void, Boolean> {
        ContentValues drinkValues;

        protected void onPreExecute(){
            CheckBox checkBox = (CheckBox) findViewById(R.id.favourite);
            drinkValues = new ContentValues();
            drinkValues.put("FAVOURITE", checkBox.isChecked());
        }

        protected Boolean doInBackground(Integer... drinks){
            int drinkNo = drinks[0];
            SQLiteOpenHelper starCoffeeSQLHelper = new StarCoffeeSQLHelper(DrinkActivity.this);
            try {
                SQLiteDatabase db = starCoffeeSQLHelper.getWritableDatabase();
                db.update(StarCoffeeSQLHelper.TABLE_NAME, drinkValues,
                        "_id = ?", new String[] {Integer.toString(drinkNo)});
                db.close();
                return true;
            } catch(SQLiteException e) {
                return false;
            }

        }

        /*
        protected void onProgressUpdate(Integer... progress){
            setProgress(progress[0]);
        }
        //*/

        protected void onPostExecute(Boolean success){
            if(!success){
                Toast.makeText(DrinkActivity.this, "DB failed", Toast.LENGTH_LONG).show();
            }
        }
    }
}
