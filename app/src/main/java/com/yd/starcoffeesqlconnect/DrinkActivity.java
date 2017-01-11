package com.yd.starcoffeesqlconnect;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DrinkActivity extends AppCompatActivity {

    public static final String EXTRA_DRINKNUM = "drinkNum";

    private String mName;
    private String mDescr;
    private int mImageID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);

        int drinkNum = getIntent().getIntExtra(EXTRA_DRINKNUM, 0);
        //OR int drinkNo = (Integer)getIntent().getExtras().get(EXTRA_DRINKNO);

        try{
            SQLiteOpenHelper sqLiteOpenHelper = new StarCoffeeSQLHelper(this);
            SQLiteDatabase db = sqLiteOpenHelper.getWritableDatabase();

            Cursor cursor = db.query(StarCoffeeSQLHelper.TABLE_NAME,
                    new String[] {"NAME", "DESCRIPTION", "IMAGE"},
                    "_id = ?",
                    new String[] {Integer.toString(drinkNum+1)},
                    null, null, null);

            //get some
            if(cursor.moveToFirst()){
                mName = cursor.getString(0);
                mDescr = cursor.getString(1);
                mImageID = cursor.getInt(2);
            }

            //closing all
            cursor.close();
            db.close();

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
}
