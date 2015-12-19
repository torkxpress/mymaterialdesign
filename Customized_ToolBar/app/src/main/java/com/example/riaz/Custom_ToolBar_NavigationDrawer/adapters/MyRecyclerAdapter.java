package com.example.riaz.Custom_ToolBar_NavigationDrawer.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.riaz.Custom_ToolBar_NavigationDrawer.pojo.Information_NavigationDrawer;
import com.example.riaz.Custom_ToolBar_NavigationDrawer.R;

import java.util.Collections;
import java.util.List;

/**
 * Created by riaz on 23/04/15.
 */



public class MyRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater inflater;


    List<Information_NavigationDrawer> data = Collections.emptyList();
    //Use Collections.emptyList() if you want to make sure that the returned list is never modified.
    // This is what is returned on calling emptyList():
    //more suitable for let's say, error checking and the like

    // FOR NAVIGATION DRAWER HEADER AND ITEMS

    private static final int TYPE_HEADER=0;
    private static final int TYPE_ITEM=1;



    //constructor for receiving context and data

    public MyRecyclerAdapter(Context context, List<Information_NavigationDrawer> data)
    {

      inflater= LayoutInflater.from(context);
        this.data = data;
    }

    //add a container for Recycler view in fragment_navigation_drawer xml file.

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // create view and pass to view holder and the recyler view will use this view again and again
        //instead of creating new one, off course depending upon screen size...


        if(viewType ==TYPE_HEADER)
        {
            View view = inflater.inflate(R.layout.navigation_drawer_header,parent,false);

            MyViewHolder_Header viewHolder= new MyViewHolder_Header(view);

            return viewHolder;
        }

            else
        {

            View view = inflater.inflate(R.layout.custom_row_layout,parent,false);


            MyViewHolder viewHolder= new MyViewHolder(view);

            return viewHolder;
        }


    }

    @Override
    public int getItemViewType(int position)
    {
        if(position==0) return TYPE_HEADER;
        else
            return TYPE_ITEM;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {


        if(holder instanceof MyViewHolder_Header)
        {



        }else

        {

           MyViewHolder itemHolder = (MyViewHolder)holder;

            Information_NavigationDrawer currentObj = data.get(position-1);

            Log.d("MyRecycler Adapter", "On Bind ViewHolder Called " + (position-1));

            // holder is already passed to onBindViewHolder as argument, using this holder and setting
            // text and image...
            itemHolder.icon.setImageResource(currentObj.icondId);
            itemHolder.title.setText(currentObj.title);

        }
    }

    @Override
    public int getItemCount() {
        return data.size()+1;
        // you must return data size other wise no data will displayed
    }
// user defined methods on click item deleted.
    public void delete(int pos)
    {
        data.remove(pos);
        notifyItemRemoved(pos);

        /* Android Developer doesn't recommend notifyDataSetChanged() method because its very expensive operation because it
        assumes all the data is invalid and recreate every thing.
        * user the other alternative methods. like notifyItemRemoved, notifyItemRangeRemoved
        * notifyItemRemoved  notify any registered observers that the item previously at position has been removed from the data set
        * and item after position may now be found at postion = oldposition-1
        * this is structural change. Representation of other existing items in the dataset are still considered upto date and
        * will not be rebound, though their position may be altered.*/
    }



    class MyViewHolder extends RecyclerView.ViewHolder {


        TextView title;
        ImageView icon;

        public MyViewHolder(View itemView) {
            super(itemView);
           title = (TextView) itemView.findViewById(R.id.listText);
            icon = (ImageView) itemView.findViewById(R.id.listIcon);


        }


    }


    //for 14 we need another MyViewHolder_Header
    class MyViewHolder_Header extends RecyclerView.ViewHolder {


        public MyViewHolder_Header(View itemView) {
            super(itemView);



        }


    }


}
