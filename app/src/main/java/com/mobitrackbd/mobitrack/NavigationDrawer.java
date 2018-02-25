package com.mobitrackbd.mobitrack;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mobitrackbd.mobitrack.Fragments.AboutUsFragment;
import com.mobitrackbd.mobitrack.Fragments.HomeFragment;
import com.mobitrackbd.mobitrack.Utility.LocalData;


/**
 * A simple {@link Fragment} subclass.
 */
public class NavigationDrawer extends Fragment implements View.OnClickListener {

    public static final String PREF_NAME ="mypref";
    public static final String KEY_USER_LEARNED_DRAWERR="user_learned_drawer";

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;

    private boolean mUserLearnedDrawer;
    private boolean mFromSavedInstanceState;

    private RelativeLayout rlaboutus,rlHome,rlContact,rlsignout;
    private TextView tvName, tvEmail, tvUserImage;

    private LocalData localData;




    public NavigationDrawer() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserLearnedDrawer = Boolean.valueOf(readFromPreferences(getActivity(),KEY_USER_LEARNED_DRAWERR,"false"));

        // if saveInstanceState is not null its coming back from rotation
        if(savedInstanceState!=null){
            mFromSavedInstanceState=true;
        }

        localData = new LocalData(getContext());




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_navigation_drawer, container, false);


        //Initialize View

        initView(view);
        return view;
    }

    private void initView(View view) {

        rlaboutus=view.findViewById(R.id.about);
        rlHome=view.findViewById(R.id.home);
        rlContact=view.findViewById(R.id.contact);
        rlsignout = view.findViewById(R.id.signout);
        rlaboutus.setOnClickListener(this);
        rlHome.setOnClickListener(this);
        rlContact.setOnClickListener(this);
        rlsignout.setOnClickListener(this);

        tvName = view.findViewById(R.id.user_name);
        tvEmail = view.findViewById(R.id.user_mail);
        tvUserImage = view.findViewById(R.id.user_image);


        if(localData.getCustomerName()!=null){
            tvName.setText(localData.getCustomerName());
        }


        if(localData.getCustomerEmail()!=null){
            tvEmail.setText(localData.getCustomerEmail());
        }

        if(localData.getCustomerName()!=null){
            tvUserImage.setText(String.valueOf(localData.getCustomerName().toUpperCase().charAt(0)));
        }

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }



    public void setUp(int fragmentId, DrawerLayout layout, final Toolbar toolbar) {

        mDrawerLayout = layout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(),mDrawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

                //if user gonna not seen the drawer before thats mean the drawer is open for the first time

                if(!mUserLearnedDrawer){
                    mUserLearnedDrawer=true;
                    // save it in sharedpreferences
                    saveToPreferences(getActivity(),KEY_USER_LEARNED_DRAWERR,mUserLearnedDrawer+"");

                    getActivity().invalidateOptionsMenu();
                }

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);

                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

                super.onDrawerSlide(drawerView, slideOffset);
            }
        };

        mDrawerLayout.addDrawerListener(mDrawerToggle);

        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
    }


    public static void saveToPreferences(Context context, String key, String prefValue){
        SharedPreferences pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key,prefValue);
        editor.apply();
    }

    public static String readFromPreferences(Context context, String key, String defaultValue){
        SharedPreferences pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        return pref.getString(key,defaultValue);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.home:
                getFragmentManager().beginTransaction().replace(R.id.main_container,new HomeFragment())
                        .addToBackStack(null).commit();
                mDrawerLayout.closeDrawer(Gravity.LEFT);
                break;
            case R.id.about:
                getFragmentManager().beginTransaction().replace(R.id.main_container,new AboutUsFragment())
                        .addToBackStack(null).commit();
                mDrawerLayout.closeDrawer(Gravity.LEFT);
                break;
            case R.id.contact:

                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", getString(R.string.mobi_phone), null));
                startActivity(intent);
                mDrawerLayout.closeDrawer(Gravity.LEFT);
                break;
            case R.id.signout:

                localData.setLogin(false);

                getActivity().finish();
                Intent intent1 = new Intent(getContext(),LoginActivity.class);
                startActivity(intent1);
                break;
            default:
        }


    }

}
