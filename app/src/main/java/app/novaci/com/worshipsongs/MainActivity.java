package app.novaci.com.worshipsongs;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
   
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), this);
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        if (tabLayout != null)
            tabLayout.setupWithViewPager(mViewPager);

        m_DBHelper = new SongReaderContract.SongDBHelper(this);

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
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void readDB(){
        SQLiteDatabase db = m_DBHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after the query.
        String[] projection = {
                SongReaderContract.SongEntry.COLUMN_NAME_UUID,
                SongReaderContract.SongEntry.COLUMN_NAME_TITLE,
                SongReaderContract.SongEntry.COLUMN_NAME_TEXT,
                SongReaderContract.SongEntry.COLUMN_NAME_LANGUAGE,
                SongReaderContract.SongEntry.COLUMN_NAME_NUMBER
        };

        String selection = SongReaderContract.SongEntry.COLUMN_NAME_LANGUAGE + " = ?";
        String[] selectionArgs1 = {"russian"};

        String sortOrder = SongReaderContract.SongEntry.COLUMN_NAME_TITLE + " DESC";

        Cursor c = db.query(SongReaderContract.SongEntry.TABLE_NAME, projection, selection,
                selectionArgs1, null, null, sortOrder);

        c.moveToFirst();
        //Add Code to read in data here

    }
  

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        final int PAGE_COUNT = 3;
        private String titles[] = new String[] {"English", "Ukrainian", "Russian"};
        private Context context;

        public SectionsPagerAdapter(FragmentManager fm, Context context) {
            super(fm);
            this.context = context;
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a SongPager instance and pass in which language is being used
            return SongPager.newInstance(titles[position]);
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
