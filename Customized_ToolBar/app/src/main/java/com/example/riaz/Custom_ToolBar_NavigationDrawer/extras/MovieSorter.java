package com.example.riaz.Custom_ToolBar_NavigationDrawer.extras;

import com.example.riaz.Custom_ToolBar_NavigationDrawer.pojo.Movie;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by riaz on 24/08/15.
 */
public class MovieSorter {


    public void sortMovieByName(ArrayList<Movie> movies){

        /*we don't need to use any merge sort, quick sort etc, Java Api already has already prebuilt sorting methods
         so we use them

         e.g.
          Collections.sort(movies)
          Or
          Collections.sort(movies, new Comparator<Movie>()
        * */

        Collections.sort(movies, new Comparator<Movie>() { // 2nd argument is for how to sort
            @Override
            public int compare(Movie lhs, Movie rhs) {
                return lhs.getTitle().compareTo(rhs.getTitle());


                /* -ve integer if first argument is less than 2nd
                zero if equal to 2nd
                1 if greater than the 2nd.
                * */
            }
        });


    }

    public void sortMovieByDate(ArrayList<Movie> movies)
    {
        Collections.sort(movies, new Comparator<Movie>() {
            @Override
            public int compare(Movie lhs, Movie rhs) {

                return rhs.getReleaseDateTheater().compareTo(lhs.getReleaseDateTheater());

                // descending order latest movies first
                // for ascending order change the order lhs.getReleaseDate... rhs.getR.....
            }
        });
    }


    public void sortMovieByRating(ArrayList<Movie> movies)
    {
        Collections.sort(movies, new Comparator<Movie>() {
            @Override
            public int compare(Movie lhs, Movie rhs) {

                int ratinglhs = lhs.getAudienceScore();
                int ratingrhs = rhs.getAudienceScore();

                if(ratinglhs<ratingrhs)
                {
                    //return  -1; // ascending , low rate comes first

                    return 1; // descending, high rate comes first
                }
                else if (ratinglhs>ratingrhs)
                    return  -1;  // descending, high rate comes first make is plus 1 for reversing

                else
                return 0;
            }
        });
    }

}
