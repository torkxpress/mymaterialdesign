package com.example.riaz.Custom_ToolBar_NavigationDrawer.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.riaz.Custom_ToolBar_NavigationDrawer.activities.Activity_Main_Tab_Library_icon;
import com.example.riaz.Custom_ToolBar_NavigationDrawer.pojo.Information_NavigationDrawer;
import com.example.riaz.Custom_ToolBar_NavigationDrawer.R;
import com.example.riaz.Custom_ToolBar_NavigationDrawer.adapters.MyRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class NavigationDrawerFragment extends Fragment  {

    private  View view ; // view created from fragment, which we need to pass to mDrawerLayout.open method

    public static final String PREF_FILE_NAME= "testpref"; // for naming shared preference
    public static final String KEY_USER_LEARNED_DRAWER="user_learned_drawer";

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout; // received from main and set to listen to DrawerToggle


/*ActionBarDrawerToggle  class provides a handy way to tie together the functionality of DrawerLayout
    and the framework ActionBar to implement the recommended design for navigation drawers.

To use ActionBarDrawerToggle, create one in your Activity and call through to the following methods corresponding to your
Activity callbacks:
onConfigurationChanged
onOptionsItemSelected*/

    // display the drawer when user opens the application for first time..
    //just to make the user aware that a drawer exists, save the values in sharedpreferences and based on that display drawer if first time

    private boolean mUserLearnedDrawer;

    private boolean mFromSavedInstanceState;

    private MyRecyclerAdapter adapter; // initialized in onCreateView
    private RecyclerView recyclerView; // initialized in onCreateView

    // directly extending from Layout (ViewGroup)
    //instead of List because Recycler View is a flexible view for providing a limited window into a
    // large data set,((it doesn't specify, how data should look like)it's much more flexible and bigger than listview)
    // ...important to note, (difference from listview)



    public NavigationDrawerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       mUserLearnedDrawer= Boolean.parseBoolean(readFromPreferences(getActivity(),KEY_USER_LEARNED_DRAWER,"false"));
        //false for default, if nothing is available, i.e first time app started, pref value should be 0 to display drawer
        //mUserLearnedDrawer is boolean while value retuned to it from pref is string, so we change that to boolean value

        if(savedInstanceState!=null) // its coming back from rotation so no need to pop up drawer again
        {
            mFromSavedInstanceState = true;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View layout = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);

            recyclerView = (RecyclerView) layout.findViewById(R.id.recyclerContainer);
        //recyclerContainer is the recyclerView... added in the fragment_navigation_drawer xml

        adapter = new MyRecyclerAdapter(getActivity(),getListData());


        // passing fragment reference to adapter so that on click method, myOnClickMethod of navigationdrawer is called.
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //LayoutManager is must other wise run time exception..



        /* MyRecycerTouchListener implements onITemTouchListener so we pass this argument to addOnITemtouchListener*/

        recyclerView.addOnItemTouchListener(new MyRecyclerTouchListener(getActivity(), recyclerView, new MyClickListener() {
            @Override
            public void onMyClick(View view, int position) {

                Toast.makeText(getActivity(),"Open tab No " + position,Toast.LENGTH_SHORT).show();
                mDrawerLayout.closeDrawer(GravityCompat.START);


                ( (Activity_Main_Tab_Library_icon) getActivity()).onDrawerItemClicked(position-1);
                // 1 is added to the getitem count in myrecycler adapter to cater for header of navigation drawer
            }

            @Override
            public void onMyLongClick(View view, int position) {

                Toast.makeText(getActivity(),"on LONG CLick " + position,Toast.LENGTH_SHORT).show();

            }
        }));

        return layout;
    }

    //data for adapter

    private static List<Information_NavigationDrawer> getListData(){

      //load only static data inside a drawer
        List<Information_NavigationDrawer> listdata= new ArrayList<>();
        int[] icons={R.drawable.fragment_smiling, R.drawable.actions_go_next_icon, R.drawable.fragment_smiling};
        String[] titles= {"Search", "BoxOffice", "UpComing"};


        for(int i=0; i<titles.length ;i++)
        {
          Information_NavigationDrawer obj = new Information_NavigationDrawer();

            obj.icondId= icons[i];
            obj.title= titles[i];
            listdata.add(obj);
        }

        return listdata;
    }

