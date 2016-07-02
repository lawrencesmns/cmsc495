package edu.umuc.cmsc495.shoppinglist.Objects;

import android.content.Context;

import java.io.Serializable;
import java.util.*;
/**
 * Created by James on 6/7/2016.
 */
public class Recipe extends GbList implements Serializable {
    //Class Variable Declarations
    //Class Variable Declarations
    private String instructions = "";


    private static Context context;

    //Constructors
    public Recipe(Context context){
        this.createdOn = UiUtils.getDateTimeNow();
        this.lastModifiedOn = UiUtils.getDateTimeNow();
        this.context = context;
        this.isPersisting = true;
    }
    protected Recipe(){
        this.createdOn = UiUtils.getDateTimeNow();
        this.isPersisting = false;
    }

    public static Recipe loadRecipe(String name){ //probably not the cleanest
        if(context !=null){
            /*Recipe returnList = new Recipe(context);
            Recipe parsedList = DataLayer.parseRecipe(name);
            for(Ingredient i : parsedList.getIngredientList()){
                returnList.addIngredient(i);
            }
            returnList.setName(parsedList.getName());
            returnList.createdOn = parsedList.createdOn;
            returnList.setInstructions(parsedList.getInstructions());*/
            return DataLayer.parseRecipe(name);
        }

        return new Recipe();

    }

    public Recipe createNewRecipe(){
        DataLayer d = new DataLayer(this.context);
        this.name = d.createEmptyRecipe();
        save();
        return this;
    }

    public String getInstructions(){
        return this.instructions;
    }



    public String getEmailSubject(){
        return super.getEmailSubject("Recipe");
    }

    public String getEmailBodyText(){
        String output = this.name + ": " + UiUtils.emailNewLine()+ UiUtils.emailNewLine()+ UiUtils.emailNewLine();
        output += "Ingredients:" + UiUtils.emailNewLine();
        output += getIngredientsForEmailBody();
        output += UiUtils.emailNewLine() + UiUtils.emailNewLine();
        output += "Instructions:" + UiUtils.emailNewLine();
        output += this.instructions;
        return output;
    }
    //endregion

    //for the set methods, call to save for every set
    //region set Methods

    public void setInstructions(String newInstructions){
        this.instructions = newInstructions;
        save();
    }
    public void setName(String newRecipeName) {
        delete();
        this.name = clearInvalidChars(newRecipeName);
        save();
    }
    public void addIngredient(Ingredient newIngredient){
        super.addIngredient(newIngredient);
        save();
    }

    public void removeIngredient(Ingredient ingredient){
        super.removeIngredient(ingredient);
        save();
    }

    public void changeIngredient(Ingredient revisedIngredient, Ingredient originalIngredient) {
        super.changeIngredient(revisedIngredient, originalIngredient);
        save();
    }

    //endregion

    //Saves the recipe
    private boolean save(){
        boolean checkValue = true;
        //TODO: check if it can eb saved, like enough free space
        if(context != null && this.isPersisting){
            DataLayer d = new DataLayer(context);

            checkValue = d.deleteRecipe(this);

            this.lastModifiedOn = UiUtils.formatDate(new Date());

            checkValue = d.saveRecipe(this);

        }else{checkValue = false;}

        return checkValue;
    }

    private boolean delete(){
        if(context != null) {
            DataLayer d = new DataLayer(context);
            return d.deleteRecipe(this);
        }else{return false;}
    }

}
