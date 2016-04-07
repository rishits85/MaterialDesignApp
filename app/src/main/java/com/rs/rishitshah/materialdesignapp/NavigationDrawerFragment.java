package com.rs.rishitshah.materialdesignapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class NavigationDrawerFragment extends android.support.v4.app.Fragment  {

    public  final String TAG = "Myapp";
    public RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private static final String FILE_NAME = "user_preferences";
    public static final String USER_LEARNED_DRAWER_KEY ="user_learned_drawer";
    ActionBarDrawerToggle mDrawertoggle;
    private DrawerLayout mDrawerLayout;
    public boolean mUserLearnedDrawer;
    public boolean mFromSavedInstanceState;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
//
//    private OnFragmentInteractionListener mListener;

    public NavigationDrawerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment NavigationDrawerFragment.
     */
    // TODO: Rename and change types and number of parameters
//    public static NavigationDrawerFragment newInstance(String param1, String param2) {
//        NavigationDrawerFragment fragment = new NavigationDrawerFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserLearnedDrawer = Boolean.valueOf(readFromPeferences(getActivity(), USER_LEARNED_DRAWER_KEY, "false"));
        recyclerViewAdapter = new RecyclerViewAdapter(getActivity(),getData());
        //read the note above clickListener interface at the bottom of RecycreViewAdapter
//        recyclerViewAdapter.setClickListener(this);

        // check if the activity is coming back from rotation or started for first time
        if(savedInstanceState != null){
            mFromSavedInstanceState = true;
        }
    }

    //this method will return a list object that is needed in the adapter's constructor
    public static List<Information> getData(){
        List<Information> data = new ArrayList<>();
        //arrayList of data
        int[] iconResource = {R.drawable.ic_menu_camera,R.drawable.ic_menu_gallery};
        String[] text = {"Camera", "Gallery"};
        for(int i = 0; i<iconResource.length && i<text.length; i++ ) {
            //this is not declared outside as every time new information object is created and added to arraylist
            Information current = new Information();
            //add data to information object
            current.iconId = iconResource[i];
            current.title = text[i];
            data.add(current);
        }

        return data;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        //this is to initialize recyclerView. This will throw NullPointerException if no layout manager specified
        recyclerView = (RecyclerView) layout.findViewById(R.id.my_recycler_view);
        //create an object of view adapter
        recyclerViewAdapter = new RecyclerViewAdapter(getActivity(),getData());
        //set the adapter for recyclerview
        recyclerView.setAdapter(recyclerViewAdapter);
        //layout manager is required to define the kind of
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerView.addOnItemTouchListener(new OnItemTouch(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void itemClicked(View v, int position) {

            }

            @Override
            public void itemLongClicked(View v, int position) {

            }
        }));
        Log.d(TAG,"oncreateview called");
        return layout;

    }


    @Override
    public void onPrepareOptionsMenu(Menu menu){
    super.onPrepareOptionsMenu(menu);
        //use this method to dynamically change the contents of fragment.
        // Not sure if this method has to be in the activity or the here in the fragment

    }
//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }

    public void setUp(int fragmentId, DrawerLayout drawerLayout, Toolbar toolbar) {
        //done to initialize the view required for passing into OpenDrawer method
        View drawerView = getActivity().findViewById(fragmentId);

        mDrawerLayout = drawerLayout;
        mDrawertoggle = new ActionBarDrawerToggle(getActivity(), mDrawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer){
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getActivity().invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //calls OnPrepareOptionsMenu if this method was written an activity no need to use getActivity
                getActivity().invalidateOptionsMenu();
                //below indicates if user has opened drawer for the first time
                if (!mUserLearnedDrawer){
                    //means the user just saw the drawer
                    mUserLearnedDrawer = true;
                    //save this value to shared preferences. The last parameter of this method is a string
                    //thus concatenation of blank string to a boolean value causes it to convert to string
                    saveToPreferences(getActivity(),USER_LEARNED_DRAWER_KEY, mUserLearnedDrawer + "");
                }

            }

        };

        //if the user has never seen the navigationDrawer and the fragment is being created for the first time,
        //ie if the values of mUserLearnedDrawer and mFromSavedInstanceState are both false
        // Open navigation drawer
        if(!mUserLearnedDrawer && !mFromSavedInstanceState){
            mDrawerLayout.openDrawer(drawerView);
        }

        mDrawerLayout.setDrawerListener(mDrawertoggle);
        //this is done as syncState needs to be called in onPostCreate of an activity.
        // But fragment does not have that method so we are calling it in a runnable
        //the post method helps in adding the task to a message que and to be
        // fired in a new thread once it is fetched from the que
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                //sync the state of navigation drawer with activity. Here it helps to show the hamburger icon for nav drawer
                mDrawertoggle.syncState();
            }
        });
    };

    //write data to shared preferences
    public static void saveToPreferences(Context context, String key, String value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILE_NAME, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        //used in place of commit as it is faster. This does the task asynchronously
        editor.apply();

    }

    //read data from shared preferences
    public static String readFromPeferences(Context context, String key,String defaultValue){
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILE_NAME, context.MODE_PRIVATE);
        return sharedPreferences.getString(key,defaultValue);
    }

    //read the note above clickListener interface at the bottom of RecycreViewAdapter
//    @Override
//    public void itemClicked(View v, int position) {
//        startActivity(new Intent(getActivity(),SubActivity.class));
//    }
//
//    @Override
//    public void itemLongClicked(View v, int position) {
//
//    }

    // this demonstrates use of onItemTouchListener as opposed to OnItemClickListener
    // as opposed to using custom click listener created by us
    //it uses gestureDetector to check long press or simple clicks.
    public class OnItemTouch implements RecyclerView.OnItemTouchListener {
        //gesture detector mentioned here as it is used only in the listener
        private GestureDetector gestureDetector;
        public OnItemTouch(FragmentActivity activity, RecyclerView recyclerView, ClickListener clickListener) {
        }

        public void onItemTouch(Context context, RecyclerView view, final ClickListener clickListener){
            //see documentation for why we did this
            gestureDetector =  new GestureDetector(context,new SimpleOnGestureListener(){
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    //this means the touch event is handled here
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                   View child = recyclerView.findChildViewUnder(e.getX(),e.getY());
                    if(child != null && clickListener != null){
                        clickListener.itemLongClicked(child, recyclerView.getChildAdapterPosition(child));
                    }
                    super.onLongPress(e);
                }
            });


        }
        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            //passes the event to gesture detectore
            View childV = recyclerView.findChildViewUnder(e.getX(),e.getY());
            gestureDetector.onTouchEvent(e);
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }

    }

    public static interface ClickListener {

        public void itemClicked(View v, int position);
        public void itemLongClicked(View v, int position);
    }




        /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}



