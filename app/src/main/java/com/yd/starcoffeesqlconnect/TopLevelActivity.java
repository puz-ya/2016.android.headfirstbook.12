package com.yd.starcoffeesqlconnect;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class TopLevelActivity extends AppCompatActivity {

    private SQLiteDatabase mDB;
    private Cursor mCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_level);

        ListView listView = (ListView) findViewById(R.id.toplist);
        listView.setOnItemClickListener(mOnItemClickListener);

        ListView listViewFav = (ListView) findViewById(R.id.top_listview_fav);
        try{
            SQLiteOpenHelper sqLiteOpenHelper = new StarCoffeeSQLHelper(this);
            mDB = sqLiteOpenHelper.getReadableDatabase();

            mCursor = mDB.query(StarCoffeeSQLHelper.TABLE_NAME,
                    new String[]{"_id, NAME"},
                    "FAVOURITE = 1",
                    null, null, null, null);

            CursorAdapter cursorAdapter = new SimpleCursorAdapter(this,
                    android.R.layout.simple_list_item_1,
                    mCursor,
                    new String[]{"NAME"},
                    new int[] {android.R.id.text1},
                    0);
            listViewFav.setAdapter(cursorAdapter);

        }catch(SQLException ex){
            Toast.makeText(this, "FAIL to Connect: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }

        listViewFav.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> listView, View v, int position, long id){

                Intent intent = new Intent(TopLevelActivity.this, DrinkActivity.class);
                intent.putExtra(DrinkActivity.EXTRA_DRINKNUM, (int)id);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    AdapterView.OnItemClickListener mOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> listView, View itemView, int position, long id) {
            //select Drinks
            if(position == 0){
                Intent intent = new Intent(TopLevelActivity.this, DrinkCategoryActivity.class);
                startActivity(intent);
            }

            //select other 2...
        }
    };

    @Override
    public void onDestroy(){
        super.onDestroy();

        mCursor.close();
        mDB.close();
    }

    @Override
    public void onRestart(){
        super.onRestart();

        try{
            StarCoffeeSQLHelper starCoffeeSQLHelper = new StarCoffeeSQLHelper(this);
            mDB = starCoffeeSQLHelper.getReadableDatabase();

            //same as previous
            Cursor cursor = mDB.query(StarCoffeeSQLHelper.TABLE_NAME,
                    new String[] { "_id", "NAME"},
                    "FAVOURITE = 1",
                    null, null, null, null);

            ListView listFav = (ListView) findViewById(R.id.top_listview_fav);
            CursorAdapter cursorAdapter = (CursorAdapter) listFav.getAdapter();
            cursorAdapter.changeCursor(cursor);
            mCursor = cursor;

        }catch(SQLException ex){
            Toast.makeText(this, "FAIL to Connect: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
