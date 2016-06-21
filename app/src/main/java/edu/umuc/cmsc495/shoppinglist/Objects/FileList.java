package edu.umuc.cmsc495.shoppinglist.Objects;

import java.io.File;
import java.util.*;

import android.content.Context;
import android.content.res.AssetManager;
/**
 * Created by Alex on 6/6/16.
 * FileList is the backend's interface to the GUI's list of recipes and shopping list
 */



public class FileList {

    Context context;

    //Global Variables
    public static final List<FileListItem> recipes = new ArrayList<>();
    public static final List<FileListItem> shoppingLists = new ArrayList<>();

    public FileList(Context context){
        this.context = context;
        List<FileListItem> files = new ArrayList<>();
        fillRecipesAndShoppingLists();
    }

    private void fillRecipesAndShoppingLists(){
        File[] appFiles = context.getFilesDir().listFiles();
        Date d;
        String name;
        String shoppingListPrefix = DataLayer.SHOPPING_LIST_FILE_PREFIX;
        String recipePrefix = DataLayer.RECIPE_FILE_PREFIX;
        for(File f:appFiles){
            d = new Date(f.lastModified());
            if(f.getName().startsWith(shoppingListPrefix)){
                name = f.getName().substring(shoppingListPrefix.length());
                shoppingLists.add(new FileListItem(name, UiUtils.formatDate(d)));
            } else if(f.getName().startsWith(recipePrefix)){
                name = f.getName().substring(recipePrefix.length());
                recipes.add(new FileListItem(name, UiUtils.formatDate(d)));
            }
        }
    }
}
