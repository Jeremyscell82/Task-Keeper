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
    public void addTitle(String title){
        DB.addRowTitles(title);
    }
    public void updateTask(long rowID, Task_Object values){
        DB.updateRow(rowID, values);
    }
    public void updateTitle(long rowID, String title, String oldTitle){
        DB.updateTitles(rowID, title);
        //UPDATE TASK DB 'LIST'
//        updateTaskTitles(title, oldTitle);
    }
    //TODO REPLACE BY USING ROW ID FOR LIST NAME
//    public void updateTaskTitles(String title, String oldTitle){
//        ArrayList<String[]> masterList = getAllTasks(oldTitle);
//        //FOR LOOP TO CHANGE EACH ENTRY
//        DB.open();
//        for (int i =0; masterList.size() > i; i++){
//            String[] task = masterList.get(i);
//            String[] newTask = {
//                    title, //NEW TITLE
//                    task[2],
//                    task[3],
//                    task[4],
//                    task[5]
//            };
//            long rowID = Long.parseLong(task[0]);
//            DB.updateRow(rowID, newTask);
//        }
//        DB.close();
//    }
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

    public ArrayList<Task_Object> getAllTasks(String list_name){
        Cursor cursor = DB.getAllTaskRows();
        ArrayList<Task_Object> db_List = new ArrayList<>();
        if (cursor.moveToFirst()){
            Log.i("TEST", " CONTROLLER LIST BEING PULLED: " + cursor.getString(DB.KEY_COL_LIST_ID) + " COMPARE TO: " + list_name);
                do {
                    if (cursor.getString(DB.KEY_COL_LIST_ID).equalsIgnoreCase(list_name)){
                        Task_Object task = new Task_Object();
                        task.setRowID(cursor.getLong(DB.KEY_COL_ID));
                        task.setListName(cursor.getString(DB.KEY_COL_LIST_ID));
                        task.setDobDate( cursor.getString(DB.KEY_COL_DOB));
                        task.setDueDate(cursor.getString(DB.KEY_COL_DUE));
                        task.setPriority(cursor.getInt(DB.KEY_COL_PRIORITY));
                        task.setTask(cursor.getString(DB.KEY_COL_TASK));


//                        String[] task2 = new String[]{
//                                String.valueOf(cursor.getInt(DB.KEY_COL_ID)),
//                                cursor.getString(DB.KEY_COL_LIST_ID),
//                                cursor.getString(DB.KEY_COL_DOB),
//                                cursor.getString(DB.KEY_COL_DUE),
//                                cursor.getString(DB.KEY_COL_PRIORITY),
//                                cursor.getString(DB.KEY_COL_TASK)
//                        };

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
        DB.deleteRow(rowID, false); //FALSE FOR TITLES
    }
    public void deleteTitle(long rowID, String title){
        //DELETE FROM TITLE DB
        DB.deleteRow(rowID, true); //TRUE FOR TITLES
        ArrayList<Task_Object> masterList = getAllTasks(title);
        for (int i = 0; masterList.size() > i; i++){
            long currID = masterList.get(i).rowID;
            //DELETE FROM TASK DB
            DB.deleteRow(currID, false);
//            Log.i(TAG, "COUNT EM....");
        }
    }
    //!!!!!MASTER RESET OF USER DATA!!!!!!
    public void deleteAllTasks(){
        DB.deleteAllRows();
    }



}
