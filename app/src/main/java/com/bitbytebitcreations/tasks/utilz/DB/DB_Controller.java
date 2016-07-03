package com.bitbytebitcreations.tasks.utilz.DB;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.bitbytebitcreations.tasks.utilz.Task_Object;

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
    public void addTask(Task_Object values){
        DB.addRowTasks(values);
    }
    public long addTitle(String title){
        return DB.addRowTitles(title);
    }
    public void updateTask(long rowID, Task_Object values){
        DB.updateRow(rowID, values);
    }
    public void updateTitle(long rowID, String title){
        DB.updateTitles(rowID, title);
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

    public ArrayList<Task_Object> getAllTasks(long list_id){
        Cursor cursor = DB.getTasksForListId(list_id);
        ArrayList<Task_Object> db_List = new ArrayList<>();
        if (cursor.moveToFirst()){
//            Log.i("TEST", " CONTROLLER LIST BEING PULLED: " + cursor.getString(DB.KEY_COL_LIST_ID) + " COMPARE TO: " + list_id);
            do {
                Task_Object task = new Task_Object();
                task.setRowID(cursor.getLong(DB.KEY_COL_ID));
                task.setListID(cursor.getLong(DB.KEY_COL_LIST_ID));
                task.setDobDate( cursor.getString(DB.KEY_COL_DOB));
                task.setDueDate(cursor.getString(DB.KEY_COL_DUE));
                task.setPriority(cursor.getInt(DB.KEY_COL_PRIORITY));
                task.setTask(cursor.getString(DB.KEY_COL_TASK));
                db_List.add(task);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return db_List;
    }

    /*
    DB DELETE
     */
    public void deleteTask(long rowID){
        DB.deleteRow(rowID, false); //FALSE FOR TITLES
    }
    public void deleteTitle(long rowID){
        //DELETE FROM TITLE DB
        DB.deleteRow(rowID, true); //TRUE FOR TITLES
        ArrayList<Task_Object> masterList = getAllTasks(rowID);
        for (int i = 0; masterList.size() > i; i++){
            long currID = masterList.get(i).rowID;
            //DELETE FROM TASK DB
            DB.deleteRow(currID, false);
        }
    }
    //!!!!!MASTER RESET OF USER DATA!!!!!!
    public void deleteAllTasks(){
        DB.deleteAllRows();
    }



}
