package edu.umuc.cmsc495.shoppinglist.Objects;

import android.content.Context;
import android.provider.ContactsContract;

import java.io.Serializable;
import java.util.*;
/**
 * Created by James on 6/7/2016.
 * Last Edited by James on 6/7/2016.
 */
public class ShoppingList extends GbList implements Serializable{
    //Class Variables
    private List<String> recipeIngredients = new ArrayList<>();
    private static Context context;

    public ShoppingList(Context context){
        this.createdOn = UiUtils.getDateTimeNow();
        this.context = context;
    }
    public ShoppingList(){
        this.createdOn = UiUtils.getDateTimeNow();
    }

    public static ShoppingList loadShoppingList(String name){ //probably not the cleanest
        if(context !=null){
            return DataLayer.parseList(name);
        } else{
            return new ShoppingList();
        }
    }


    //region Get Methods
    public String getEmailBodyText(){
        String output = this.name + ": " + UiUtils.emailNewLine()+ UiUtils.emailNewLine()+ UiUtils.emailNewLine();
        output += "Items:" + UiUtils.emailNewLine();
        for(Ingredient i:this.ingredientList){
            output += i.toString() + UiUtils.emailNewLine();
        }
        return output;
    }
    public String getEmailSubject(){
        return super.getEmailSubject("Shopping List");
    }
    //endregion

    //region Set Methods
    public void setName(String newListName){
        delete();
        this.name = clearInvalidChars(newListName);
        save();
    }

    //endregion

    //not yet implemented
   /* public boolean addRecipe(Recipe newRecipe){
        List<Ingredient> recipeIngredients = newRecipe.getIngredientList();

        for(Ingredient newIngredient: recipeIngredients){
            if(isIngredientAlreadyPresent(newIngredient)){
                int indexOf = getIndexOfName(newIngredient);
                Ingredient targetIngredient = this.Ingredients.get(indexOf);
                //targetIngredient = updateIngredient(targetIngredient, newIngredient);
                this.Ingredients.set(indexOf, targetIngredient);
            }else{
                addIngredient(newIngredient);
            }
        }
        return true;
    }
*/



    /* private int getIndexOfName(Ingredient targetIngredient){
        for(Ingredient ingredient: this.ingredientList){
            if(ingredient.getName().compareTo(targetIngredient.getName()) == 0){
                return this.ingredientList.indexOf(ingredient);
            }
        }
        return -1;
    }
*/

    //List Operations

    public void addIngredient(Ingredient newIngredient){
        super.addIngredient(newIngredient);
        save();
    }


    public void removeIngredient(Ingredient ingredient){
        super.removeIngredient(ingredient);
        save();

    }

    public void changeIngredient(Ingredient revisedIngredient, Ingredient originalIngredient){
        super.changeIngredient(revisedIngredient, originalIngredient);
        save();
    }









    public boolean save(){
        boolean checkValue = true;
        //TODO: check if it can eb saved, like enough free space
        if(context != null){
            DataLayer d = new DataLayer(context);
            checkValue = d.deleteShoppingList(this);

            this.lastModifiedOn = UiUtils.formatDate(new Date());


            checkValue = d.saveShoppingList(this);

        } else{checkValue = false;}
        return checkValue;
    }

    public boolean delete(){
        if(context != null){
            DataLayer d = new DataLayer(context);
            return d.deleteShoppingList(this);
        }
        else{return false;}
    }

    public boolean doesShoppingListExistInStorage(String listName){
        boolean checkFlag = false;

        //TODO: Add call to DataLayer to look for shoppinglist name

        return checkFlag;
    }
}


/**
 * Attempts to locate the index of the ingredient name provided.
 * If found, returns the index of the matching ingredient.
 * Return -1 if no match if found.
 * @param targetIngredient
 * @return integer index of ingredient
 */

//Valid checks

/**
 * Method to test if the name of the shopping list is already present in storage
 * @param listName
 * @return boolean
 */

//File Operations

/**
 * Attempts to save the shopping list through the data layer.
 * @return boolean
 */

/**
 *Attempts to delete the shopping list through the data layer.
 * @return boolean
 */

/**
 * Attempts to add an ingredient object to the list.
 * @param newIngredient
 */
/**
 * Attempts to remove an ingredient object if it exists in the list
 * @param newIngredient
 */
/**
 * Attempts to edit the shopping list's name.
 * Returns false if the name already exists.
 * @param name
 */


/**
 * Constructor for the shopping list. Fills the name and list objects.
 //* @param name - Name of the list to be built.
 */
//public ShoppingList(String name){
//    this.name = name;
//}

//Set Add Methods
//   public void addIngredient(String input){
//       List<String> inputSplit = Arrays.asList(input.split(","));
//      String name = inputSplit.get(0);
//      String measurement = inputSplit.get(1);
//      Double count = Double.parseDouble(inputSplit.get(2));
//      Ingredient tempIngredient = new Ingredient(name, measurement, count);
//      this.ingredientList.add(tempIngredient);
//  }


//Methods to add items to the list from a given recipe

/**
 * Iterates through the given recipe's ingredients and checks if the ingredient is already present.
 * If it is, it updates the quantity on the shopping list's ingredient.
 * If it is NOT, it simply adds the ingredient to the list.
 * @param newRecipe
 * @return true

/**
 * updates the quantities of oldIngredient by adding the quantities of newIngredient.
 * @param oldIngredient
 * @param newIngredient
 * @return updated Ingredient

private Ingredient updateIngredient(Ingredient oldIngredient, Ingredient newIngredient){
return oldIngredient;
}
 */

/**
 * Checks if the given ingredient is present in the list by comparing the names
 * @param ingredient
 * @return
 */
