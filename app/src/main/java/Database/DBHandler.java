package Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "FeedReader.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + UserProfile.Users.TABLE_NAME + " (" +
                    UserProfile.Users._ID + " INTEGER PRIMARY KEY," +
                    UserProfile.Users.COLUMN_NAME_USERNAME + " TEXT," +
                    UserProfile.Users.COLUMN_NAME_PASSWORD + " TEXT," +
                    UserProfile.Users.COLUMN_NAME_DATEOFBIRTH + " TEXT," +
                    UserProfile.Users.COLUMN_NAME_GENDER + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + UserProfile.Users.TABLE_NAME;

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public long addInfo(String un, String pw, String dob, String gender) {
        // Gets the data repository in write mode
        SQLiteDatabase db = getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();

        values.put(UserProfile.Users.COLUMN_NAME_USERNAME, un);
        values.put(UserProfile.Users.COLUMN_NAME_PASSWORD, pw);
        values.put(UserProfile.Users.COLUMN_NAME_DATEOFBIRTH, dob);
        values.put(UserProfile.Users.COLUMN_NAME_GENDER, gender);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(UserProfile.Users.TABLE_NAME, null, values);

        return  newRowId;
    }

    public Cursor readAllInfo() {
        // Gets the data repository in read mode
        SQLiteDatabase db = getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                BaseColumns._ID,
                UserProfile.Users.COLUMN_NAME_USERNAME,
                UserProfile.Users.COLUMN_NAME_PASSWORD,
                UserProfile.Users.COLUMN_NAME_DATEOFBIRTH,
                UserProfile.Users.COLUMN_NAME_GENDER
        };

        /*
        // Filter results WHERE "title" = 'My Title'
        String selection = UserProfile.Users.COLUMN_NAME_USERNAME + " = ?";
        String[] selectionArgs = { "" };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                UserProfile.Users.COLUMN_NAME_USERNAME + " DESC";
        */

        Cursor cursor = db.query(
                UserProfile.Users.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        return cursor;
    }

    public List readAllInfo(String un) {
        // Gets the data repository in read mode
        SQLiteDatabase db = getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                BaseColumns._ID,
                UserProfile.Users.COLUMN_NAME_USERNAME,
                UserProfile.Users.COLUMN_NAME_PASSWORD,
                UserProfile.Users.COLUMN_NAME_DATEOFBIRTH,
                UserProfile.Users.COLUMN_NAME_GENDER
        };

        // Filter results WHERE "title" = 'My Title'
        String selection = UserProfile.Users.COLUMN_NAME_USERNAME + " = ?";
        String[] selectionArgs = { un };

        /*
        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                UserProfile.Users.COLUMN_NAME_USERNAME + " DESC";
        */

        Cursor cursor = db.query(
                UserProfile.Users.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        List details = new ArrayList<>();

        while(cursor.moveToNext()) {
            long itemId = cursor.getLong(cursor.getColumnIndexOrThrow(UserProfile.Users._ID));
            details.add(itemId);

            String username = cursor.getString(cursor.getColumnIndexOrThrow(UserProfile.Users.COLUMN_NAME_USERNAME));
            details.add(username);

            String password = cursor.getString(cursor.getColumnIndexOrThrow(UserProfile.Users.COLUMN_NAME_PASSWORD));
            details.add(password);

            String dob = cursor.getString(cursor.getColumnIndexOrThrow(UserProfile.Users.COLUMN_NAME_DATEOFBIRTH));
            details.add(dob);

            String gender = cursor.getString(cursor.getColumnIndexOrThrow(UserProfile.Users.COLUMN_NAME_GENDER));
            details.add(gender);
        }

        cursor.close();

        return details;
    }

    public int deleteInfo(String un) {
        // Gets the data repository in write mode
        SQLiteDatabase db = getWritableDatabase();

        // Define 'where' part of query.
        String selection = UserProfile.Users.COLUMN_NAME_USERNAME + " = ?";

        // Specify arguments in placeholder order.
        String[] selectionArgs = { un };

        // Issue SQL statement.
        int deletedRows = db.delete(UserProfile.Users.TABLE_NAME, selection, selectionArgs);

        return deletedRows;
    }

    public int updateInfo(String un, String pw, String dob, String gender) {
        // Gets the data repository in write mode
        SQLiteDatabase db = getWritableDatabase();

        // New value for one column

        //String title = "MyNewTitle";
        ContentValues values = new ContentValues();

        //values.put(UserProfile.Users.COLUMN_NAME_USERNAME, un);
        values.put(UserProfile.Users.COLUMN_NAME_PASSWORD, pw);
        values.put(UserProfile.Users.COLUMN_NAME_DATEOFBIRTH, dob);
        values.put(UserProfile.Users.COLUMN_NAME_GENDER, gender);

        // Which row to update, based on the title
        String selection = UserProfile.Users.COLUMN_NAME_USERNAME + " = ?";
        String[] selectionArgs = { un };

        int count = db.update(
                UserProfile.Users.TABLE_NAME,
                values,
                selection,
                selectionArgs);

        return count;
    }

    public boolean login(String un, String pw) {
        Cursor cursor = readAllInfo();

        List<String> usernames = new ArrayList<>();
        List<String> passwords = new ArrayList<>();

        while(cursor.moveToNext()) {
            String u = cursor.getString(cursor.getColumnIndexOrThrow(UserProfile.Users.COLUMN_NAME_USERNAME));
            usernames.add(u);

            String p = cursor.getString(cursor.getColumnIndexOrThrow(UserProfile.Users.COLUMN_NAME_PASSWORD));
            passwords.add(p);
        }

        cursor.close();

        for (int i = 0; i < usernames.size(); i++) {
            if (usernames.get(i).equals(un) && passwords.get(i).equals(pw)) {
                return true;
            }
        }

        return false;
    }
}
