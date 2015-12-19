package com.example.riaz.Custom_ToolBar_NavigationDrawer.database;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.widget.Toast;

import com.example.riaz.Custom_ToolBar_NavigationDrawer.activities.Activity_Main_Tab_Library_icon;
import com.example.riaz.Custom_ToolBar_NavigationDrawer.materialtest.MyApplication;
import com.example.riaz.Custom_ToolBar_NavigationDrawer.pojo.Movie;


import java.util.ArrayList;
import java.util.Date;

/**
 * Created by riaz on 29/08/15.
 */
public class MoviesDatabase extends Activity {




    private MovieDBHelper movieDBHelper;
    private SQLiteDatabase sqLiteDatabase;
   // private Context context ;

    private static MoviesDatabase  moviesDatabase;

   private MoviesDatabase(Context context) {



        movieDBHelper = new MovieDBHelper(context);
        sqLiteDatabase = movieDBHelper.getWritableDatabase();
    }

public static MoviesDatabase getDBInstance()
{
    if(moviesDatabase ==null)
    {
        moviesDatabase= new MoviesDatabase(MyApplication.getAppContext());
    }

    return moviesDatabase;
}




    public void insertMoviesBoxOffice(ArrayList<Movie> listMovies, boolean clearPrevious)
    {
        if(clearPrevious) deleteAll();

        //create a sql prepared statement

        String sql = "INSERT INTO "+ MovieDBHelper.TABLE_BOX_OFFICE + " VALUES(?,?,?,?,?,?)";

        //compile the statement and start a transaction;

        SQLiteStatement statement = sqLiteDatabase.compileStatement(sql);

        sqLiteDatabase.beginTransaction();

        for(int i=0; i< listMovies.size(); i++)
        {
            Movie currentMovie = listMovies.get(i);

            // for a given column index, simply bind the data to be put inside that index

            statement.bindString(2,currentMovie.getTitle());
            statement.bindLong(3, currentMovie.getReleaseDateTheater() == null ? -1 : currentMovie.getReleaseDateTheater().getTime());
            statement.bindLong(4, currentMovie.getAudienceScore());
            statement.bindString(5, currentMovie.getSynopsis());
            statement.bindString(6, currentMovie.getUrlThumbnail());

            statement.execute();
        }




        //set the transaction as successful and end the transaction

        sqLiteDatabase.setTransactionSuccessful();
        sqLiteDatabase.endTransaction();

    }

    //toast on main thread can be called from any where, e.g background thread.

    public void showToast(final String toast) {
        this.runOnUiThread(new Runnable() { // “this keyword” is optional
            public void run() {
                Toast.makeText(MyApplication.getAppContext(), toast, Toast.LENGTH_SHORT).show();
            }
        }); }

    public ArrayList<Movie> getAllMoviesBoxOffice()
    {
        ArrayList<Movie> movieArrayList = new ArrayList<>();

        // get a list of columns need to be retrieved

        String columns[] = {MovieDBHelper.COLUMN_UID,MovieDBHelper.COLUMN_TITLE, MovieDBHelper.COLUMN_RELEASE_DATE,
                MovieDBHelper.COLUMN_AUDIENCE_SCORE, MovieDBHelper.COLUMN_SYNOPSIS, MovieDBHelper.COLUMN_URL_THUMBNAIL

        };

        Cursor cursor = sqLiteDatabase.query(MovieDBHelper.TABLE_BOX_OFFICE, columns,null, null, null,null,null);

        if(cursor !=null && cursor.moveToFirst())
        {
            Toast.makeText(MyApplication.getAppContext()," Loading Enteries ",Toast.LENGTH_SHORT).show();

            do{

                //create a new Movie object and retrieve the data from the cursor to be stored in this movie object
                Movie movie = new Movie();
                //each step is 2 part process, find the index of the column first, find the data of that column using
                //that index and finally set our blank movie object to contain our data


                movie.setTitle(cursor.getString(cursor.getColumnIndex(MovieDBHelper.COLUMN_TITLE)));

                long releaseDate = cursor.getLong(cursor.getColumnIndex(MovieDBHelper.COLUMN_RELEASE_DATE));
                movie.setReleaseDateTheater(releaseDate != -1 ? new Date(releaseDate) : null);

                movie.setAudienceScore(cursor.getInt(cursor.getColumnIndex(MovieDBHelper.COLUMN_AUDIENCE_SCORE)));

                movie.setSynopsis(cursor.getString(cursor.getColumnIndex(MovieDBHelper.COLUMN_SYNOPSIS)));

                movie.setUrlThumbnail(cursor.getString(cursor.getColumnIndex(MovieDBHelper.COLUMN_URL_THUMBNAIL)));

                movieArrayList.add(movie);


            } while(cursor.moveToNext());
        }


        return movieArrayList;
    }

    private void deleteAll() {

        sqLiteDatabase.delete(MovieDBHelper.TABLE_BOX_OFFICE,null, null);
    }


    private static class MovieDBHelper extends SQLiteOpenHelper{

        public static final String TABLE_BOX_OFFICE = "movies_box_office_table";
        public static final String COLUMN_UID = "_id";
        public static final String COLUMN_TITLE = "_title";
        public static final String COLUMN_RELEASE_DATE = "release_date";
        public static final String COLUMN_AUDIENCE_SCORE = "audience_score";
        public static final String COLUMN_SYNOPSIS ="synopsis";
        public static final String COLUMN_URL_THUMBNAIL = "url_thumbnail";

        private static final String CREATE_TABLE_BOX_OFFICE = "CREATE TABLE "+ TABLE_BOX_OFFICE+ " ("+
                COLUMN_UID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                COLUMN_TITLE + " TEXT ,"+
                COLUMN_RELEASE_DATE + " INTEGER, "+
                COLUMN_AUDIENCE_SCORE + " INTEGER, "+
                COLUMN_SYNOPSIS + " TEXT, "+
                COLUMN_URL_THUMBNAIL + " TEXT "+
                " );";

        private static final String DB_NAME="movies_db";
        private static final int DB_VERSION = 1;
        private Context mContext;


        private MovieDBHelper(Context context) {
            super(context, DB_NAME , null, DB_VERSION);
            mContext = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            try{
                db.execSQL(CREATE_TABLE_BOX_OFFICE);
            //    Toast.makeText(mContext, " Box Office table created ", Toast.LENGTH_SHORT).show();

            } catch(SQLiteException e)
            {
                Toast.makeText(mContext, " Exception in Box Office Table Creation " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }



        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            try{

                db.execSQL(" DROP TABLE IF EXISTS " + TABLE_BOX_OFFICE +" ;");

                Toast.makeText(mContext, " Upgrading Box Office Table ", Toast.LENGTH_SHORT).show();

                onCreate(db);

            } catch(SQLiteException e)
            {
                Toast.makeText(mContext, " Exception in Box Office Table Upgrading "+ e.getMessage(), Toast.LENGTH_SHORT).show();
            }


        }
    }
}


