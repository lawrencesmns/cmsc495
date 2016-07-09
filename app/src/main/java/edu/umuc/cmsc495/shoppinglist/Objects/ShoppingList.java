package edu.umuc.cmsc495.shoppinglist.Objects;

import android.content.Context;
import android.provider.ContactsContract;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.*;

import edu.umuc.cmsc495.shoppinglist.UI.RecipesList;

/**
 * Created by James on 6/7/2016.
 * Last Edited by James on 6/7/2016.
 */
public class ShoppingList extends GbList implements Serializable{
    //Class Variables
    private List<Recipe> recipesAdded = new ArrayList<>();
    private static Context context;


    public ShoppingList(Context context){
        this.createdOn = UiUtils.getDateTimeNow();
        this.context = context;
        this.isPersisting = true;
    }
    protected ShoppingList(){
        this.createdOn = UiUtils.getDateTimeNow();
        this.isPersisting = false;
    }
    protected List<Recipe> getRecipesAdded(){
        return this.recipesAdded;
    }

    public ShoppingList loadShoppingList(String name){ //probably not the cleanest
        try {
            if (context != null) {
                DataLayer d = new DataLayer(context);
                ShoppingList sl = new ShoppingList();
                sl = d.parseList(name);
                this.name = sl.getName();
                this.createdOn = sl.getCreatedOn();
                this.lastModifiedOn = sl.getLastModifiedOn();
                this.recipesAdded = sl.getRecipesAdded();
                this.ingredientList = sl.getIngredientList();
                //for(Ingredient i:this.ingredientList){
                //    this.ingredientListFromStorage.add(i);
                //}
                for(Recipe r:this.recipesAdded){
                    for(Ingredient i:r.ingredientList){
                        i.setIsFromRecipe(true);
                        i.setRecipeName(r.getName());
                    }
                }

                addIngredientsFromRecipes();
            } else {
                throw new IllegalStateException("The context must be passed in the shopping list constructor");
            }
        }catch(Exception e){
            throw new IllegalArgumentException("The shopping list name " + name + " could not be loaded or parsed");
        }
        return this;
    }
    public ShoppingList createNewList(){
        DataLayer d = new DataLayer(this.context);
        this.name = d.createEmptyShoppingList();
        save();
        return this;
    }



