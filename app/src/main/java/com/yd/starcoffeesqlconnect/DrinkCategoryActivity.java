package com.yd.starcoffeesqlconnect;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class DrinkCategoryActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //get View
        ListView listDrinks = getListView();
        //set data from Drink.java
        ArrayAdapter<Drink> listAdapter = new ArrayAdapter<Drink>(this, android.R.layout.simple_list_item_1,Drink.drinks);
        //set Adapter
        listDrinks.setAdapter(listAdapter);
    }

    @Override
    public void onListItemClick(ListView view, View viewItem, int position, long id){
        Intent intent = new Intent(this, DrinkActivity.class);
        intent.putExtra(DrinkActivity.EXTRA_DRINKNUM, (int)id);
        startActivity(intent);
    }

}
