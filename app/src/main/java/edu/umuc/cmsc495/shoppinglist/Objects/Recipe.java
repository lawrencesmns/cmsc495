package edu.umuc.cmsc495.shoppinglist.Objects;

import java.io.Serializable;
import java.util.*;
/**
 * Created by James on 6/7/2016.
 */
public class Recipe implements Serializable {
    //Class Variable Declarations
    private String name, instructions, createdOn, lastModifiedOn;
    private ArrayList<Ingredient> ingredientList = new ArrayList<>();

    //Constructors
    public Recipe(){
        this.createdOn = UiUtils.getDateTimeNow();
        this.lastModifiedOn = UiUtils.getDateTimeNow();
    }



    public static Recipe loadRecipe(String name){ //probably not the cleanest
        return DataLayer.parseRecipe(name);
    }

    //region get Methods
    protected String getName(){
        return this.name;
    }
    protected String getInstructions(){
        return this.instructions;
    }
    protected List<Ingredient> getIngredientList(){
        return this.ingredientList;
    }
    protected String getLastModifiedOn(){return this.lastModifiedOn;}
    protected String getCreatedOn(){return this.createdOn;}

    protected String getEmailSubject(){
        return UiUtils.getAppName() + "   Recipe: " + this.name;
    }

    protected String getEmailBody(){
        String output = this.name + ": " + UiUtils.emailNewLine()+ UiUtils.emailNewLine()+ UiUtils.emailNewLine();
        output += "Ingredients:" + UiUtils.emailNewLine();
        for(Ingredient i:this.ingredientList){
            output += i.toString() + UiUtils.emailNewLine();
        }
        output += UiUtils.emailNewLine() + UiUtils.emailNewLine();
        output += "Instructions:" + UiUtils.emailNewLine();
        output += this.instructions;
        return output;
    }
    //endregion

    //for the set methods, call to save for every set
    //region set Methods
    protected void setName(String newRecipeName){
        delete();
        for(char c:DataLayer.INVALID_FILE_NAME_CHARS){
            newRecipeName = newRecipeName.replace(c, ' ');
        }
        this.name = newRecipeName;
        save();
    }
    protected void setInstructions(String newInstructions){
        this.instructions = newInstructions;
    }
    protected void setCreatedOn(String newCreatedOn){
        this.createdOn = newCreatedOn;
    }
    protected void setLastModifiedOn(String newLastModifiedOn){this.lastModifiedOn = newLastModifiedOn;
    }
    //endregion

    //Saves the recipe
    private boolean save(){
        boolean checkValue = true;
        //TODO: check if it can eb saved, like enough free space

        checkValue = DataLayer.deleteRecipe(this);

        lastModifiedOn = UiUtils.formatDate(new Date());

        if(checkValue){
            checkValue = DataLayer.saveRecipe(this);
        }

        return checkValue;
    }
    private boolean delete(){
        return DataLayer.deleteRecipe(this);
    }



    //Adds an ingredient to the list
    protected void addIngredient(Ingredient newIngredient){
        if(!isExistingIngredientByName(newIngredient)){
            ingredientList.add(newIngredient);
            save();
        }else{
            throw new IllegalArgumentException("This ingredient is already here, please modify the existing one.");
        }
    }

    //Removes an ingredient
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


    //Checks if the ingredient name is already present
    private boolean isExistingIngredientByName(Ingredient check){
        for (Ingredient i : ingredientList) {
            if(i.getName().compareTo(check.getName()) == 0){
                return true;    //matching ingredient name found
            }
        }
        return false;   //No matching ingredient name
    }




   // protected void addIngredient(String input){
   //     List<String> inputSplit = Arrays.asList(input.split(","));
   //     String name = inputSplit.get(0);
    //    String measurement = inputSplit.get(1);
    //    Double count = Double.parseDouble(inputSplit.get(2));
    //    Ingredient tempIngredient = new Ingredient(name, measurement, count);
    //    this.ingredientList.add(tempIngredient);
   // }


}
