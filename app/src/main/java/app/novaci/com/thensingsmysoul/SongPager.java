package app.novaci.com.thensingsmysoul;

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

        String titles[] = getTitlesFromList();//getResources().getStringArray(R.array.russiantitles);
        String descriptions[] = getDescriptionsFromList();//getResources().getStringArray(R.array.russiantext);

        for (int i = 0; i < m_list.size(); i++) {
            if (titles[i] != "") {
                RowItem item = new RowItem(titles[i], descriptions[i]);
                rowItems.add(item);
            }
        }
        CustomAdapter adapter = new CustomAdapter(getActivity(), rowItems);
        setListAdapter(adapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().getMenuInflater().inflate(R.menu.menu_main, menu);
    }

    public String[] getTitlesFromList(){
        if (m_list.size() == 0)
            return null;
        String titleList[] = new String[m_list.size()];
        for (int i = 0; i < m_list.size(); i++){
            titleList[i] = m_list.get(i).getTitle();
        }
        return titleList;
    }

    public String[] getDescriptionsFromList(){
        if (m_list.size() == 0)
            return null;
        String descriptionList[] = new String[m_list.size() + 1];
        for (int i = 0; i < m_list.size(); i++){
            descriptionList[i] = m_list.get(i).getText();
        }
        return descriptionList;
    }
}
