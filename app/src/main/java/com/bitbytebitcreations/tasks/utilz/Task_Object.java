package com.bitbytebitcreations.tasks.utilz;

import java.io.Serializable;

/**
 * Created by JeremysMac on 6/21/16.
 */
public class Task_Object implements Serializable{

    public long listID;
    public String listName; //TEMP
    public String dobDate;
    public int priority;
    public String dueDate;
    public String body;
    public long rowID;

    public void setRowID(long id){
        this.rowID = id;
    };

    public void setListID(long id){
        this.listID = id;
    }

    //temp
    public void setListName(String name){
        this.listName = name;
    }

    public void setDobDate(String dob){
        this.dobDate = dob;
    }

    public void setPriority(int priority){
        this.priority = priority;
    }

    public void setDueDate(String due){
        this.dueDate = due;
    }

    public void setTask(String task){
        this.body = task;
    }


}
