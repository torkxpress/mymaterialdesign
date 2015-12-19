package com.example.riaz.Custom_ToolBar_NavigationDrawer.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.example.riaz.Custom_ToolBar_NavigationDrawer.R;
import com.example.riaz.Custom_ToolBar_NavigationDrawer.anim.AnimationUtils;
import com.example.riaz.Custom_ToolBar_NavigationDrawer.extras.Constants;
import com.example.riaz.Custom_ToolBar_NavigationDrawer.materialtest.MyApplication;
import com.example.riaz.Custom_ToolBar_NavigationDrawer.network.VolleySingleton;
import com.example.riaz.Custom_ToolBar_NavigationDrawer.pojo.Movie;

import java.awt.font.TextAttribute;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by riaz on 30/06/15.
 */
public class AdapterBoxOffice extends RecyclerView.Adapter<AdapterBoxOffice.ViewHolderBoxOffice> {

    //first create ViewHolderBoxOffice extending Recyclerview holder as inner static class
    //then AdapterBoxOffice extends RecyclerView.Adapter<AdapterBoxOffice.ViewHolderBoxOffice>

    private LayoutInflater layoutInflater;
    private ArrayList<Movie> listMovies = new ArrayList<>();
    private VolleySingleton volleySingleton;
    private ImageLoader imageLoader;



    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");



    private int previousPostion =0; // used in onBindView method

    public AdapterBoxOffice(Context context)

    {

        layoutInflater= LayoutInflater.from(context);
        volleySingleton = VolleySingleton.getsInstance();
        imageLoader = volleySingleton.getImageLoader();
    }

    public void setMoviesList(ArrayList<Movie> listMovies)
    {
        this.listMovies = listMovies;
        notifyItemRangeChanged(0,listMovies.size());

    }

    @Override
    public ViewHolderBoxOffice onCreateViewHolder(ViewGroup parent, int viewType) {

       View view =layoutInflater.inflate(R.layout.custome_boxoffice_ayout,parent,false);

        ViewHolderBoxOffice viewHolderBoxOffice = new ViewHolderBoxOffice(view);

        return viewHolderBoxOffice;

    }

    @Override
    public void onBindViewHolder(final ViewHolderBoxOffice holder, int position) {
        Movie currentMovie = listMovies.get(position);
        holder.movieTitle.setText(currentMovie.getTitle());


        Date date = currentMovie.getReleaseDateTheater();
        if(date !=null)
        {
            String formattedDate = dateFormat.format(date);
            holder.movieReleaseDate.setText(formattedDate);
        } else
        {
            holder.movieReleaseDate.setText(Constants.NA);
        }


        int audienceScore= currentMovie.getAudienceScore();

            if(audienceScore == -1){

                holder.movieAudienceScore.setRating(0.0F);
                holder.movieAudienceScore.setAlpha(0.5F); // means only 50% visible
            }

        else {

              //  Toast.makeText(MyApplication.getAppContext(), "Setting socre "+audienceScore ,Toast.LENGTH_SHORT).show();

                holder.movieAudienceScore.setRating(audienceScore / 20.0F);
                holder.movieAudienceScore.setAlpha(1.0F); // means only 100% visible
                //divide by 20.0f to get float value
                //why 20 because score is 0 to 100 while Rating stars are 5, so dividing score of 100 between 5 stars.
                // 20 out of 100 means 1 star 40 means 2 star. here in example score is 93 which comes around 4.5 stars.
        }




        String urlThumbnail = currentMovie.getUrlThumbnail();



   /*     if(position> previousPostion)
        {
            AnimationUtils.animate(holder, true);

        }else {
            AnimationUtils.animate(holder, false);

        }
        previousPostion = position;

        */


        // CALLING ANIMATION LIBRARY METHOD
        AnimationUtils.animate(holder);




        if (urlThumbnail != null && !urlThumbnail.equals(Constants.NA) )
        {
            imageLoader.get(urlThumbnail, new ImageLoader.ImageListener() {
                @Override
                public void onErrorResponse(VolleyError error) {


                }

                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    holder.movieThumbnail.setImageBitmap(response.getBitmap());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return listMovies.size();
    }

    static class ViewHolderBoxOffice extends RecyclerView.ViewHolder{

        private ImageView movieThumbnail;
        private TextView movieTitle;
        private TextView movieReleaseDate;
        private RatingBar movieAudienceScore;


        public ViewHolderBoxOffice(View itemView) {
            super(itemView);

            movieThumbnail = (ImageView) itemView.findViewById(R.id.movieThumbnail);
            movieTitle =(TextView) itemView.findViewById(R.id.movieTitle);
            movieReleaseDate =(TextView) itemView.findViewById(R.id.movieReleasedate);
            movieAudienceScore =(RatingBar) itemView.findViewById(R.id.movieRatingBar);

        }
    }



}
