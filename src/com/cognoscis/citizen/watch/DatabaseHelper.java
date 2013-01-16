package com.cognoscis.citizen.watch;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
	
	// All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
    
    // Database Name
    private static final String DATABASE_NAME = "imageDatabase";
    
    // table name
    private static final String DATABASE_TABLE = "images";
    
    // table column names
    private static final String KEY_ROWID = "_id";
    private static final String KEY_REG_NO = "regNo";
    private static final String KEY_TYPE = "vType";
    private static final String KEY_DATE = "vDate";
    private static final String KEY_TIME = "vTime";
    private static final String KEY_PLACE = "vPlace";
    private static final String KEY_NAME = "uName";
    private static final String KEY_CONTACT = "uContact";
    private static final String KEY_EMAIL = "uEmail";
    private static final String KEY_REMARKS = "uRemarks";
    private static final String KEY_PATH = "imagePath";
    
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    
    /*  
    private static final String DATABASE_CREATE =
            
            
            
            "CREATE_TABLE images " + "(" + KEY_ROWID 
    			+ "INTEGER PRIMARY KEY,"+ KEY_REG_NO + "TEXT," + KEY_TYPE +  "TEXT," + KEY_DATE + "TEXT," 
    			+ KEY_TIME + "TEXT," + KEY_PLACE + "TEXT," + KEY_NAME + "TEXT," + KEY_CONTACT + "TEXT," 
    			+ KEY_EMAIL + "TEXT," + KEY_REMARKS + "TEXT," + KEY_PATH + "TEXT" + ");" ;
    
    */
    
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
    	String CREATE_IMAGEDATABASE_TABLE = "CREATE TABLE images (_id integer primary key autoincrement, "
                + "regNo text NOT NULL, vType text NOT NULL, vDate text NOT NULL, " +
                "vTime text NOT NULL, vPlace text NOT NULL, uName text NOT NULL," +
                "uContact text NOT NULL, uEmail text NOT NULL, uRemarks text NOT NULL, imagePath text NOT NULL);";
    	
    	db.execSQL(CREATE_IMAGEDATABASE_TABLE);        
    }
    
    //Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    	// Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
        
        // Create tables again
        onCreate(db);
    }
    
    // Adding new record
    public void createEntry(ImageDatabase entry) {
    	SQLiteDatabase db = this.getWritableDatabase();
    	
    	ContentValues initialValues = new ContentValues();
    	
    	initialValues.put(KEY_REG_NO, entry.getRegNo());
        initialValues.put(KEY_TYPE, entry.getvType());
        initialValues.put(KEY_DATE, entry.getvDate());
        initialValues.put(KEY_TIME, entry.getvTime());
        initialValues.put(KEY_PLACE, entry.getvPlace());
        initialValues.put(KEY_NAME, entry.getuName());
        initialValues.put(KEY_CONTACT, entry.getuContact());
        initialValues.put(KEY_EMAIL, entry.getuEmail());
        initialValues.put(KEY_REMARKS, entry.getuRemarks());
        initialValues.put(KEY_PATH, entry.getImagePath());
        
        // Inserting Row
        db.insert(DATABASE_TABLE, null, initialValues);
        db.close(); // Closing database connection
    	
    }
    
    // Updating a record
    
    public int updateEntry(ImageDatabase entry) {
    	SQLiteDatabase db = this.getWritableDatabase();
    	
    	ContentValues args = new ContentValues();
    	
    	args.put(KEY_REG_NO, entry.getRegNo());
        args.put(KEY_TYPE, entry.getvType());
        args.put(KEY_DATE, entry.getvDate());
        args.put(KEY_TIME, entry.getvTime());
        args.put(KEY_PLACE, entry.getvPlace());
        args.put(KEY_NAME, entry.getuName());
        args.put(KEY_CONTACT, entry.getuContact());
        args.put(KEY_EMAIL, entry.getuEmail());
        args.put(KEY_REMARKS, entry.getuRemarks());
        args.put(KEY_PATH, entry.getImagePath());
        
        // updating row
        return db.update(DATABASE_TABLE, args, KEY_ROWID + " = ?",
                new String[] { String.valueOf(entry.getID()) });
    }
    
    //Deleting a record    
    public void deleteEntry(ImageDatabase entry) {
    	SQLiteDatabase db = this.getWritableDatabase();
    	
    	db.delete(DATABASE_TABLE, KEY_ROWID + " = ?",
                new String[] { String.valueOf(entry.getID()) });
    	

        db.close();
    }
  
}