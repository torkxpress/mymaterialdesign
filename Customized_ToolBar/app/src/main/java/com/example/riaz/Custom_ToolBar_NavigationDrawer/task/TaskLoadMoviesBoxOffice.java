package com.example.riaz.Custom_ToolBar_NavigationDrawer.task;

import android.os.AsyncTask;
import android.os.Looper;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.example.riaz.Custom_ToolBar_NavigationDrawer.callbacks.BoxOfficeMoviesLoadedListener;
import com.example.riaz.Custom_ToolBar_NavigationDrawer.extras.MovieUtils;
import com.example.riaz.Custom_ToolBar_NavigationDrawer.materialtest.MyApplication;
import com.example.riaz.Custom_ToolBar_NavigationDrawer.network.VolleySingleton;
import com.example.riaz.Custom_ToolBar_NavigationDrawer.pojo.Movie;

import org.json.JSONObject;

import java.util.ArrayList;

import me.tatarka.support.job.JobParameters;

/**
 * Created by riaz on 31/08/15.
 */
public class TaskLoadMoviesBoxOffice extends AsyncTask<Void,Void,ArrayList<Movie>> {



    private BoxOfficeMoviesLoadedListener boxOfficeMoviesLoadedListener;
    private VolleySingleton volleySingleton;

    private RequestQueue requestQueue;



    public TaskLoadMoviesBoxOffice(BoxOfficeMoviesLoadedListener boxOfficeMoviesLoadedListener)

    {
        this.boxOfficeMoviesLoadedListener = boxOfficeMoviesLoadedListener;

        volleySingleton = VolleySingleton.getsInstance();
        requestQueue = volleySingleton.getmRequestQueue();
    }



    @Override
    protected ArrayList<Movie> doInBackground(Void... params) {

        ArrayList<Movie> movieArrayList = MovieUtils.loadBoxOfficeMovies(requestQueue);


        return movieArrayList;
    }

    @Override
    protected void onPostExecute(ArrayList<Movie> movieArrayList) {
        super.onPostExecute(movieArrayList);


        if(boxOfficeMoviesLoadedListener !=null)

        {
       //     Toast.makeText(MyApplication.getAppContext(), "Task on post execute", Toast.LENGTH_SHORT).show();
            boxOfficeMoviesLoadedListener.onBoxOfficeMoviesLoaded(movieArrayList);
        } else {
            //  Toast.makeText(MyApplication.getAppContext(), "post execute Boxofficemovie laoder null", Toast.LENGTH_SHORT).show();
        }
    }


}
