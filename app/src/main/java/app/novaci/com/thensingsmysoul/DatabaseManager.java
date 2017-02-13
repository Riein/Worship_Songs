package app.novaci.com.thensingsmysoul;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Sasha on 12/14/2016.
 */
public class DatabaseManager {
    public static final String TABLE_NAME = "songs";
    public static final String COLUMN_NAME_UUID = "UUID";
    public static final String COLUMN_NAME_TITLE = "title";
    public static final String COLUMN_NAME_TEXT = "text";
    public static final String COLUMN_NAME_LANGUAGE = "language";
    public static final String COLUMN_NAME_NUMBER = "number";

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                    COLUMN_NAME_UUID + " INTEGER PRIMARY KEY," +
                    COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
                    COLUMN_NAME_LANGUAGE + TEXT_TYPE + COMMA_SEP +
                    COLUMN_NAME_TEXT + TEXT_TYPE + COMMA_SEP +
                    COLUMN_NAME_NUMBER + " INTEGER" + ")";
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    private static final String DATABASE_NAME = "songs.db";
    private static final int DATABASE_VERSION = 2;

    private String mDatabasePath;
    private DBHelper mDatabaseHelper;
    private Context mContext;

    public DatabaseManager (Context context){
        mContext = context;
        mDatabaseHelper = new DBHelper(context);
        mDatabasePath = context.getDatabasePath(DATABASE_NAME).toString();
        try {
            createDataBase();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public void createDataBase() throws IOException {

        if (!checkDataBase()) {
            mDatabaseHelper.getReadableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    /**
     * Check if the database already exist to avoid re-copying the file each
     * time you open the application.
     *
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase() {
        boolean check=false;
        SQLiteDatabase checkDB = null;
        try {
            checkDB = SQLiteDatabase.openDatabase(mDatabasePath, null,
                    SQLiteDatabase.OPEN_READONLY);

        } catch (SQLiteException e) {
            Log.v("DB", "No DB");
            // database does't exist yet.
        }
        if (check = (checkDB != null)) {
            checkDB.close();
        }
        return check;
    }

    /**
     * Copies your database from your local assets-folder to the just created
     * empty database in the system folder, from where it can be accessed and
     * handled. This is done by transfering bytestream.
     * */
    private void copyDataBase() throws IOException {

        // Open your local db as the input stream
        InputStream input = mContext.getResources().getAssets().open(DATABASE_NAME);

        // Open the empty db as the output stream
        OutputStream output = new FileOutputStream(mDatabasePath);

        // transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = input.read(buffer)) > 0) {
            output.write(buffer, 0, length);
        }

        // Close the streams
        output.flush();
        output.close();
        input.close();

    }


    public String getDatabasePath(){
        return mDatabasePath;
    }

    public SQLiteDatabase open (){
        return mDatabaseHelper.getReadableDatabase();
    }

    private static class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        // Method is called during creation of the database
        public void onCreate(SQLiteDatabase database) {
            database.execSQL(SQL_CREATE_ENTRIES);
        }

        // Method is called during an upgrade of the database,
        // e.g. if you increase the database version
        public void onUpgrade(SQLiteDatabase database, int oldVersion,
                              int newVersion) {
            database.execSQL(SQL_DELETE_ENTRIES);
            onCreate(database);
        }
    }
}
