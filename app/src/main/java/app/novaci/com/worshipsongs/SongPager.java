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
    String m_language;
    ArrayList<SongInfo> m_list;

    public SongPager() {

    }

    public static SongPager newInstance(String language, ArrayList<SongInfo> list) {
        SongPager s = new SongPager();

        // Supply language as an argument
        Bundle args = new Bundle();
        args.putString("language", language);
        args.putSerializable("list", list);
        s.setArguments(args);
        return s;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        m_language = getArguments() != null ? getArguments().getString("language") : "English";
        m_list = (ArrayList<SongInfo>) getArguments().getSerializable("list");
    }

    /**
     * onCreateView method
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return Inflated view
     */

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
        if (m_language == "English") {

        } else if (m_language == "Ukrainian") {

        } else if (m_language == "Russian") {

        }
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
