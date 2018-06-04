package com.example.jose5.a35mm;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.example.jose5.a35mm.modelo.Pelicula;
import com.example.jose5.a35mm.modelo.User;

import java.util.ArrayList;
import java.util.List;

public class PanelAdmin extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    //aqui guardo los querys
    ArrayList<User> users=new ArrayList<>();
    ArrayList<Pelicula> pelis=new ArrayList<>();

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panel_admin);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

    }




    public static class addMovie extends Fragment {

        public addMovie() {
        }

        public static addMovie newInstance() {
            addMovie fragment = new addMovie();
            return fragment;
        }
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.add_movie, container, false);
            return rootView;
        }
    }

    public static class userBan extends Fragment {

        public userBan() {
        }
        public static userBan newInstance() {
            userBan fragment = new userBan();
            return fragment;
        }
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.users, container, false);
            return rootView;
        }
    }

    public static class editMovie extends Fragment {

        public editMovie() {
        }

        public static editMovie newInstance() {
            editMovie fragment = new editMovie();
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.edit_movie, container, false);
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Log.println(Log.DEBUG,"freagment", String.valueOf(position));
            switch (position){
                case 0: return addMovie.newInstance();
                case 1: return userBan.newInstance();
                case 2: return editMovie.newInstance();
                default:return addMovie.newInstance();
            }

        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }
    }
    private class ListAdapter extends ArrayAdapter<String>{
        private int layout;
        public ListAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull List<String> objects) {
            super(context, resource, textViewResourceId, objects);
            layout=resource;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            ViewUser mainViewUser = null;
            if(convertView==null){
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(layout,parent,false);
                final ViewUser viewHolder = new ViewUser();
                viewHolder.email = (TextView) convertView.findViewById(R.id.user_email);
                viewHolder.user = (TextView) convertView.findViewById(R.id.user_name);
                viewHolder.ban = (Switch) convertView.findViewById(R.id.user_switch);
                viewHolder.ban.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        //hacer el ban
                        viewHolder.ban.isActivated();
                    }
                });
                convertView.setTag(viewHolder);
            }else{
                mainViewUser = (ViewUser) convertView.getTag();
                //poner los atributos
                //mainViewUser.email
            }

            return super.getView(position, convertView, parent);
        }
    }
    public class ViewUser{
        TextView email;
        TextView user;
        Switch ban;
    }
}
