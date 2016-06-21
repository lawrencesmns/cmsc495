package edu.umuc.cmsc495.shoppinglist.Objects;

import java.io.Serializable;
import java.util.*;
/**
 * Created by James on 6/7/2016.
 * Last Edited by James on 6/7/2016.
 */
public class ShoppingList implements Serializable{

    //Class Variables
    private String name, createdOn, lastModifiedOn ;
    private List<Ingredient> ingredientList = new ArrayList<Ingredient>();
    private List<String> recipeIngredients = new ArrayList<>();


    public ShoppingList(){
        this.createdOn = UiUtils.getDateTimeNow();
    }

    //region Get Methods
    protected List<Ingredient> getIngredientList(){return this.ingredientList;}
    protected String getName(){ return this.name; }
    protected String getLastModifiedOn(){return this.lastModifiedOn;}
    protected String getCreatedOn(){return this.createdOn;}
    protected String getEmailSubject(){
        return UiUtils.getAppName() + "   Shopping List: " + this.name;
    }

    protected String getEmailBody(){
        String output = this.name + ": " + UiUtils.emailNewLine()+ UiUtils.emailNewLine()+ UiUtils.emailNewLine();
        output += "Items:" + UiUtils.emailNewLine();
        for(Ingredient i:this.ingredientList){
            output += i.toString() + UiUtils.emailNewLine();
        }
        return output;
    }
    //endregion

    //region Set Methods
    protected void addShoppingListItem(Ingredient ingredient){
        ingredientList.add(ingredient);
    }
    protected void setName(String newListName){
        delete();
        for(char c:DataLayer.INVALID_FILE_NAME_CHARS){
            newListName = newListName.replace(c, ' ');
        }
        this.name = newListName;
    }

    //endregion

  /* not yet implemented
    protected boolean addRecipe(Recipe newRecipe){
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

    private boolean isIngredientAlreadyPresent(Ingredient ingredient){
        for(Ingredient currIngredient : this.ingredientList){
            if(currIngredient.getName().compareTo(ingredient.getName()) == 0){
                return true;
            }
        }
        return false;
    }

    private int getIndexOfName(Ingredient targetIngredient){
        for(Ingredient ingredient: this.ingredientList){
            if(ingredient.getName().compareTo(targetIngredient.getName()) == 0){
                return this.ingredientList.indexOf(ingredient);
            }
        }
        return -1;
    }


    //List Operations


    protected void addIngredient(Ingredient newIngredient){
        this.ingredientList.add(newIngredient);
        save();
    }


    protected void removeIngredient(Ingredient ingredient){
        for (Ingredient i: ingredientList) {
            if(i.getName().compareTo(ingredient.getName()) == 0){
                ingredientList.remove(i);
                //System.out.println("Ingredient removed from recipe!");
                save();
                break;
            }
        }
    }

    protected void changeIngredient(Ingredient revisedIngredient, Ingredient originalIngredient){
        removeIngredient(originalIngredient);
        addIngredient(revisedIngredient);
        save();
    }




    protected boolean doesShoppingListExistInStorage(String listName){
        boolean checkFlag = false;

        //TODO: Add call to DataLayer to look for shoppinglist name

        return checkFlag;
    }




    protected boolean save(){
        boolean checkValue = true;
        //TODO: check if it can eb saved, like enough free space

        checkValue = DataLayer.deleteShoppingList(this);

        this.lastModifiedOn = UiUtils.formatDate(new Date());

        if(checkValue){
            checkValue = DataLayer.saveShoppingList(this);
        }

        return checkValue;
    }

    protected boolean delete(){
        return DataLayer.deleteShoppingList(this);
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
//   protected void addIngredient(String input){
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
