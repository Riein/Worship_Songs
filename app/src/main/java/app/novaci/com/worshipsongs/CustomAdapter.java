package app.novaci.com.worshipsongs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Sasha on 7/29/2016.
 */
public class CustomAdapter extends BaseAdapter{

    Context context;
    ArrayList<RowItem> rowItems;

    CustomAdapter(Context context, ArrayList<RowItem> rowItems) {
        this.context = context;
        this.rowItems = rowItems;
    }

    @Override
    public int getCount() {
        return rowItems.size();
    }

    @Override
    public Object getItem(int position) {
        return rowItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return rowItems.indexOf(getItem(position));
    }

    private class Holder {
        TextView title;
        TextView description;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder = null;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        // If the View has not been created, create it, else use it again to avoid recreating each time
        if(convertView == null) {
            convertView = inflater.inflate(R.layout.fragment_main, null);
        } else {
            holder = (Holder) convertView.getTag();
        }

        holder = new Holder();
        holder.title = (TextView) convertView.findViewById(R.id.firstLine);
        holder.description = (TextView) convertView.findViewById(R.id.secondLine);

        RowItem row = rowItems.get(position);

        holder.title.setText(row.getText());
//            holder.description.setText(row.getDescription());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SongActivity.class);
                intent.putExtra("title", rowItems.get(position).getText());
                intent.putExtra("description", rowItems.get(position).getDescription());
                context.startActivity(intent);
            }
        });
        convertView.setTag(holder);


        return convertView;
    }
}
