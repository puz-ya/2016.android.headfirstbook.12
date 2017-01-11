package com.yd.starcoffeesqlconnect;

/**
 * Created by YD on 04.01.2017.
 */

public class Drink {

    private String mName;
    private String mDescrip;
    private int mImageID;

    public static final Drink[] drinks = {
            new Drink("Latte", "First drink", R.drawable.latte),
            new Drink("Cappuccino", "Second drink", R.drawable.cappuccino),
            new Drink("Filter", "The third drink", R.drawable.filter)
    };

    private Drink(String s1, String s2, int id){
        this.mName = s1;
        this.mDescrip = s2;
        this.mImageID = id;
    }

    public String getName(){
        return mName;
    }

    public String getDescrip(){
        return mDescrip;
    }

    public int getImageID(){
        return mImageID;
    }

    public String toString(){
        return mName;
    }
}
