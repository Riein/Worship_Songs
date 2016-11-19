package app.novaci.com.worshipsongs;

import android.content.ContentValues;
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

    public void open() {
        try {
            database = dbHelper.getReadableDatabase();

        } catch (SQLException ex) {
            Log.w("SQLException", ex.fillInStackTrace());
        }
    }

    public void close() {
        dbHelper.close();
    }

    public List<SongInfo> getSongsByLanguage(String language) {
        List<SongInfo> songs = new ArrayList<SongInfo>();

        String[] projection = {
                SongDBHelper.COLUMN_NAME_UUID,
                SongDBHelper.COLUMN_NAME_TITLE,
                SongDBHelper.COLUMN_NAME_TEXT,
                SongDBHelper.COLUMN_NAME_LANGUAGE,
                SongDBHelper.COLUMN_NAME_NUMBER
        };

        String selection = SongDBHelper.COLUMN_NAME_LANGUAGE + " = ?";
        String[] selectionArgs1 = {language};

        String sortOrder = SongDBHelper.COLUMN_NAME_TITLE + " DESC";

        Cursor cursor = database.query(SongDBHelper.TABLE_NAME, projection, selection,
                selectionArgs1, null, null, sortOrder);

        cursor.moveToFirst();
        SongInfo songInfo1 = cursorToSong(cursor);
        while (!cursor.isAfterLast()) {
            SongInfo songInfo = cursorToSong(cursor);
            songs.add(songInfo);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();

        return songs;
    }

    public List<SongInfo> getAllSongs() {
        this.open();
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
