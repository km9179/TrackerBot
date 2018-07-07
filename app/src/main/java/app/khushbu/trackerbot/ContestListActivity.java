package app.khushbu.trackerbot;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.Map;
import java.util.TreeMap;


public class ContestListActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    public static int siteKey;

    Toolbar toolbar;
    static TextView textToolbar;
    static CheckBox toolbarCheckbox;

    TabLayout tabLayout;
    Database database;

    UpdateDatabase updateDatabase;
    //MainActivity mainActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contest_list);

        new InternetCheck(this).isInternetConnectionAvailable(new InternetCheck.InternetCheckListener() {

            @Override
            public void onComplete(boolean connected) {
                //proceed!
                MainActivity.isConnected=connected;
            }
        });
        database=new Database();
        updateDatabase =new UpdateDatabase();


        tabLayout=(TabLayout)findViewById(R.id.tabs);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        //getSupportActionBar().setTitle();
        textToolbar=(TextView)toolbar.findViewById(R.id.textToolbar);
        toolbarCheckbox = (CheckBox)toolbar.findViewById(R.id.toolbarCheckBox);
        toolbarCheckbox.setChecked(false);
        toolbarCheckbox.setVisibility(View.GONE);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        /*mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        */
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(Upcoming.is_in_actionMode){
                    clearActionMode();
                }
                //new TabLayout.ViewPagerOnTabSelectedListener(mViewPager);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if(Upcoming.is_in_actionMode){
                    clearActionMode();
                }
                //new TabLayout.ViewPagerOnTabSelectedListener(mViewPager);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                if(Upcoming.is_in_actionMode){
                    clearActionMode();
                }
                //new TabLayout.ViewPagerOnTabSelectedListener(mViewPager);
            }
        });
        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        Intent intent=getIntent();
        siteKey=intent.getIntExtra("id",-1);
        Map names=new TreeMap();
        names.put(1,"CodeForces");
        names.put(2,"Codechef");
        names.put(12,"Topcoder");
        names.put(63,"Hackerrank");
        names.put(73,"Hackerearth");

        getSupportActionBar().setTitle(names.get(siteKey).toString());

    }

    public void changeMenu(int id){
        toolbar.getMenu().clear();
        if(id==1) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            toolbar.inflateMenu(R.menu.menu_contest_list_action_mode);
        }
        else{
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            toolbar.inflateMenu((R.menu.menu_contest_list));
        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_contest_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_fav) {

            try {
                updateDatabase.execute();
            }
            catch (Exception e){

            }
            /*for(int i=0;i<Upcoming.selectedContest.size();i++)
            {

                //Fav.favContestData.add(Upcoming.selectedContest.get(i));
                //long iddd=database.insert(Upcoming.selectedContest.get(i).getEvent_names(),Upcoming.selectedContest.get(i).getImgId(),Upcoming.selectedContest.get(i).getEvent_start_time(),Upcoming.selectedContest.get(i).getEvent_end_time(),Upcoming.selectedContest.get(i).getEvent_duration(),Upcoming.selectedContest.get(i).getEvent_url());
                Message.message(this,"Inserted Data");
            }*/
            //mainActivity.getData();
            Message.message(this,"Favourite Data");

            //Upcoming.adapter.show_data();

            //database.deleteRow(new Time().getCurrentTimeStamp());
            //database.show_data();
            //mainActivity.show_data();

            //Upcoming.adapter.helper.onOpen(Upcoming.adapter.db);
            //Toast.makeText(getApplicationContext(),"Added to favourites",Toast.LENGTH_SHORT).show();
            // add arraylist selectedContest ArrayList in to database;

            //for(int i=0;i<Fav.favContestData.size();i++)
            //  Log.i("msg",Fav.favContestData.get(i).getEvent_names());

            clearActionMode();
            return true;
        }
        else if(id == android.R.id.home){
            if(Upcoming.is_in_actionMode) {
                clearActionMode();
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(Upcoming.is_in_actionMode){
            clearActionMode();
            return;
        }

        super.onBackPressed();
    }

    public void clearActionMode(){
        Upcoming.swipeRefreshLayout.setEnabled(true);
        changeMenu(2);
        Upcoming.is_in_actionMode=false;
        Upcoming.adapter.notifyDataSetChanged();
        //Upcoming.selectedContest.clear();
        Upcoming.counter = 0;
        setLayoutScrollFlags(2);
        textToolbar.setVisibility(View.GONE);
        toolbarCheckbox.setChecked(false);
        toolbarCheckbox.setVisibility(View.GONE);
    }

    public void setLayoutScrollFlags(int id){

        AppBarLayout.LayoutParams toolbarParams=(AppBarLayout.LayoutParams) toolbar.getLayoutParams();
        if(id==1) {
            toolbarParams.setScrollFlags(0);
        }
        else{
            toolbarParams.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL
                    | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS);
        }
    }

    public void onToolbarCheckBoxClick(View view) {
        toolbarCheckbox =(CheckBox)view;
        if(toolbarCheckbox.isChecked()){
            for(int i=0;i<Upcoming.adapter.getItemCount();i++){
                if(!Upcoming.adapter.getItem(i).isSelected()) {
                    Upcoming.adapter.getItem(i).setSelected(true);
                    Upcoming.selectedContest.add(Upcoming.adapter.getItem(i));
                    Upcoming.counter+=1;
                }
                Upcoming.adapter.notifyDataSetChanged();
                Upcoming.updateCounter(Upcoming.counter);
            }
        }
        else{
            for(int i=0;i<Upcoming.adapter.getItemCount();i++){
                if(Upcoming.adapter.getItem(i).isSelected()) {
                    Upcoming.adapter.getItem(i).setSelected(false);
                    Upcoming.selectedContest.remove(Upcoming.adapter.getItem(i));
                    Upcoming.counter -= 1;
                }
                Upcoming.adapter.notifyDataSetChanged();

                Upcoming.updateCounter(Upcoming.counter);
            }
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            //View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            //TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            //textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return null;
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
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            //return PlaceholderFragment.newInstance(position + 1);
            switch(position) {
                case 0:
                    Ongoing ongoing = new Ongoing();
                    return ongoing;
                case 1:
                    Upcoming upcoming=new Upcoming();
                    return upcoming;
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }
    }

    class UpdateDatabase extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            for(int i=0;i<Upcoming.selectedContest.size();i++)
            {
                //Fav.favContestData.add(Upcoming.selectedContest.get(i));
                try {
                    long iddd = database.insert(Upcoming.selectedContest.get(i).getEvent_names(), Upcoming.selectedContest.get(i).getImgId(), Upcoming.selectedContest.get(i).getEvent_start_time(), Upcoming.selectedContest.get(i).getEvent_end_time(), Upcoming.selectedContest.get(i).getEvent_duration(), Upcoming.selectedContest.get(i).getEvent_url());
                }
                catch (Exception e){
                    String errorString = Upcoming.selectedContest.get(i).getEvent_names();
                    errorString = errorString.replace("'","");
                    //errorString =errorString.replaceAll("\"","\"");
                    //errorString = StringEscapeUtils.escapeJava(errorString);
                    Upcoming.selectedContest.get(i).setEvent_names(errorString);
                    long iddd = database.insert(Upcoming.selectedContest.get(i).getEvent_names(), Upcoming.selectedContest.get(i).getImgId(), Upcoming.selectedContest.get(i).getEvent_start_time(), Upcoming.selectedContest.get(i).getEvent_end_time(), Upcoming.selectedContest.get(i).getEvent_duration(), Upcoming.selectedContest.get(i).getEvent_url());

                }
                //Message.message(ContestListActivity.this,"Inserted Data");
            }
            Upcoming.selectedContest.clear();
            return null;
        }
    }
}
