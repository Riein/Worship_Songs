package app.novaci.com.worshipsongs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sasha on 11/14/2016.
 */


public class SongDataSource {

    private SQLiteDatabase database;
    private SongDBHelper dbHelper;
    private String[] allColumns = {
            SongDBHelper.COLUMN_NAME_UUID,
            SongDBHelper.COLUMN_NAME_TITLE,
            SongDBHelper.COLUMN_NAME_TEXT,
            SongDBHelper.COLUMN_NAME_LANGUAGE,
            SongDBHelper.COLUMN_NAME_NUMBER
    };

    public SongDataSource(Context context) {
        dbHelper = new SongDBHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public SongInfo createSong(int uuid, String title, String text, String language, int number) {
        ContentValues values = new ContentValues();
        values.put(SongDBHelper.COLUMN_NAME_UUID, uuid);
        values.put(SongDBHelper.COLUMN_NAME_TITLE, title);
        values.put(SongDBHelper.COLUMN_NAME_TEXT, text);
        values.put(SongDBHelper.COLUMN_NAME_LANGUAGE, language);
        values.put(SongDBHelper.COLUMN_NAME_NUMBER, number);

        long insertId = database.insert(SongDBHelper.TABLE_NAME, null, values);

        Cursor cursor = database.query(SongDBHelper.TABLE_NAME, allColumns, SongDBHelper.COLUMN_NAME_UUID + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        SongInfo newSong = cursorToSong(cursor);
        cursor.close();

        return newSong;
    }

    public SongInfo getSong(String language) {
        return null;
    }

    public List<SongInfo> getAllSongs() {
        List<SongInfo> songs = new ArrayList<SongInfo>();

        Cursor cursor = database.query(SongDBHelper.TABLE_NAME, allColumns, null, null, null, null,null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            SongInfo songInfo = cursorToSong(cursor);
            songs.add(songInfo);
            cursor.moveToNext();
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
