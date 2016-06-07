package edu.umuc.cmsc495.shoppinglist;

import java.util.*;
/**
 * Created by James on 6/7/2016.
 * Last Edited by James on 6/7/2016.
 */
public class ShoppingList {

    //Class Variables
    String name;
    List<Ingredient> Ingredients;
    String emailBody, emailSubject;

    /**
     * Constructor for the shopping list. Fills the name and list objects.
     * @param name - Name of the list to be built.
     */
    ShoppingList(String name){
        this.name = name;
        Ingredients = new ArrayList<Ingredient>();
    }


    //Methods to add items to the list from a given recipe

    /**
     * Iterates through the given recipe's ingredients and checks if the ingredient is already present.
     * If it is, it updates the quantity on the shopping list's ingredient.
     * If it is NOT, it simply adds the ingredient to the list.
     * @param newRecipe
     * @return true
     */
    protected boolean addRecipe(Recipe newRecipe){
        List<Ingredient> recipeIngredients = newRecipe.getIngredientList();

        for(Ingredient newIngredient: recipeIngredients){
            if(isIngredientAlreadyPresent(newIngredient)){
                int indexOf = getIndexOfName(newIngredient);
                Ingredient targetIngredient = this.Ingredients.get(indexOf);
                targetIngredient = updateIngredient(targetIngredient, newIngredient);
                this.Ingredients.set(indexOf, targetIngredient);
            }else{
                addIngredient(newIngredient);
            }
        }
        return true;
    }

    /**
     * updates the quantities of oldIngredient by adding the quantities of newIngredient.
     * @param oldIngredient
     * @param newIngredient
     * @return updated Ingredient
     */
    private Ingredient updateIngredient(Ingredient oldIngredient, Ingredient newIngredient){
        oldIngredient.addCount(newIngredient.getCount());
        oldIngredient.addMeasurement(newIngredient.getMeasurement());
        return oldIngredient;
    }

    /**
     * Checks if the given ingredient is present in the list by comparing the names
     * @param ingredient
     * @return
     */
    private boolean isIngredientAlreadyPresent(Ingredient ingredient){
        for(Ingredient currIngredient : Ingredients){
            if(currIngredient.getName().compareTo(ingredient.getName()) == 0){
                return true;
            }
        }
        return false;
    }

    /**
     * Attempts to locate the index of the ingredient name provided.
     * If found, returns the index of the matching ingredient.
     * Return -1 if no match if found.
     * @param targetIngredient
     * @return integer index of ingredient
     */
    private int getIndexOfName(Ingredient targetIngredient){
        for(Ingredient ingredient: Ingredients){
            if(ingredient.getName().compareTo(targetIngredient.getName()) == 0){
                return Ingredients.indexOf(ingredient);
            }
        }
        return -1;
    }


    //List Operations

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

    //Get Methods
    protected String getEmailBody(){
        return this.emailBody;
    }
    protected String getEmailSubject(){
        return this.emailSubject;
    }
}