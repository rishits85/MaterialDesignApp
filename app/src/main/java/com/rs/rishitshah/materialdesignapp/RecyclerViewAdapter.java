package com.rs.rishitshah.materialdesignapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

/**
 * This is our custom adapter for recyclerView. It takes in ViewHolder as a parameter
 * which will be used in subsequent implemented methods
 * NEVER DISPLAY DYNAMIC CONTENT IN YOUR NAVIGATION DRAWER
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.myViewHolder>{

    List<Information> data = Collections.emptyList();
    private LayoutInflater inflater;
    Context context;
    /*IMP MESSAGE BELOW. MUST READ*/
//    this has to be private. Otherwise the onclick method in the
//     view holder will directly access this null reference here and throw a nullpointer exception.
//     Keeping this private will force it to use the value stored in the setClickListener method
//    private ClickListener clickListener;

    //this constructor initializes the inflater
    RecyclerViewAdapter(Context context, List<Information> data){
        this.context = context;
        //this is the way to get a LayoutInflator to your adapter as it is never used directly. Read the docs
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    //viewHolder is created. This will return the viewHolder.
    // You pass in a view which will be recycled. This is called every time a new row is created
    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_row,parent,false);
        myViewHolder viewHolder = new myViewHolder(view);
        return viewHolder;
    }



    //this method is used to display items of the view adapter
    @Override
    public void onBindViewHolder(myViewHolder holder, final int position) {

        //gets data from the empty arrayList data initialized above
        Information current = data.get(position);
        //title and iconId defined in the Information java class
        holder.title.setText(current.title);
        holder.image.setImageResource(current.iconId);
        //this is done as recyclerView does not have onItemClickListener method like listView.
        //same could be done by implementing the View.onClickListener in the viewHolder as opposed to doing it here in the adapter.
//        holder.image.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
    }

    //read the note above clickListener interface at the bottom of RecycreViewAdapter
//    public void setClickListener(ClickListener clickListener){
//        this.clickListener = clickListener;
//    }
    //return number of items in the data set held by the adapter
    @Override
    public int getItemCount() {

        return data.size();
    }

    public void delete(int position){
        data.remove(position);
        notifyItemRemoved(position);

    }

    //this is created as a sub class as it has to be passed as an object to the adapter
    class myViewHolder extends RecyclerView.ViewHolder{

        public ImageView image;
        public TextView title;

        //this constructor creates the new view required to represent
        public myViewHolder(final View itemView) {

            //find the views from the root view
            super(itemView);
            title = (TextView)itemView.findViewById(R.id.listText);
            image = (ImageView) itemView.findViewById(R.id.listImage);
            //read the note above clickListener interface at the bottom of RecycreViewAdapter
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
////                    if(clickListener != null){
//                        clickListener.itemClicked(v,getPosition());
////                    }
//                }
//            });



        }

    }

    /* Imp must read */
    //this interface is created to solve the purpose of giving the control
    //of starting an activity to the fragment. Reason is to allwo re using this adapter for any other recyclerview
    //The flow goes as follows. This is implemented by the fragment which means it is overrides itemClicked method.
    // In this method you pass the intent to start the activity.
    //the iteClicked method is called in the viewholder where the onClickListener is set on the view.
    //to access this method in the viewholder you need an object of the clicklistener which cannot be created as it is an interface
    //thus we write a method setClicklistener in the adapter and take in ClickListener as parameter.
    // It is then called in the NavigationDrawerFragment
    // passing "this" argument when calling the method will set the fragment as an object for our use
    //this.clickListener = clickListner in the method setClickListener saves this object reference.
    //now we can use clicklistener in the OnclickListener's onClick method.
//    public interface ClickListener{
//
//        public void itemClicked(View v, int position);
//        public void itemLongClicked(View v, int position);
//    }


}
