package edu.umuc.cmsc495.shoppinglist;

import android.support.annotation.NonNull;

import java.util.*;
/**
 * Created by James on 6/7/2016.
 * Last Edited by James on 6/7/2016.
 */
public class ShoppingList {

    //Class Variables
    String name;
    List<Ingredient> Ingredients;

    /**
     * Constructor for the shopping list. Fills the name and list objects.
     * @param name
     */
    ShoppingList(String name){
        this.name = name;
        Ingredients = new ArrayList<Ingredient>();
    }

    /**
     * Attempts to add an ingredient object to the list.
     * @param newIngredient
     */
    protected void addIngredient(Ingredient newIngredient){
        Ingredients.add(newIngredient);
        Save(); //Not implemented yet
    }

    /**
     * Attempts to remove an ingredient object if it exists in the list
     * @param newIngredient
     */
    protected void removeIngredient(Ingredient newIngredient){
        Ingredients.remove(newIngredient);
        Save(); //Not implemented yet
    }

    /**
     * Attempts to edit the shopping list's name.
     * Returns false if the name already exists.
     * @param name
     */
    protected boolean editName(String name){
        if(doesShoppingListExistInStorage(name)){
            this.name = name;
            Save(); //Not implemented yet
            return true;
        }else{return false;}
    }



    //Valid checks

    /**
     * Method to test if the name of the shopping list is already present in storage
     * @param listName
     * @return boolean
     */
    protected boolean doesShoppingListExistInStorage(String listName){
        boolean checkFlag = false;

        //TODO: Add call to DataLayer to look for shoppinglist name

        return checkFlag;
    }




    //File Operations

    /**
     * Attempts to save the shopping list through the data layer.
     * @return boolean
     */
    protected boolean Save(){
        boolean checkFlag = false;

        //TODO: Implement call to DataLayer to write file.

        return checkFlag;
    }

    /**
     *Attempts to delete the shopping list through the data layer.
     * @return boolean
     */
    protected boolean Delete(){
        boolean checkFlag = false;

        //TODO: Implement call to DataLayer to erase file.

        return checkFlag;
    }
}
