package com.example.riaz.Custom_ToolBar_NavigationDrawer.activities;

import android.content.ComponentName;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.riaz.Custom_ToolBar_NavigationDrawer.R;
import com.example.riaz.Custom_ToolBar_NavigationDrawer.extras.SortListener;
import com.example.riaz.Custom_ToolBar_NavigationDrawer.fragments.FragmentBoxOffice;
import com.example.riaz.Custom_ToolBar_NavigationDrawer.fragments.Fragment_Search;
import com.example.riaz.Custom_ToolBar_NavigationDrawer.fragments.Fragment_UpComing;
import com.example.riaz.Custom_ToolBar_NavigationDrawer.fragments.NavigationDrawerFragment;
import com.example.riaz.Custom_ToolBar_NavigationDrawer.services.MyService;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;
import me.tatarka.support.job.JobInfo;
import me.tatarka.support.job.JobScheduler;


/**
 * Created by riaz on 17/05/15.
 *
 */
public class

        Activity_Main_Tab_Library_icon extends AppCompatActivity implements MaterialTabListener, View.OnClickListener {
    private static final int JOB_ID = 100 ; // can give any value

    NavigationDrawerFragment drawerFragment ;

    Toolbar toolbar ;

    private ViewPager mPager;
    private MaterialTabHost mTabs; // edited for tab with library



    private MyPagerAdapter myPagerAdapter; // for inserting all tabs from PagerAdapter data

    public static final int MOVIES_SEARCH_RESULTS =0;
    public static final int MOVIES_HITS =1; public static final int MOVIES_UPCOMING =2;




    private  FloatingActionMenu mFABMenu;
    private FloatingActionButton floatingActionButton;
    public static final String TAG_SORT_NAME = "sortName";
    public static final String TAG_SORT_DATE = "sortDate";
    public static final String TAG_SORT_RATING = "sortRating";



    private JobScheduler jobScheduler;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tab_library_icon);

        buildFAB(); // creat Floating Action Button
        setupTabs();
        setupJob();

        setupDrawer();


    }
//  for switching to the corresponding tab when item on navigator drawer is clicked
    public void onDrawerItemClicked(int index){
        mPager.setCurrentItem(index);

    }



    private void setupTabs(){

        myPagerAdapter =new MyPagerAdapter(getSupportFragmentManager());


        mTabs = (MaterialTabHost) findViewById(R.id.materialTabHost_icon);
        mPager = (ViewPager) findViewById(R.id.viewPager_Library);
        mPager.setAdapter(myPagerAdapter);
        mPager.setOnPageChangeListener( new ViewPager.SimpleOnPageChangeListener(){

            @Override
            public void onPageSelected(int position) {
                mTabs.setSelectedNavigationItem(position);
                //on page change corresponding tab is selected and do the reverse in
                //onTabSelected.
            }
        });

// insert all tabs from pagerAdapter data
        for (int i = 0; i < myPagerAdapter.getCount(); i++) {
            mTabs.addTab(
                    mTabs.newTab()
                            .setIcon(myPagerAdapter.getIcon(i))

                            .setTabListener(this)

            );
        }


    }

    private void setupDrawer()
    {
        toolbar = (Toolbar) findViewById(R.id.myToolBar);



        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);



        /*Set whether to include the application home affordance in the action bar. Home is presented as either an activity icon or logo.
     To set several display options at once, see the setDisplayOptions methods.*/

        drawerFragment = (NavigationDrawerFragment)getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);


        // passing fragment, drawer layout(which contains the main and fragment) and toolbar to fragment
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);

    }



    private void setupJob()
    {
        jobScheduler = JobScheduler.getInstance(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                construct_job(); // for schedule job, run after 30 seconds= 30000 for first time on create then
                // every 10 minutes. Just to give some time to async task or data base to load data

            }
        }, 30000);


    }

    private void construct_job()
    {
        JobInfo.Builder builder = new JobInfo.Builder(JOB_ID, new ComponentName(this, MyService.class));

        // PersistableBundle persistableBundle = new PersistableBundle();
        // a bundle stored across screen rotation, just in case you need to send some data
        // .setextra to bundle.. and send data


        // for the first time its run after 30 second from handler in Oncreate Method and then subsequently
        // after every ten minutes
        builder.setPeriodic(6000) // run job scheduler every 10 minutes= 600000, time in millli seconds
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                .setPersisted(true);


        jobScheduler.schedule(builder.build());
    }

    private void buildFAB() {


        // Floating Action Button

        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.drawable.actions_go_next_icon);
        // just a dummy icon for this, we are using this icon for sorting so in real life we would put sorting icon


      floatingActionButton = new FloatingActionButton.Builder(this)
                .setContentView(imageView)
                .setBackgroundDrawable(R.drawable.selector_button_red)
                .build();

        ImageView iconSortName =  new ImageView(this);
        iconSortName.setImageResource(R.drawable.actions_go_next_icon);


        ImageView iconSortDate =  new ImageView(this);
        iconSortDate.setImageResource(R.drawable.actions_go_next_icon);

        ImageView iconSortRating =  new ImageView(this);
        iconSortRating.setImageResource(R.drawable.actions_go_next_icon);



        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);

        // set background colour added later
        itemBuilder.setBackgroundDrawable(getResources().getDrawable(R.drawable.selector_sub_button));

        // build the sub button

        SubActionButton btnSortName = itemBuilder.setContentView(iconSortName).build();
        SubActionButton btnSortDate = itemBuilder.setContentView(iconSortDate).build();
        SubActionButton btnSortRating = itemBuilder.setContentView(iconSortRating).build();

        btnSortName.setTag(TAG_SORT_NAME);
        btnSortDate.setTag(TAG_SORT_DATE);
        btnSortRating.setTag(TAG_SORT_RATING);


        btnSortName.setOnClickListener(this);
        btnSortDate.setOnClickListener(this);
        btnSortRating.setOnClickListener(this);


        mFABMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(btnSortName)
                .addSubActionView(btnSortDate)
                .addSubActionView(btnSortRating)
                .attachTo(floatingActionButton) // created at top
                .build();
