package com.example.riaz.Custom_ToolBar_NavigationDrawer.services;

import android.widget.Toast;

import com.example.riaz.Custom_ToolBar_NavigationDrawer.callbacks.BoxOfficeMoviesLoadedListener;
import com.example.riaz.Custom_ToolBar_NavigationDrawer.materialtest.MyApplication;
import com.example.riaz.Custom_ToolBar_NavigationDrawer.pojo.Movie;
import com.example.riaz.Custom_ToolBar_NavigationDrawer.task.TaskLoadMoviesBoxOffice;

import java.util.ArrayList;

import me.tatarka.support.job.JobParameters;
import me.tatarka.support.job.JobService;


/**
 * Created by riaz on 26/08/15.
 */
// implements BoxOfficeMoviesLoadedListener because TaskLoadMoviesBoxOffice accepts only BoxOfficeMoviesLoadedListener
    // which is also implemented by BoxOfficeFragment for updating boxoffice fragment when app is loaded for the first time
    //saveinstane state and database both are empty, so Aysnc is called, and then later on every 10 minutes service calls
    //async task to update database tables and async task accepts only BoxOfficeMovieLoaded listener

public class MyService extends JobService   implements BoxOfficeMoviesLoadedListener
{ // android.app.job for 21 api, while me.tatarka.job for api below 21


    private JobParameters jobParameters;
    @Override
    public boolean onStartJob(JobParameters jobParameters) {

        Toast.makeText(MyApplication.getAppContext(), "Refreshing movies data every 10 second", Toast.LENGTH_SHORT).show();

// run this method when data is loaded from database
      //  jobFinished(jobParameters,false); // add it for pre lollipop devices, false means don't run again, true means


        this.jobParameters = jobParameters;

        new TaskLoadMoviesBoxOffice(this).execute();

        return true; // when returning result from asyntask
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {

        Toast.makeText(MyApplication.getAppContext(), "Updated", Toast.LENGTH_SHORT).show();
        return false;
    }

   @Override
    public void onBoxOfficeMoviesLoaded(ArrayList<Movie> movieArrayList) {

         // run this method when data is loaded from database
          jobFinished(jobParameters,false); // add it for pre lollipop devices, false means don't run again, true means



    }


}
