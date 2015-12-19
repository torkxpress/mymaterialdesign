package com.example.riaz.Custom_ToolBar_NavigationDrawer.activities;

import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.riaz.Custom_ToolBar_NavigationDrawer.R;


public class subAct extends AppCompatActivity {

    Toolbar tb ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        tb=(Toolbar) findViewById(R.id.myToolBar);
        setSupportActionBar(tb);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        /* actionBar.setHomeButtonEnabled(true) will just make the icon clickable,
        with the color at the background of the icon as a feedback of the click.

        BUT THERE WILL BE NO ICON WITHOUT setDisplayHomeAsUpEnabled(true)

       .*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sub, menu);
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

        if(id==android.R.id.home) // find the id of homebutton enabled... already on subactivity tool bar
        {
            NavUtils.navigateUpFromSameTask(this);
            // "this" refers to the source activity, from which user is attempting to navigate up.
            // this method should be used when source activity and the corresponding activity are within the same task
            //use shouldUpRecreateTask(Activity, intent) in case of navigation across tasks..

            //IMPORTANT
            /* YOU MUST ALSO DECLARE THE MAIN ACTIVITY AS PARENT ACTIVITY FOR SUB ACTIVITY IN THE MAINFEST FILE*/
        }

        return super.onOptionsItemSelected(item);
    }
}
