package edu.umuc.cmsc495.shoppinglist.Objects;

import java.io.*;

/**
 * Created by James on 6/7/2016.
 * Last Modified by james on 6/7/2016.
 */
public class DataLayer{

    //Class Variables
    String RECIPE_FILE_PREFIX = "r_";
    String SHOPPINGLIST_FILE_PREFIX = "sl_";
    char[] INVALID_FILE_NAME_CHARS = {'?',':','\\','\"','\'','*','|','/','<','>',';'};

    /**
    protected getXMLReader(){

    }**/

    protected boolean addRecipe(Recipe newRecipe){

        return  true;
    }

    protected boolean addShoppingList(ShoppingList shoppingList){

        return true;
    }

    protected boolean deleteRecipe(Recipe targetRecipe){

        return true;
    }

    protected boolean deleteShoppingList(ShoppingList targetShoppingList){

        return true;
    }

    protected FileList getRecipeList(){
        return null;
    }

    protected FileList getShoppingLists(){
        return null;
    }

    protected Recipe getRecipe(){

        return null;
    }

    protected ShoppingList getShoppingList(){
        return null;
    }
}