    //region Get Methods
    public String getEmailBodyText(){
        String output = this.name + ": " + UiUtils.emailNewLine()+ UiUtils.emailNewLine()+ UiUtils.emailNewLine();
        output += "Items:" + UiUtils.emailNewLine();
        output += getIngredientsForEmailBody();
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

   public void addRecipe(Recipe newRecipe){
       boolean alreadyAdded = false;
       Iterator<Recipe> r = recipesAdded.iterator();
       while(r.hasNext()){
           Recipe rec = r.next();
           if(rec.getName().equals(newRecipe.getName())){
               r.remove();
           }
       }

        recipesAdded.add(newRecipe);
        save();
   }





    public void removeRecipe(Recipe newRecipe){
        Iterator<Recipe> r = recipesAdded.iterator();
        while(r.hasNext()){
            Recipe rec = r.next();
            if(rec.getName().equals(newRecipe.getName())){
                r.remove();
            }
        }

        save();
    }

    private void addIngredientsFromRecipes(){
        String existingIngredientName = "";

        for(Recipe recipe:this.recipesAdded){
            if(!recipe.isAddedToShoppingList()){
                recipe.setIsAddedToShoppingList(true);
            for(Ingredient ingRecipe:recipe.ingredientList){
                String  nonPluralRecipeIngredientName = ingRecipe.getName();
                ingRecipe.setIsFromRecipe(true);
                if(nonPluralRecipeIngredientName.endsWith("s")){
                    nonPluralRecipeIngredientName = nonPluralRecipeIngredientName.substring(0,nonPluralRecipeIngredientName.length()-1);
                }
                boolean found = false;
                for(Ingredient ingHere:this.ingredientList){
                    String nonPluralListIngredientName = ingHere.getName();
                    if (nonPluralListIngredientName.endsWith("s")) {
                        nonPluralListIngredientName = nonPluralListIngredientName.substring(0, nonPluralListIngredientName.length() - 1);
                    }
                    if(nonPluralListIngredientName.equals(nonPluralRecipeIngredientName) && found == false){
                        //do arithmetic here
                        found = true;
                        String firstEntry = "";
                        if(!ingHere.getUseDisplayName()){
                            firstEntry = ingHere.toString();
                            ingHere.setUseDisplayName(true);
                        }else{
                            firstEntry = ingHere.getDisplayName();
                        }
                        ingHere.setDisplayName(firstEntry + "  " + buildNameString(ingRecipe) + " for " + recipe.getName());
                    }
                }
                if(!found){
                    ingRecipe.setDisplayName(buildNameString(ingRecipe)+"  " + ingRecipe.getName() + " for " + recipe.getName());
                    //Ingredient j= new Ingredient(ingRecipe.getName(), ingRecipe.getMeasurement(), ingRecipe.getCountFullString(), ingRecipe.getCountPartialString(),false);
                    //clear
                    //ingRecipe.setCountFull("");
                    //ingRecipe.setCountPartial("");
                    //ingRecipe.setMeasurement("");
                    ingRecipe.setUseDisplayName(true);
                    this.ingredientList.add(ingRecipe);
                    //reset
                    //ingRecipe.setCountFull(j.getCountFullString());
                    //ingRecipe.setCountPartial(j.getCountPartialString());
                    //ingRecipe.setMeasurement(j.getMeasurement());
                }

                }
            }
        }

    }

    private String buildNameString(Ingredient i){
        String result = "";
        if(!i.getCountFullString().equals("")){
            result = " " + i.getCountFullString();
        }
        if(!i.getCountPartialString().equals("")){
            result += " " + i.getCountPartialString();
        }
        if(!i.getMeasurement().equals("")){
            result += " " + i.getMeasurement();
        }
        return "+" + result;
    }

    public void addIngredient(Ingredient newIngredient){
        doAddIngredientWork(newIngredient);
        save();
    }

    public void doAddIngredientWork(Ingredient newIngredient){
        this.ingredientListFromStorage.add(newIngredient);
        super.addIngredient(newIngredient);

    }

    public void removeIngredient(Ingredient ingredient){
    doRemoveWork(ingredient);
    save();
    }

    public void doRemoveWork(Ingredient ingredient){
        removeIngredientFromStorageList(ingredient);
        super.removeIngredient(ingredient);

        if(ingredient.getIsFromRecipe()){
            for(Recipe r:this.recipesAdded){
                boolean found = false;
                Iterator<Ingredient> i = r.ingredientList.iterator();
                while(i.hasNext()){
                    Ingredient ing = i.next();
                    if(ingredient.getName().equals(ing.getName())){
                        i.remove();
                    }
                }
            }
        }

    }

    public void changeIngredientInRecipe(Ingredient revisedIngredient, Ingredient originalIngredient){
        for(Recipe r:this.recipesAdded){
            if(r.getName().equals(originalIngredient.getRecipeName())){
                Iterator<Ingredient> i = r.ingredientList.iterator();
                while(i.hasNext()){
                    Ingredient ing = i.next();
                    if(originalIngredient.getName().equals(ing.getName())){
                        i.remove();
                    }
                }
                r.addIngredient(revisedIngredient);
            }
        }
    }

    public void removeIngredientFromRecipe(Ingredient ingredient){
        for(Recipe r:this.recipesAdded){
            if(r.getName().equals(ingredient.getRecipeName())){
                Iterator<Ingredient> i = r.ingredientList.iterator();
                while(i.hasNext()){
                    Ingredient ing = i.next();
                    if(ingredient.getName().equals(ing.getName())){
                        i.remove();
                    }
                }
            }
        }
    }


    public void changeIngredient(Ingredient revisedIngredient, Ingredient originalIngredient){
         if(originalIngredient.getIsFromRecipe()){
             changeIngredientInRecipe(revisedIngredient, originalIngredient);
         }else{
             super.removeIngredient(originalIngredient);
             doRemoveWork(originalIngredient);
             removeIngredientFromRecipe(originalIngredient);
             // addIngredient(revisedIngredient);
            // this.ingredientListFromStorage.add(revisedIngredient);
            // super.changeIngredient(revisedIngredient, originalIngredient);
             doAddIngredientWork(revisedIngredient);
         }
        save();
    }

    private void removeIngredientFromStorageList(Ingredient ingStor){
        Iterator<Ingredient> i = ingredientListFromStorage.iterator();
        while(i.hasNext()){
            Ingredient ing = i.next();
            if(ing.getName().equals(ingStor.getName())){
                i.remove();
            }
        }
    }
    private boolean save(){
        boolean checkValue = true;
        //TODO: check if it can eb saved, like enough free space
        if(context != null && this.isPersisting){
            DataLayer d = new DataLayer(context);
            checkValue = d.deleteShoppingList(this);

            this.lastModifiedOn = UiUtils.formatDate(new Date());

            Iterator<Ingredient> i = ingredientList.iterator();
            while(i.hasNext()){
                Ingredient ing = i.next();
                if(ing.getIsFromRecipe()){
                    i.remove();
                }
            }

            checkValue = d.saveShoppingList(this);

           // this.ingredientList.clear();
            //for(Ingredient ing:this.ingredientListFromStorage ){
            //    this.ingredientList.add(ing);
            //}
            for(Recipe r:this.recipesAdded){
                r.setIsAddedToShoppingList(false);
                for(Ingredient ing:r.ingredientList){
                    ing.setIsFromRecipe(true);
                    ing.setRecipeName(r.getName());
                    ing.setDisplayName(ing.getName());
                }
            }
          // for(Ingredient ing:this.ingredientList){
           //     ing.setDisplayName(ing.getName());
           // }
            addIngredientsFromRecipes();

        } else{checkValue = false;}
        return checkValue;
    }

    private boolean delete(){
        if(context != null){
            DataLayer d = new DataLayer(context);
            return d.deleteShoppingList(this);
        }
        else{return false;}
    }

}


/*
    public boolean doesShoppingListExistInStorage(String listName){
        boolean checkFlag = false;

        //TODO: Add call to DataLayer to look for shoppinglist name

        return checkFlag;
    } */


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
