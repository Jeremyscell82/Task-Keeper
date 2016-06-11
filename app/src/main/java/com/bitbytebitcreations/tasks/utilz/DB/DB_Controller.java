package com.bitbytebitcreations.tasks.utilz.DB;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by JeremysMac on 6/8/16.
 */
public class DB_Controller extends AppCompatActivity {

    private static final String TAG = "DB_CONTROLLER";
    public DB_Adapter DB;


    /*
    DB CONNECTION
     */
    public void openDB(Context context){
        DB = new DB_Adapter(context);
        DB.open();
    }

    public void closeDB(){
        DB.close();
    }

    /*
    DB WRITE
     */
    public void addTask(String[] values){
        DB.addRowTasks(values);
    }
    public void addTitle(String title){
        DB.addRowTitles(title);
    }

//    public void initialSettingsSetup(String[] settings){
//        //CREATE SETTINGS TABLE
//        DB.createSetting(settings);
//    }
    public void updateTask(long rowID, String[] values){
        DB.updateRow(rowID, values);
    }

    /*
    DB READ
     */
    public ArrayList<String[]> getAllTitles(){
        Log.i(TAG, "GETTING TITLES");
        Cursor cursor = DB.getAllTitleRows();
        ArrayList<String[]> db_List = new ArrayList<>();
        if (cursor.moveToFirst()){
            do {
                String[] task = new String[] {
                        String.valueOf(cursor.getInt(DB.KEY_COL_ID)),
                        cursor.getString(DB.KEY_COL_TITLE)
                };
                db_List.add(task);
            }while (cursor.moveToNext());
        }
        //TODO ORGANIZE LIST BASED ON DUE DATE
//        Collections.reverse(db_List);
        cursor.close();
        return db_List;
    }

    public ArrayList<String[]> getAllTasks(String list_name){
        Cursor cursor = DB.getAllTaskRows();
        ArrayList<String[]> db_List = new ArrayList<>();
        if (cursor.moveToFirst()){
            Log.i("TEST", " CONTROLLER LIST BEING PULLED: " + cursor.getString(DB.KEY_COL_LIST) + " COMPARE TO: " + list_name);
                do {
                    if (cursor.getString(DB.KEY_COL_LIST).equalsIgnoreCase(list_name)){
                        String[] task = new String[]{
                                String.valueOf(cursor.getInt(DB.KEY_COL_ID)),
                                cursor.getString(DB.KEY_COL_LIST),
                                cursor.getString(DB.KEY_COL_DOB),
                                cursor.getString(DB.KEY_COL_DUE),
                                cursor.getString(DB.KEY_COL_PRIORITY),
                                cursor.getString(DB.KEY_COL_TASK)
                        };
                        db_List.add(task);
                    }
                }while (cursor.moveToNext());
        }
        //TODO ORGANIZE LIST BASED ON DUE DATE
//        Collections.reverse(db_List);
        cursor.close();
        return db_List;
    }

    /*
    DB DELETE
     */
    public void deleteTask(long rowID){
        DB.deleteRow(rowID);
    }
    //!!!!!MASTER RESET OF USER DATA!!!!!!
    public void deleteAllTasks(){
        DB.deleteAllRows();
    }

    /*
    PREP DATA
     */


}
