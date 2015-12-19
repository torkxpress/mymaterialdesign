package com.example.riaz.Custom_ToolBar_NavigationDrawer.materialtest;

import android.app.Application;
import android.content.Context;

import com.example.riaz.Custom_ToolBar_NavigationDrawer.database.MoviesDatabase;

/**
 * Created by riaz on 22/06/15.
 */
public class MyApplication extends Application {
    private static MyApplication sInstance;

    private static MoviesDatabase moviesDatabase;


    public static final String API_KEY_ROTTEN_TOMATOES ="43d2r2sdf65rj93ucnzxzvn4";




    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    public static MyApplication getsInstance()
    {
        return  sInstance;
    }

    public static Context getAppContext()
    {
        return sInstance.getApplicationContext();
    }


}
