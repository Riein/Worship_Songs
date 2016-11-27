package app.novaci.com.worshipsongs;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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
    private static String DB_PATH = "/data/data/app.novaci.com.worshipsongs/databases/";
    private static String DB_NAME = "songs.db";
    public static final String DATABASE_NAME = "songs";//"C:\\Users\\Sasha\\AndroidStudioProjects\\WorshipSongs\\app\\src\\main\\assets\\songs.db";
    private final Context context;
    public SQLiteDatabase m_Database;

    public SongDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
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

    public void createDataBase() throws IOException {
        boolean dbExist = checkDataBase();

        if (!dbExist) {
            try {
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;

        try {
            String path = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
        } catch(SQLiteException e) {
            //Database doesn't exist
        }

        if (checkDB != null) {
            checkDB.close();
        }

        return checkDB != null ? true : false;
    }


    private void copyDataBase() throws IOException {
        // Open local database as input stream
        InputStream inputStream = context.getAssets().open(DB_NAME);

        // Create path to the empty database
        String outFilename = DB_PATH + DB_NAME;

        // Open the empty database as an output stream
        OutputStream outputStream = new FileOutputStream(outFilename);

        // Transfer bytes from the Input file to the Output file
        byte[] buffer = new byte[2048];
        int length;
        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
        }

        // Close the Streams
        outputStream.flush();
        outputStream.close();
        inputStream.close();
    }

    public void openDataBase() throws SQLException {
        //Open Database
        String path = DB_PATH + DB_NAME;
        m_Database = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
    }
}