// Floating Action Button
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tab_library, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if(id==R.id.next)
        {
            Intent intent = new Intent(this, subAct.class );
            startActivity(intent);
        }


        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onTabSelected(MaterialTab materialTab) {

        mPager.setCurrentItem(materialTab.getPosition());
    }

    @Override
    public void onTabReselected(MaterialTab materialTab) {

    }

    @Override
    public void onTabUnselected(MaterialTab materialTab) {

    }

    // ********************generated auto by implementing onclick listener interface for FAB..

    @Override
    public void onClick(View v) {


        //call instantiateitem insteadof getitem because getItem always return a new Fragment instance, which is not tied
        //to the Activity, the getActivity() method returns null, however the instantiateItem method will return
        //a cached fragment if exists or a new fragment (in this case it will be always a cached fragment)
        //Fragment fragment=  myPagerAdapter.getItem(mPager.getCurrentItem()); crashes app, fragment not yet instantiated


        Fragment fragment = (Fragment) myPagerAdapter.instantiateItem(mPager,mPager.getCurrentItem());

        if(fragment instanceof SortListener)
        {

            if (v.getTag().equals(TAG_SORT_NAME)) {

                ((SortListener) fragment).onSortByName();

            } else if (v.getTag().equals(TAG_SORT_DATE)) {

                ((SortListener) fragment).onSortByDate();

            } else if (v.getTag().equals(TAG_SORT_RATING)) {
                ((SortListener) fragment).onSortByRating();

            }
        }




    }
    public void onDrawSlide(float slideOffset){

        toggleTranslateFAB(slideOffset);
    }

    private void toggleTranslateFAB(float slideOffset)
    {
        if (mFABMenu != null) {
            if (mFABMenu.isOpen()) {
                mFABMenu.close(true);
            }
            floatingActionButton.setTranslationX(slideOffset * 300);
        }
    }

    private  class MyPagerAdapter extends FragmentStatePagerAdapter {

        /*
        * FragmentPagerAdapter stores the previous data which is fetched from the adapter, good for static and limited number
        * of fragments, doesn't save state
        * while FragmentStatePagerAdapter takes the new value from the adapter everytime it is executed.
        * good for big no of fragments, stats saved*/


        //Initialize String array declared in String file

        int[] icons = {android.R.drawable.btn_star, android.R.drawable.alert_dark_frame, android.R.drawable.ic_delete };;


        public MyPagerAdapter(FragmentManager fm) {
            super(fm);


        }

        @Override
        public Fragment getItem(int position) { // we need three fragments, because we load data from 3 different feeds


            Fragment myFragment= null;

            switch (position)
            {
                case MOVIES_SEARCH_RESULTS:
                    myFragment = Fragment_Search.newInstance(" ", " ");
                    // as this Fragment_search was auto created by system so it needs two argument now that's why
                    // we just pass two blank arguments..
                    // also it auto imports android.app.Fragment; we need to replace it with the
                    //import android.support.v4.app.Fragment;

                    break;
                case MOVIES_HITS:
                    myFragment = FragmentBoxOffice.newInstance(" ", " ");

                    break;
                case MOVIES_UPCOMING:
                    myFragment = Fragment_UpComing.newInstance(" ", " ");
                    break;

            }


            return myFragment;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            return getResources().getStringArray(R.array.tabs)[position];



        }

        @Override
        public int getCount() {
            return 3;
        }


        private Drawable getIcon(int position)
        {
            return getResources().getDrawable(icons[position]);
        }
    }


}
