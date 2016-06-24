package edu.umuc.cmsc495.shoppinglist.Objects;

import android.content.Context;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by martin on 6/23/2016.
 */
public class GbList implements Serializable {
    protected String name = "", createdOn = "", lastModifiedOn = "";
    protected List<Ingredient> ingredientList = new ArrayList<>();
   // private static Context context;

    //region get Methods
    protected String getName() {
        return this.name;
    }

    protected List<Ingredient> getIngredientList() {
        return this.ingredientList;
    }

    protected String getLastModifiedOn() {
        return this.lastModifiedOn;
    }

    protected String getCreatedOn() {
        return this.createdOn;
    }

    protected String getEmailSubject(String type){
        return UiUtils.getAppName() + "   " + type + ": " + this.name;
    }

    protected String clearInvalidChars(String name){
        String output = "";
        for (char c : DataLayer.INVALID_FILE_NAME_CHARS) {
            output = name.replace(c, ' ');
        }
            return output;
    }

    protected void setCreatedOn(String newCreatedOn) {
        this.createdOn = newCreatedOn;
    }

    protected void setLastModifiedOn(String newLastModifiedOn) {
        this.lastModifiedOn = newLastModifiedOn;
    }

    //Adds an ingredient to the list
    protected void addIngredient(Ingredient newIngredient) {
        if (!isExistingIngredientByName(newIngredient)) {
            if (newIngredient.getName().length() == 0) {
                throw new IllegalArgumentException("Please enter a name for the ingredient");
            }
            ingredientList.add(newIngredient);
            //  sortIngredientsAscending(); //martin note: commented this as maybe the user wants to sort the ingredients - should be an option
        } else {
            throw new IllegalArgumentException("This ingredient is already here, please modify the existing one.");
        }
    }

    protected void changeIngredient(Ingredient revisedIngredient, Ingredient originalIngredient) {
        removeIngredient(originalIngredient);
        addIngredient(revisedIngredient);
    }

    //Checks if the ingredient name is already present
    private boolean isExistingIngredientByName(Ingredient check) {
        for (Ingredient i : ingredientList) {
            if (i.getName().compareTo(check.getName()) == 0) {
                return true;    //matching ingredient name found
            }
        }
        return false;   //No matching ingredient name
    }


    //Sorts the Ingredients in the list by name in alphanumeric order
    protected void sortIngredientsAscending() {
        ArrayList<Ingredient> sortedList = new ArrayList<Ingredient>();
        sortedList.addAll(this.ingredientList);

        boolean sortFlag = false;
        while (sortFlag) {
            sortFlag = true;

            for (int i = 0; i < sortedList.size() - 1; i++) {
                Ingredient tempIng1 = sortedList.get(i);
                Ingredient tempIng2 = sortedList.get(i + 1);

                if (tempIng1.getName().compareTo(tempIng2.getName()) > 0) {
                    sortedList.set(i, tempIng1);
                    sortedList.set(i + 1, tempIng2);
                    sortFlag = false;
                }
            }
        }

        //Assigns sorted list to class var
        this.ingredientList = sortedList;
    }

    protected void removeIngredient(Ingredient ingredient){
        boolean found = false;
        for (Ingredient i: ingredientList) {
            if(i.getName().compareTo(ingredient.getName()) == 0){
                ingredientList.remove(i);
                found = true;
                break;
            }
        }
        if(found == false){
            throw new IllegalArgumentException("Ingredient " + ingredient.getName() + " doesn't exist");
        }
    }


}
