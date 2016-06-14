package edu.umuc.cmsc495.shoppinglist.Objects;

import java.util.*;
/**
 * Created by James on 6/6/2016.
 *
 */
public class FileListItem{

    private String name;
    private Date createdOn, lastModifiedOn;
    private Date prevModifiedDate;

    //Constructor
    FileListItem(String name, Date createdOn, Date lastModifiedOn){
        //Global variables
        this.name = name;
        this.createdOn = createdOn;
        this.lastModifiedOn = lastModifiedOn;

        //Set created and last modified date
        this.createdOn = new Date();
        updateModifiedOn();

    }

    //Modifies the date to current
    protected void updateModifiedOn(){
        lastModifiedOn = new Date();
    }

    //DEBUG::: Remove or Comment out if not needed
    //Reverts modified date to previous date in event of rollback.
    protected void revertModifiedDate(){
        if(prevModifiedDate != null) {
            lastModifiedOn = prevModifiedDate;
        }
    }

    //Get Methods
    protected String getName(){
        return name;
    }

    protected Date getCreatedDate(){
        return createdOn;
    }

    protected Date getModifiedOn(){
        return lastModifiedOn;
    }



}
