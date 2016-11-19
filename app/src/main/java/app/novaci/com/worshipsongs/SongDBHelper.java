package app.novaci.com.worshipsongs;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Sasha on 11/7/2016.
 */
public class SongDBHelper extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "songs";
    public static final String COLUMN_NAME_UUID = "UUID";
    public static final String COLUMN_NAME_TITLE = "title";
    public static final String COLUMN_NAME_TEXT = "text";
    public static final String COLUMN_NAME_LANGUAGE = "language";
    public static final String COLUMN_NAME_NUMBER = "number";

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_NAME_UUID + " INTEGER PRIMARY KEY," +
                    COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
                    COLUMN_NAME_LANGUAGE + TEXT_TYPE + COMMA_SEP +
                    COLUMN_NAME_TEXT + TEXT_TYPE + COMMA_SEP +
                    COLUMN_NAME_NUMBER + " INTEGER" + ")";
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;


    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "C:\\Users\\Sasha\\AndroidStudioProjects\\WorshipSongs\\app\\src\\main\\assets\\songs.db";

    public SongDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
        onCreate(sqLiteDatabase);
    }

}
