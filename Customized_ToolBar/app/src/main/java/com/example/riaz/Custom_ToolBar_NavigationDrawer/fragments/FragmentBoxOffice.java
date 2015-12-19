package com.example.riaz.Custom_ToolBar_NavigationDrawer.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.example.riaz.Custom_ToolBar_NavigationDrawer.R;
import com.example.riaz.Custom_ToolBar_NavigationDrawer.adapters.AdapterBoxOffice;
import com.example.riaz.Custom_ToolBar_NavigationDrawer.callbacks.BoxOfficeMoviesLoadedListener;
import com.example.riaz.Custom_ToolBar_NavigationDrawer.database.MoviesDatabase;
import com.example.riaz.Custom_ToolBar_NavigationDrawer.extras.MovieSorter;
import com.example.riaz.Custom_ToolBar_NavigationDrawer.extras.SortListener;
import com.example.riaz.Custom_ToolBar_NavigationDrawer.materialtest.MyApplication;
import com.example.riaz.Custom_ToolBar_NavigationDrawer.network.VolleySingleton;
import com.example.riaz.Custom_ToolBar_NavigationDrawer.pojo.Movie;
import com.example.riaz.Custom_ToolBar_NavigationDrawer.services.MyService;
import com.example.riaz.Custom_ToolBar_NavigationDrawer.task.TaskLoadMoviesBoxOffice;

import java.util.ArrayList;

//manually static import


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentBoxOffice#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentBoxOffice extends Fragment implements SortListener, BoxOfficeMoviesLoadedListener, SwipeRefreshLayout.OnRefreshListener {
    // TODO: Rename parameter arguments, choose names that match

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private static final String STATE_MOVIE = "state_movie"; // parcelable key

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;





    //NEW OR USER DEFINED part 4
    private RecyclerView listMovieHits; // initialized onCreate view
    private AdapterBoxOffice adapterBoxOffice;
    ArrayList<Movie> listMovies= new ArrayList<>();






    private MovieSorter movieSorter = new MovieSorter();


    private SwipeRefreshLayout swipeRefreshLayout;



    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(STATE_MOVIE, listMovies);

    }




    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentBoxOffice.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentBoxOffice newInstance(String param1, String param2) {
        FragmentBoxOffice fragment = new FragmentBoxOffice();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public FragmentBoxOffice() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }



    //new
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View layout = inflater.inflate(R.layout.fragment_box_office,container,false);

        listMovieHits= (RecyclerView) layout.findViewById(R.id.listMovieHist);
        // set the layout out manager before trying to display data
        listMovieHits.setLayoutManager(new LinearLayoutManager(getActivity()));




        swipeRefreshLayout = (SwipeRefreshLayout) layout.findViewById(R.id.swipeMovieHits);

        swipeRefreshLayout.setOnRefreshListener(this);



        adapterBoxOffice = new AdapterBoxOffice(getActivity());
        listMovieHits.setAdapter(adapterBoxOffice);






        if(savedInstanceState !=null)
        {

//            Toast.makeText(getActivity(), "From saved instance state", Toast.LENGTH_SHORT).show();

            listMovies = savedInstanceState.getParcelableArrayList(STATE_MOVIE);

        } else {

          //  Toast.makeText(MyApplication.getAppContext(), "Save instane Data base" , Toast.LENGTH_SHORT).show();

            listMovies= MoviesDatabase.getDBInstance().getAllMoviesBoxOffice();

        }

        if(listMovies.isEmpty())
        {
           // Toast.makeText(getActivity(), "Save instance Asyntask", Toast.LENGTH_SHORT).show();

            new TaskLoadMoviesBoxOffice(this).execute();
        }


        adapterBoxOffice.setMoviesList(listMovies); // in case of listmovies empty, adapterBoxOffice is set by
        //onBoxOfficeMoviesLoaded called from onPostExecute method

        return layout;
    }


    @Override
    public void onSortByName() {

        movieSorter.sortMovieByName(listMovies);
        adapterBoxOffice.notifyDataSetChanged();

        Toast.makeText(getActivity(),"Sorted by name ",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSortByDate() {

        movieSorter.sortMovieByDate(listMovies);
        adapterBoxOffice.notifyDataSetChanged();

        Toast.makeText(getActivity(),"Sorted by Date ",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSortByRating() {
        movieSorter.sortMovieByRating(listMovies);
        adapterBoxOffice.notifyDataSetChanged();

        Toast.makeText(getActivity(),"Sorted by Rating ",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onBoxOfficeMoviesLoaded(ArrayList<Movie> movieArrayList) {

   //     Toast.makeText(MyApplication.getAppContext(), "Fragment OnBox office move from post execute "

     //           , Toast.LENGTH_SHORT).show();

        //This method onBoxOffice is called twice one from onPostExecute and once from swipeRefreshLayout.
        //if swipeRefreshlayout is triggered we need to set progress bar false.

        if(swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }

       adapterBoxOffice.setMoviesList(movieArrayList);

    }

    // for 13
    @Override
    public void onRefresh() {

       // System.out.print("Refresh is executed");
        Toast.makeText(MyApplication.getAppContext(), "Fragment OnBox office Refresh "

                , Toast.LENGTH_SHORT).show();

        new TaskLoadMoviesBoxOffice(this).execute();

    }
}
