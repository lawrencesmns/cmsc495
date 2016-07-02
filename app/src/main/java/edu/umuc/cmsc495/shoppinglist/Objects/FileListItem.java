package edu.umuc.cmsc495.shoppinglist.Objects;

import java.util.*;
/**
 * Created by James on 6/6/2016.
 *
 */
public class FileListItem{

    private String name;
    private String lastModifiedOn;
    private String fileName;
    //region Get Methods
    public String getName(){return name;}
    public String getFileName(){return fileName;}
    //public Date getCreatedDate(){return createdOn;}
    public String getModifiedOn(){return lastModifiedOn;}
    //endregion

    //Constructor
    FileListItem(String name, String lastModifiedOn){
        //Global variables
        this.fileName = name;
        this.name = name.substring(0,name.length()-4);
        this.name = name.substring(name.indexOf("_") + 1);
        this.lastModifiedOn = lastModifiedOn;
        //Set created and last modified date
        //  this.createdOn = new Date();
        //  updateModifiedOn();
    }

    //Modifies the date to current
  //  public void updateModifiedOn(){
   //     lastModifiedOn = new Date();
   // }

    //DEBUG::: Remove or Comment out if not needed
    //Reverts modified date to previous date in event of rollback.
  //  public void revertModifiedDate(){
  //      if(prevModifiedDate != null) {
   //         lastModifiedOn = prevModifiedDate;
  //      }
  //  }





}
