package com.example.riaz.Custom_ToolBar_NavigationDrawer.extras;

import com.android.volley.RequestQueue;
import com.example.riaz.Custom_ToolBar_NavigationDrawer.database.MoviesDatabase;
import com.example.riaz.Custom_ToolBar_NavigationDrawer.json.Parser;
import com.example.riaz.Custom_ToolBar_NavigationDrawer.json.Requestor;
import com.example.riaz.Custom_ToolBar_NavigationDrawer.materialtest.MyApplication;
import com.example.riaz.Custom_ToolBar_NavigationDrawer.pojo.Movie;

import org.json.JSONObject;


import java.util.ArrayList;

import static com.example.riaz.Custom_ToolBar_NavigationDrawer.extras.UrlEndPoints.URL_BOX_OFFICE;
import static com.example.riaz.Custom_ToolBar_NavigationDrawer.extras.UrlEndPoints.URL_CHAR_AMEPERSAND;
import static com.example.riaz.Custom_ToolBar_NavigationDrawer.extras.UrlEndPoints.URL_CHAR_QUESTION;
import static com.example.riaz.Custom_ToolBar_NavigationDrawer.extras.UrlEndPoints.URL_PARAM_API_KEY;
import static com.example.riaz.Custom_ToolBar_NavigationDrawer.extras.UrlEndPoints.URL_PARAM_LIMIT;

/**
 * Created by riaz on 31/08/15.
 */
public class MovieUtils {

    public static ArrayList<Movie> loadBoxOfficeMovies(RequestQueue requestQueue)
    {
        JSONObject jsonObject = Requestor.sendRequestBoxOfficeMovies(requestQueue, getRequestUrl(30));

        ArrayList<Movie> movieArrayList = Parser.parseJSONObject(jsonObject);

        MoviesDatabase.getDBInstance().insertMoviesBoxOffice(movieArrayList, true);
        return  movieArrayList;
    }


    private static String getRequestUrl(int limit) {
        //return URL_ROTTEN_TOMATOE_BOX_OFFICE+"?apikey="+ MyApplication.API_KEY_ROTTEN_TOMATOES+"&limit="+limit;
        //modified version both works fine, 2nd one is a bit clean

        //URL_BOX_OFFICE declare in UrlEndPoints in package extras and imported statically
        return URL_BOX_OFFICE + URL_CHAR_QUESTION + URL_PARAM_API_KEY
                + MyApplication.API_KEY_ROTTEN_TOMATOES + URL_CHAR_AMEPERSAND + URL_PARAM_LIMIT + limit;

    }
}
