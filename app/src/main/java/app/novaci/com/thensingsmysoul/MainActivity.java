package app.novaci.com.thensingsmysoul;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
   
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

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
    private ArrayList<SongInfo> EnglishSongs;
    private ArrayList<SongInfo> UkrainianSongs;
    private ArrayList<SongInfo> RussianSongs;
    SQLiteOpenHelper m_DBHelper;

    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        MobileAds.initialize(getApplicationContext(), "ca-app-pub-2974888235042322~6904381296");
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        SongDataSource songDataSource = new SongDataSource(this);
        //List<SongInfo> songInfoList = songDataSource.getAllSongs();
        RussianSongs = songDataSource.getSongsByLanguage("russian");
        EnglishSongs = songDataSource.getSongsByLanguage("english");
        UkrainianSongs = songDataSource.getSongsByLanguage("ukrainian");

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), this, EnglishSongs, UkrainianSongs, RussianSongs);
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        if (tabLayout != null)
            tabLayout.setupWithViewPager(mViewPager);



        //songDataSource.close();
//      FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//      fab.setOnClickListener(new View.OnClickListener() {
//          @Override
//          public void onClick(View view) {
//              Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                      .setAction("Action", null).show();
//          }
//      });
      
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
  

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        final int PAGE_COUNT = 3;
        private String titles[] = new String[] {"English", "Український", "Русский"};
        private Context context;
        ArrayList<SongInfo> m_englishList;
        ArrayList<SongInfo> m_ukrainianList;
        ArrayList<SongInfo> m_russianList;

        public SectionsPagerAdapter(FragmentManager fm, Context context,
                                    ArrayList<SongInfo> englishList,
                                    ArrayList<SongInfo> ukrainianList,
                                    ArrayList<SongInfo> russianList) {
            super(fm);
            this.context = context;
            this.m_englishList = englishList;
            this.m_ukrainianList = ukrainianList;
            this.m_russianList = russianList;
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a SongPager instance and pass in which language is being used
            if (titles[position] == "English")
                return SongPager.newInstance(titles[position], m_englishList);
            else if (titles[position] == "Український")
                return SongPager.newInstance(titles[position], m_ukrainianList);
            else
                return SongPager.newInstance(titles[position], m_russianList);
            //return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return PAGE_COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }
}
