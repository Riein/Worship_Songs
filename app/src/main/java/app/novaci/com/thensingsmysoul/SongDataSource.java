package app.novaci.com.thensingsmysoul;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sasha on 11/14/2016.
 */


public class SongDataSource {

    private SQLiteDatabase database;
    private DatabaseManager dbHelper;
    private String[] mAllColumns = {
            DatabaseManager.COLUMN_NAME_UUID,
            DatabaseManager.COLUMN_NAME_TITLE,
            DatabaseManager.COLUMN_NAME_TEXT,
            DatabaseManager.COLUMN_NAME_LANGUAGE,
            DatabaseManager.COLUMN_NAME_NUMBER
    };

    public SongDataSource(Context context) {
        dbHelper = new DatabaseManager(context);
    }

    public void open() {
        try {
            database = dbHelper.open();

        } catch (SQLException ex) {
            Log.w("SQLException", ex.fillInStackTrace());
        }
    }

    /**
     * Gets all songs by language from the database
     * @param language
     * @return
     */

    public ArrayList<SongInfo> getSongsByLanguage(String language) {
        ArrayList<SongInfo> songs = new ArrayList<>();

        String selection = DatabaseManager.COLUMN_NAME_LANGUAGE + " = ?";
        String[] selectionArgs1 = {language};

        String sortOrder = DatabaseManager.COLUMN_NAME_TITLE + " ASC";

        String select = "SELECT * FROM " + DatabaseManager.TABLE_NAME +
                " WHERE language = \"" + language + "\" ORDER BY "
                + DatabaseManager.COLUMN_NAME_TITLE + " ASC";
        Cursor cursor = dbHelper.open().query(DatabaseManager.TABLE_NAME, mAllColumns, selection,
                selectionArgs1, null, null, sortOrder);
        //dbHelper.open().rawQuery(select, null);


        if (cursor != null) {
            cursor.moveToFirst();
            while(!cursor.isAfterLast())
            {
                SongInfo songInfo = cursorToSong(cursor);
                songs.add(songInfo);
                cursor.moveToNext();
            }
        }
        // make sure to close the cursor
        cursor.close();

        return songs;
    }

    public List<SongInfo> getAllSongs() {
        List<SongInfo> songs = new ArrayList<SongInfo>();
        String select = "SELECT * FROM songs";
        Cursor cursor = dbHelper.open().rawQuery(select,null);
        //Cursor cursor = database.query(SongDBHelper.TABLE_NAME, allColumns, null, null, null, null,null);
        //database.rawQuery(select,null);
        if (cursor != null) {
            cursor.moveToFirst();
            do {
                SongInfo songInfo = cursorToSong(cursor);
                songs.add(songInfo);
            } while (cursor.moveToNext());
        }
        // make sure to close the cursor
        cursor.close();
        return songs;
    }


    private SongInfo cursorToSong(Cursor cursor) {
        SongInfo song = new SongInfo();
        song.setUUID(cursor.getInt(0));
        song.setTitle(cursor.getString(1));
        song.setText(cursor.getString(2));
        song.setLanguage(cursor.getString(3));
        song.setNumber(cursor.getInt(4));
        return song;
    }
}
