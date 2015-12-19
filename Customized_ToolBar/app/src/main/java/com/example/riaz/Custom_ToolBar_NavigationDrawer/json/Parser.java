package com.example.riaz.Custom_ToolBar_NavigationDrawer.json;

import com.example.riaz.Custom_ToolBar_NavigationDrawer.extras.Constants;
import com.example.riaz.Custom_ToolBar_NavigationDrawer.pojo.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.example.riaz.Custom_ToolBar_NavigationDrawer.extras.Keys.EndpointBoxOffice.KEY_AUDIENCE_SCORE;
import static com.example.riaz.Custom_ToolBar_NavigationDrawer.extras.Keys.EndpointBoxOffice.KEY_ID;
import static com.example.riaz.Custom_ToolBar_NavigationDrawer.extras.Keys.EndpointBoxOffice.KEY_MOVIES;
import static com.example.riaz.Custom_ToolBar_NavigationDrawer.extras.Keys.EndpointBoxOffice.KEY_POSTERS;
import static com.example.riaz.Custom_ToolBar_NavigationDrawer.extras.Keys.EndpointBoxOffice.KEY_RATINGS;
import static com.example.riaz.Custom_ToolBar_NavigationDrawer.extras.Keys.EndpointBoxOffice.KEY_RELEASE_DATES;
import static com.example.riaz.Custom_ToolBar_NavigationDrawer.extras.Keys.EndpointBoxOffice.KEY_SYNOPSIS;
import static com.example.riaz.Custom_ToolBar_NavigationDrawer.extras.Keys.EndpointBoxOffice.KEY_THEATER;
import static com.example.riaz.Custom_ToolBar_NavigationDrawer.extras.Keys.EndpointBoxOffice.KEY_THUMBNAIL;
import static com.example.riaz.Custom_ToolBar_NavigationDrawer.extras.Keys.EndpointBoxOffice.KEY_TITILE;

/**
 * Created by riaz on 31/08/15.
 */
public class Parser {



   public static ArrayList<Movie> parseJSONObject(JSONObject obj) {

    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//mm for mints

        ArrayList<Movie> returnlistMovies = new ArrayList<>();

        if (obj != null || obj.length() > 0) // length mean no. of name/values paris that exist in json obj
        {


            try {


                //Key_movies declared in Keys extra package and imported statically

                JSONArray arrayMovies = obj.getJSONArray(KEY_MOVIES);


                //inside arrayMovies we get fields one by one, check boxoffice.json file in raw folder
                for (int i = 0; i < arrayMovies.length(); i++) {



                    long id = -1; // -1 in case no id is extracted from json object.
                    // and it will be used for checking condition before adding movie object to list.
                    String title = Constants.NA;
                    String releaseDate = Constants.NA;
                    int audienceScore = -1;  //-1 rating is not available
                    String synopsis = Constants.NA;
                    String urlThumbnail = Constants.NA;


                    JSONObject currentMovie = arrayMovies.getJSONObject(i);

                    if (contains(currentMovie, KEY_ID)) {

                        id = currentMovie.getLong(KEY_ID);
                    }

                    if (contains(currentMovie, KEY_TITILE)) {
                        title = currentMovie.getString(KEY_TITILE);
                    }

                    //release dates is an object inside array "release_dates": {"theater": "2011-07-15"},

                    if (contains(currentMovie, KEY_RELEASE_DATES))

                    {
                        JSONObject objectRelaseDates = currentMovie.getJSONObject(KEY_RELEASE_DATES);

                        if (contains(objectRelaseDates, KEY_THEATER)) // becoz release date contains value theater so we check it first
                        {
                            releaseDate = objectRelaseDates.getString(KEY_THEATER);
                        }

                    }


                    //get the audience score for the current movie


                    if (contains(currentMovie, KEY_RATINGS))


                    {

                        //  Toast.makeText(MyApplication.getAppContext(), "key rating not null" ,Toast.LENGTH_SHORT).show();

                        JSONObject objRating = currentMovie.getJSONObject(KEY_RATINGS);
                        // audience score is an int value inside rating object.


                        if (contains(objRating, KEY_AUDIENCE_SCORE)) {

                            audienceScore = objRating.getInt(KEY_AUDIENCE_SCORE);
                            // Toast.makeText(MyApplication.getAppContext(), "Setting socre "+audienceScore ,Toast.LENGTH_SHORT).show();
                        }

                    }


                    //get Synopsis and poster object

                    if (contains(currentMovie, KEY_SYNOPSIS))

                    {
                        synopsis = currentMovie.getString(KEY_SYNOPSIS);
                    }


                    if (contains(currentMovie, KEY_POSTERS))

                    {

                        JSONObject objPosters = currentMovie.getJSONObject(KEY_POSTERS);

                        if (contains(objPosters, KEY_THUMBNAIL)) {
                            urlThumbnail = objPosters.getString(KEY_THUMBNAIL);
                        }
                    }


                    Movie movie = new Movie(); // can be initialized in two way either use the constructor with arguments
                    // or no argument constructor and set values by using setter methods

                    movie.setId(id);
                    movie.setTitle(title);
                    Date datef = null;

                    try {
                        datef = dateFormat.parse(releaseDate);
                        movie.setReleaseDateTheater(datef);
                    } catch (ParseException e) {

                    }



                    movie.setAudienceScore(audienceScore);
                    movie.setSynopsis(synopsis);
                    movie.setUrlThumbnail(urlThumbnail);


                    if (id != -1 && !title.equals(Constants.NA))

                        //  Toast.makeText(MyApplication.getAppContext(), "object addede to list" ,Toast.LENGTH_SHORT).show();
                        returnlistMovies.add(movie);

                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return returnlistMovies;
    }



    private static boolean contains(JSONObject jsonObject, String key) {
        return jsonObject != null && jsonObject.has(key) && !jsonObject.isNull(key) ? true : false;
    }
}
