package com.yd.starcoffeesqlconnect;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by YD on 09.01.2017.
 */

public class StarCoffeeSQLHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "starcoffee_db";   //db name
    public static final int DB_VERSION = 2; //db version
    public static final String TABLE_NAME = "DRINKS";

    StarCoffeeSQLHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        updateMyDB(db, 0, DB_VERSION);
    }

    public void insertDrink(SQLiteDatabase db, String name, String descr, int imageID, int fav){
        ContentValues drinkValues = new ContentValues();
        drinkValues.put("NAME", name);
        drinkValues.put("DESCRIPTION", descr);
        drinkValues.put("IMAGE", imageID);
        drinkValues.put("FAVOURITE", fav);
        db.insert(TABLE_NAME, null, drinkValues);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        updateMyDB(db, oldVersion, newVersion);
    }

    public void updateMyDB(SQLiteDatabase db, int oldVer, int newVer){
        if(oldVer < 1){
            //db.execSQL("DROP TABLE "+TABLE_NAME+";");

            db.execSQL("CREATE TABLE "+TABLE_NAME+" (" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "NAME TEXT, " +
                    "DESCRIPTION TEXT, " +
                    "IMAGE INTEGER, " +
                    "FAVOURITE INTEGER); ");

            insertDrink(db, "Latte", "Espresso and steamed milk", R.drawable.latte, 0);
            insertDrink(db, "Cappuccino", "Espresso, hot milk and steamed-milk foam", R.drawable.cappuccino, 0);
            insertDrink(db, "Filter", "Our best drip coffee", R.drawable.filter, 0);
        }

        if(oldVer < 2){
            //code
            db.execSQL("ALTER TABLE " + TABLE_NAME + " ADD COLUMN FAVORITE NUMERIC;");
        }
        //*/
    }
}
