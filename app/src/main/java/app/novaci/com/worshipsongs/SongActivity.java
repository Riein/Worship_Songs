package app.novaci.com.worshipsongs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SongActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);

        Bundle extras = getIntent().getExtras();

        String title = extras.getString("title");
        String desc = extras.getString("description");

        TextView titleView = (TextView) findViewById(R.id.song_title);
        titleView.setText(title);
        TextView descriptionView = (TextView) findViewById(R.id.body);
        descriptionView.setText(desc);

        // Remove Data
        extras.remove("title");
        extras.remove("description");
    }
}
