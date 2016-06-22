package edu.umuc.cmsc495.shoppinglist.Objects;

import java.util.*;
/**
 * Created by James on 6/6/2016.
 *
 */
public class FileListItem{

    private String name;
    private String lastModifiedOn;

    //region Get Methods
    public String getName(){return name;}
    //protected Date getCreatedDate(){return createdOn;}
    public String getModifiedOn(){return lastModifiedOn;}
    //endregion

    //Constructor
    FileListItem(String name, String lastModifiedOn){
        //Global variables
        this.name = name;
        this.lastModifiedOn = lastModifiedOn;
        //Set created and last modified date
        //  this.createdOn = new Date();
        //  updateModifiedOn();
    }

    //Modifies the date to current
  //  protected void updateModifiedOn(){
   //     lastModifiedOn = new Date();
   // }

    //DEBUG::: Remove or Comment out if not needed
    //Reverts modified date to previous date in event of rollback.
  //  protected void revertModifiedDate(){
  //      if(prevModifiedDate != null) {
   //         lastModifiedOn = prevModifiedDate;
  //      }
  //  }





}
