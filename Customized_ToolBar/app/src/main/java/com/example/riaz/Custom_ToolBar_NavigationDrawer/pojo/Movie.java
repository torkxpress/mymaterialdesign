package com.example.riaz.Custom_ToolBar_NavigationDrawer.pojo;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.Toast;

import com.example.riaz.Custom_ToolBar_NavigationDrawer.materialtest.MyApplication;

import java.util.Date;

/**
 * Created by riaz on 27/06/15.
 */
public class Movie implements Parcelable {
    private long id;
    private String title;
    private Date releaseDateTheater;
    private String synopsis;
    private int audienceScore;
    private String urlThumbnail;



    public Movie() // used with setter and getter methods
    {

    }

    public Movie(Parcel input) // constructor for restoring data from parcel
    {

        id = input.readLong();
        title = input.readString();
        releaseDateTheater=  new Date(input.readLong());
        audienceScore = input.readInt();
        synopsis = input.readString();
        urlThumbnail = input.readString();

    }

    public Movie(long id, String title,Date releaseDateTheater,String synopsis,
                 int audienceScore,String urlThumbnail)

    {
        this.id= id;
        this.title = title;
        this.releaseDateTheater = releaseDateTheater;
        this.synopsis = synopsis;
        this.audienceScore = audienceScore;
        this.urlThumbnail= urlThumbnail;
    }

    @Override
    public String toString() {
        return "ID: " + id+
                "Title: " + title+
                "Date: " + releaseDateTheater+
                "Synopsis: "+ synopsis+
                "Score: " + audienceScore+
                "UrlThumbnail "+ urlThumbnail;
    }


    //getter and setter methods

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Date getReleaseDateTheater() {
        return releaseDateTheater;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public int getAudienceScore() {
        return audienceScore;
    }

    public String getUrlThumbnail() {
        return urlThumbnail;
    }


    public void setId(long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setReleaseDateTheater(Date releaseDateTheater) {
        this.releaseDateTheater = releaseDateTheater;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public void setAudienceScore(int audienceScore) {
        this.audienceScore = audienceScore;
    }

    public void setUrlThumbnail(String urlThumbnail) {
        this.urlThumbnail = urlThumbnail;
    }




    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeLong(id);
        dest.writeString(title);
        //for date we don't have direct method so we either serialize it or change it to long
        // serialization is not a good choice because of slow performance.
        dest.writeLong(releaseDateTheater.getTime());//long in milli seconds, at the other end convert back to date from milli secon
        dest.writeInt(audienceScore);
        dest.writeString(synopsis);
        dest.writeString(urlThumbnail);
    }

    //code copied from adroid developer site
    //http://developer.android.com/reference/android/os/Parcelable.html#writeToParcel(android.os.Parcel, int)
    //go to code overview

    public static final Parcelable.Creator<Movie> CREATOR
            = new Parcelable.Creator<Movie>() {
        public Movie createFromParcel(Parcel in) {

            Toast.makeText(MyApplication.getAppContext(), " Creating from parcel", Toast.LENGTH_SHORT).show();

            return new Movie(in);
        }

        public Movie[] newArray(int size) { // for in case you are using array

            Toast.makeText(MyApplication.getAppContext(), " Creating from Array", Toast.LENGTH_SHORT).show();

            return new Movie[size];
        }
    };
}
