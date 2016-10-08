package app.novaci.com.worshipsongs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by Sasha on 9/10/2016.
 */
public class SongPager extends ListFragment {

    ArrayList<RowItem> rowItems;

    public SongPager() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        rowItems = new ArrayList<>();
        //Read Info from database?
        String titles[] = getResources().getStringArray(R.array.russiantitles);
        String descriptions[] = getResources().getStringArray(R.array.russiantext);

        for (int i = 0; i < 2; i++) {
            RowItem item = new RowItem(titles[i], descriptions[i]);
            rowItems.add(item);
        }
        CustomAdapter adapter = new CustomAdapter(getActivity(), rowItems);
        setListAdapter(adapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().getMenuInflater().inflate(R.menu.menu_main, menu);
    }

}
