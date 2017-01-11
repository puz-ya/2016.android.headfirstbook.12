package com.yd.starcoffeesqlconnect;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class DrinkActivity extends AppCompatActivity {

    public static final String EXTRA_DRINKNUM = "drinkNum";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);

        int drinkNum = getIntent().getIntExtra(EXTRA_DRINKNUM, 0);
        //OR int drinkNo = (Integer)getIntent().getExtras().get(EXTRA_DRINKNO);

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
    }
}