// drawer navigation set up
    public void setUp(int fragment_navigation_drawer, DrawerLayout drawerLayout, final Toolbar toolbar) {

        view = getActivity().findViewById(fragment_navigation_drawer);
        mDrawerLayout= drawerLayout;

        //drawer open is called after checking the condition, .
        //if(!mUserLearnedDrawer && !mFromSavedInstanceState) (defined below the anonymous class extending mDrawerToggle class)
        mDrawerToggle= new ActionBarDrawerToggle(getActivity(),drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close){

            //onDrawerClosed(hamburger icon) and open(arrow icon) for changing the icon or logo on toolbar

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if(!mUserLearnedDrawer) // first time user saw it
                {
                    mUserLearnedDrawer= true; // no need to display it again automatically
                    saveToPreferences(getActivity(),KEY_USER_LEARNED_DRAWER,mUserLearnedDrawer+"");
                     //mUserLearnedDrawer is boolean to change it to string by adding + ""
                }
                getActivity().invalidateOptionsMenu();
                // redraw the toolbar so its not hidden
            }

            // color translucent
            // for setting the toolbar color, when drawer is opened or closed
            // dim toolbar when drawer is opened, bright when drawers closes..
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);

                // for hiding FAB
                ((Activity_Main_Tab_Library_icon)getActivity()).onDrawSlide(slideOffset);

                toolbar.setAlpha(1-slideOffset/2);

            }
        }; // new ActionBarToggle class closing


        mDrawerLayout.setDrawerListener(mDrawerToggle); // listening to opening and closing

//Either use post message to main thread or call onActivityCreated....
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
                if(!mUserLearnedDrawer && !mFromSavedInstanceState) // drawer not seen before and not coming back from screen rotation
                // display the drawer
                {
                    mDrawerLayout.openDrawer(view); // need to specify the fragment view.

                }

                //to synchronize the tool bar when the drawer opens or closes and display the hambuger or arrow sign
            }
        });
    }



    public  static void saveToPreferences(Context context,String preferenceName, String preferenceValue){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedPreferences.edit();
        editor.putString(preferenceName,preferenceValue);
        editor.apply();

    }

    public  static String readFromPreferences(Context context,String preferenceName, String defaultValue){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(preferenceName, defaultValue);


    }

    class MyRecyclerTouchListener implements RecyclerView.OnItemTouchListener{


        private GestureDetector gestureDetector; // to detect difference between single tap, double tap or Long press

        private MyClickListener myClickListener;

        // constructor
        public MyRecyclerTouchListener(Context context, final RecyclerView recyclerView, final MyClickListener myClickListener){

           this.myClickListener = myClickListener;

            gestureDetector = new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){

                /*it has got many methods but we are only interested in two methods here
                To call these methods on touch, you must have to send manually MotionEvent from onItercepTouchEvent
                * */
              @Override
                public boolean onSingleTapUp(MotionEvent e) {
                   // return super.onSingleTapUp(e); // returns false by default
                     /* by default all methods return false, which means we are not interested in
                    handling touch event but we want to handle touch event then we have to return true;
                    means the user lift the finger.
                     */

                    return true; // means the touch is handled by this method.
                }

                @Override
                public void onLongPress(MotionEvent e) {

                   View childView =  recyclerView.findChildViewUnder(e.getX(),e.getY());
                   // super.onLongPress(e);// by default return false
                    if(childView!=null && myClickListener !=null)
                    {
                        myClickListener.onMyLongClick(childView,recyclerView.getChildPosition(childView));
                    }
                }
            });

        }
       @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View childView = rv.findChildViewUnder(e.getX(), e.getY());

            if(childView !=null &&  myClickListener !=null && gestureDetector.onTouchEvent(e))

            {
                myClickListener.onMyClick(childView,rv.getChildPosition(childView));
                // onMyClick method implemented in onCreateView
                /*

                *
                * */

            }


            return false; /*by default its false, which allows the child view to process the touch event,
            and onTouchEvent is not called.
            if its true then the touch process will stop here and call the onTouchEvent */
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

    public interface MyClickListener{
         void onMyClick(View view,int position);
         void onMyLongClick(View view,int position);
    }

//recycler view implements the MyClickListener by recyclerview.addonitemtouchevent
}
