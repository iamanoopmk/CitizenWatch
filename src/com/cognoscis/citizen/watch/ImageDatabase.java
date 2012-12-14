package com.cognoscis.citizen.watch;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ImageDatabase {
	
	public static final String KEY_REG_NO = "regNo";
    public static final String KEY_TYPE = "vType";
    public static final String KEY_DATE = "vDate";
    public static final String KEY_TIME = "vTime";
    public static final String KEY_PLACE = "vPlace";
    public static final String KEY_NAME = "uName";
    public static final String KEY_CONTACT = "uContact";
    public static final String KEY_EMAIL = "uEmail";
    public static final String KEY_REMARKS = "uRemarks";
    public static final String KEY_PATH = "imagePath";
    public static final String KEY_ROWID = "_id";

    private static final String TAG = "NotesDbAdapter";
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;
    
    // Database creation statement
    
    private static final String DATABASE_CREATE =
            "CREATE TABLE dbImages (_id integer primary key autoincrement, "
            + "regNo text NOT NULL, vType text NOT NULL, vDate text NOT NULL, " +
            "vTime text NOT NULL, vPlace text NOT NULL, uName text NOT NULL," +
            "uContact text NOT NULL, uEmail text NOT NULL, uRemarks text NOT NULL, imagePath text NOT NULL);";
    
    private static final String DATABASE_NAME = "data";
    private static final String DATABASE_TABLE = "dbImages";
    private static final int DATABASE_VERSION = 2;

    private final Context mCtx;
    
    //Constructor - takes the context to allow the database to be opened/created
    
    public ImageDatabase(Context ctx) {
        this.mCtx = ctx;
    }
    

    public ImageDatabase open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
    	
        mDbHelper.close();
    }
    
    public long createEntry(String regNo, String vType, String vDate, String vTime, String vPlace,
    		String uName, String uContact, String uEmail, String uRemarks, String imagePath) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_REG_NO, regNo);
        initialValues.put(KEY_TYPE, vType);
        initialValues.put(KEY_DATE, vDate);
        initialValues.put(KEY_TIME, vTime);
        initialValues.put(KEY_PLACE, vPlace);
        initialValues.put(KEY_NAME, uName);
        initialValues.put(KEY_CONTACT, uContact);
        initialValues.put(KEY_EMAIL, uEmail);
        initialValues.put(KEY_REMARKS, uRemarks);
        initialValues.put(KEY_PATH, imagePath);

        return mDb.insert(DATABASE_TABLE, null, initialValues);
    }
    
    public boolean deleteEntry(long rowId) {

        return mDb.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }
    
    public Cursor fetchEntry(long rowId) throws SQLException {

        Cursor mCursor =

            mDb.query(true, DATABASE_TABLE, new String[] {KEY_ROWID,
                    KEY_REG_NO, KEY_TYPE, KEY_DATE, KEY_TIME, KEY_PLACE,
                    KEY_NAME, KEY_CONTACT, KEY_EMAIL, KEY_REMARKS, KEY_PATH}, KEY_ROWID + "=" + rowId, null,
                    null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;

    }
    
    public boolean updateEntry(long rowId, String regNo, String vType, String vDate, String vTime, String vPlace,
    		String uName, String uContact, String uEmail, String uRemarks, String imagePath) {
        ContentValues args = new ContentValues();
        args.put(KEY_REG_NO, regNo);
        args.put(KEY_TYPE, vType);
        args.put(KEY_DATE, vDate);
        args.put(KEY_TIME, vTime);
        args.put(KEY_PLACE, vPlace);
        args.put(KEY_NAME, uName);
        args.put(KEY_CONTACT, uContact);
        args.put(KEY_EMAIL, uEmail);
        args.put(KEY_REMARKS, uRemarks);
        args.put(KEY_PATH, imagePath);

        return mDb.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    }
    
    
    private static class DatabaseHelper extends SQLiteOpenHelper {
    	
    	DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
        	
        	try {
        		db.execSQL(DATABASE_CREATE);
        		
        	} catch (SQLException e){
        		e.printStackTrace();
        		
        	}            
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS dbImages");
            onCreate(db);
        }
    }
    

}


