package com.community.desibot.desibotv1;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class navigationFragment extends Fragment {

    public static final String PREF_FILE_NAME = "testpref";
    public static final String KEY_USR_LEARNED_DRAWER = "user_learned_drawer";
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private boolean mUserLearnedDrawer;
    private boolean mFromSavedInstanceState;
    private View containerView;
    public navigationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserLearnedDrawer = Boolean.valueOf(readPreference(getActivity(),KEY_USR_LEARNED_DRAWER,"false"));
        if(savedInstanceState != null){
            mFromSavedInstanceState = true;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_navigation, container, false);
    }

    public void setup(int fragID,DrawerLayout dLayout, Toolbar tBar) {
        containerView = getActivity().findViewById(fragID);
        mDrawerLayout = dLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(),dLayout,tBar,R.string.drawer_open,R.string.drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if(!mUserLearnedDrawer){
                    mUserLearnedDrawer =  true;
                    saveToPreference(getActivity(),KEY_USR_LEARNED_DRAWER,mUserLearnedDrawer+"");
                }
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }
        };
        if(!mUserLearnedDrawer && !mFromSavedInstanceState){
            mDrawerLayout.openDrawer(containerView);
        }
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
    }
    public static void saveToPreference(Context cxt,String prefName,String prefValue){
        SharedPreferences sharePref = cxt.getSharedPreferences(PREF_FILE_NAME,cxt.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharePref.edit();
        editor.putString(prefName,prefValue);
        editor.apply();
    }
    public static String readPreference(Context cxt, String prefName, String defValue){
        SharedPreferences sharePref = cxt.getSharedPreferences(PREF_FILE_NAME,cxt.MODE_PRIVATE);
        return sharePref.getString(prefName,defValue);
    }
}
