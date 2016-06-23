package com.bitbytebitcreations.tasks.utilz.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.bitbytebitcreations.tasks.utilz.Task_Object;


/**
 * Created by JeremysMac on 6/8/16.
 */
public class DB_Adapter extends AppCompatActivity {

    //TAG
    private static final String TAG = "DB_ADAPTER";

    //DATABASE NAMES
    public static final String DATABASE_NAME = "TASK_DB"; //DATABASE NAME
    public static final String TABLE_TITLES
            = "settings_TABLE"; //DEFAULT TABLE FOR SETTINGS
    public static final String TABLE_TASKS
            = "user_TABLE";
    public static final int DATABASE_VERSION = 1;

    //UNIVERSAL
    public static final String KEY_ID = "id";
    public static final int KEY_COL_ID = 0;


    //USER TITLE COLUMN NAMES
    public static final String KEY_TITLE = "title";
    //USER TITLE KEY LIST
    public static final String[] TITLES_KEYS = new String[] {KEY_ID, KEY_TITLE};

    //USER COLUMN NAMES
    public static final String KEY_LIST_ID = "list";
    public static final String KEY_DOB = "dob";
    public static final String KEY_DUE = "due";
    public static final String KEY_PRIORITY = "priority";
    public static final String KEY_TASK = "task";
    //USER KEY LIST
    public static final String[] USER_KEYS = new String[] {KEY_ID, KEY_LIST_ID, KEY_DOB, KEY_DUE, KEY_PRIORITY, KEY_TASK};

    //SETTINGS COLUMN NUMBERS
    public static final int KEY_COL_TITLE = 1;


    //USER COLUMN NUMBERS - USER
    public static final int KEY_COL_LIST_ID = 1;
    public static final int KEY_COL_DOB = 2;
    public static final int KEY_COL_DUE = 3;
    public static final int KEY_COL_PRIORITY = 4;
    public static final int KEY_COL_TASK = 5;



    //CREATE DB STRINGs
    private static final String CREATE_TITLE_TABLE = "CREATE TABLE " + TABLE_TITLES
            + " (" +
            KEY_ID + " integer primary key autoincrement, " +
            KEY_TITLE + " TEXT" + ");";

    private static final String CREATE_TASK_TABLE = "CREATE TABLE " + TABLE_TASKS
            + " (" +
            KEY_ID + " integer primary key autoincrement, " +
            KEY_LIST_ID + " TEXT NOT NULL, " +
            KEY_DOB + " TEXT NOT NULL, " +
            KEY_DUE + " TEXT NOT NULL, " +
            KEY_PRIORITY + " TEXT NOT NULL, " +
            KEY_TASK + " TEXT" + ");";

    //SQLIGHT HELPER
    private DatabaseHelper DB_Helper;
    private SQLiteDatabase DB;

    /*
    DB CALLS
     */
    public DB_Adapter(Context context){
        DB_Helper = new DatabaseHelper(context);
        Log.i(TAG, "DB_ADAPTER HAS CALLED");
    }
    public DB_Adapter open(){
        DB = DB_Helper.getWritableDatabase();
        return this;
    }
    public void close(){
        DB_Helper.close();
    }

    /*
    INSERT ROW
     */
    public void addRowTasks(Task_Object tasks){ //INSERTS A ROW
        ContentValues initialValues = new ContentValues();
        //THIS IS THE USERS DATABASE
        initialValues = userValues(initialValues, tasks);
        DB.insert(TABLE_TASKS
                , null, initialValues);
    }
    public void addRowTitles(String titles){
        ContentValues initialValues = new ContentValues();
        //THIS IS THE USERS DATABASE
        initialValues = titleValues(initialValues, titles);
        DB.insert(TABLE_TITLES
                , null, initialValues);
    }

    /*
    UPDATE ROW
     */
    public void updateRow(long rowID, Task_Object task){
        String where = KEY_ID + "=" + rowID;
        ContentValues initialValues = new ContentValues();
        initialValues = userValues(initialValues, task);
        DB.update(TABLE_TASKS
                , initialValues, where, null);
    }

    public void updateTitles(long rowID, String setting){
        String where = KEY_ID + "=" + rowID;
        ContentValues initialValues = new ContentValues();
        initialValues = titleValues(initialValues, setting);
        DB.update(TABLE_TITLES, initialValues, where, null);
    }

    /*
    GET ALL ROWS
     */
    public Cursor getAllTaskRows(){
        Cursor cursor = null;
        cursor = DB.query(true, TABLE_TASKS
                , USER_KEYS, null, null, null, null, null, null);
        if (cursor != null){
            cursor.moveToFirst();
        }
        return cursor;
    }
    public Cursor getAllTitleRows(){
        Cursor cursor = null;
        cursor = DB.query(true, TABLE_TITLES
                , TITLES_KEYS, null, null, null, null, null, null);
        if (cursor != null){
            cursor.moveToFirst();
        }
        return cursor;
    }

    /*
    DELETE ROW
     */
    public void deleteRow(long rowID, boolean title){
        String who = KEY_ID + "=" + rowID;
        if (title){
            DB.delete(TABLE_TITLES, who, null);
        } else {
            DB.delete(TABLE_TASKS, who, null);
        }

    }

    public void deleteAllRows(){
        //DELETE THE TASKS
        Cursor cursor = getAllTaskRows();
        long rowID = cursor.getColumnIndexOrThrow(KEY_ID);
        if (cursor.moveToFirst()){
            do {
                deleteRow(cursor.getLong((int) rowID), false); //FALSE FOR TITLE BOOLEAN
            } while (cursor.moveToNext());
        }
        //DELETE THE TITLES
        Cursor cursor1 = getAllTitleRows();
        long rowID1 = cursor1.getColumnIndexOrThrow(KEY_ID);
        if (cursor1.moveToFirst()){
            do {
                deleteRow(cursor1.getLong((int) rowID1), true); //TRUE FOR TITLE BOOLEAN
            } while (cursor1.moveToNext());
        }
    }




    /*
    SET CURRENT VALUES
     */
    public ContentValues titleValues(ContentValues initialValues, String myTitle){
        initialValues.put(KEY_TITLE, myTitle);
        return initialValues;
    }

    public ContentValues userValues(ContentValues initialValues, Task_Object task){
        initialValues.put(KEY_LIST_ID, task.listName);
        initialValues.put(KEY_DOB, task.dobDate);
        initialValues.put(KEY_DUE, task.dueDate);
        initialValues.put(KEY_PRIORITY, task.priority);
        initialValues.put(KEY_TASK, task.body);
        return initialValues;
    }




    /*
    INNER CLASS HELPER
     */
    private static class DatabaseHelper extends SQLiteOpenHelper{

        DatabaseHelper(Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TITLE_TABLE);
            db.execSQL(CREATE_TASK_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_TITLES);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);

            onCreate(db);
        }
    }
}
