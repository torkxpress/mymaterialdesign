package com.example.riaz.Custom_ToolBar_NavigationDrawer.json;

import android.content.res.Resources;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.example.riaz.Custom_ToolBar_NavigationDrawer.R;
import com.example.riaz.Custom_ToolBar_NavigationDrawer.database.MoviesDatabase;
import com.example.riaz.Custom_ToolBar_NavigationDrawer.materialtest.MyApplication;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by riaz on 31/08/15.
 */
public class Requestor {




    public static JSONObject sendRequestBoxOfficeMovies(RequestQueue requestQueue, String url) {


        JSONObject jsonObject ;

        //request future object to tell the volley to run on the same thread as asynctask
        RequestFuture<JSONObject> requestFuture = RequestFuture.newFuture();


        //if JSON file starts with curely bracket "{" then use JSONObjectRequest, if starts with "[" square bracket then use
        //JSON array Request.


        // don't have acces to RottenTomato online api so we read from json file, because this method throws an exception
           /* JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                    url,
                    (String) null, requestFuture, requestFuture);



            requestQueue.add(request); */

        jsonObject = readJSONFromRawFolder();



        try {
            jsonObject = requestFuture.get(3000, TimeUnit.MILLISECONDS); // simple get() method waits
            //indefinite time, but here it waits for 30 seconds but i changed for mobile to 3 seconds
            // so it already read data from jsonfile, as we don't have access to rottentomato api
        } catch (InterruptedException e) {

        } catch (ExecutionException e) {


        } catch (TimeoutException e) {


        }

        return jsonObject ;
    }


    private static JSONObject readJSONFromRawFolder() {
        Resources res = MyApplication.getAppContext().getResources();
        InputStream inputStream = res.openRawResource(R.raw.boxoffice);

        JSONObject obj = null;
        byte[] b = null;
        try {
            b = new byte[inputStream.available()];

        } catch (IOException e) {
          //  Toast.makeText(MyApplication.getAppContext(), "IOException inputStream available", Toast.LENGTH_SHORT).show();
        }
        try {
            inputStream.read(b);

        } catch (IOException e) {
           // Toast.makeText(MyApplication.getAppContext(), "IO Exception in Reading ", Toast.LENGTH_SHORT).show();
        }
        String json = new String(b);


        try {
            obj = new JSONObject(json);

            //  listMovies=    parseJSONObject(obj);
            // adapterBoxOffice.setMoviesList(listMovies);

        } catch (JSONException e) {
           // Toast.makeText(MyApplication.getAppContext(), "JSON Exception", Toast.LENGTH_SHORT).show();
        }

        return obj;


    }



}

