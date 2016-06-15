package edu.umuc.cmsc495.shoppinglist.Objects;

import java.util.*;

/**
 * Created by James on 6/7/2016.
 */
public class Recipe {
    //Class Variable Declarations
    String recipeName, instructions, emailBody, emailSubject;
    List<Ingredient> ingredientList;



    //Adds an ingredient to the list
    protected boolean addIngredient(Ingredient newIngredient){
        if(checkIngredientName(newIngredient)){
            ingredientList.add(newIngredient);
            return true;
        }else{
            System.out.println("Ingredient is already present! Edit the quantity!");
            return false;
        }
    }

    //Removes an ingredient
    protected void removeIngredient(String ingredientName){
        for (Ingredient i: ingredientList) {
            if(i.getName().compareTo(ingredientName) == 0){
                ingredientList.remove(i);
                System.out.println("Ingredient removed from recipe!");
                break;
            }
        }
    }

    //Saves the recipe
    protected boolean Save(){
        boolean checkValue = true;

        //TODO: check if it can eb saved, then call the dataLayer to write.

        return checkValue;
    }

    //Checks if the ingredient name is already present
    private boolean checkIngredientName(Ingredient check){
        for (Ingredient i : ingredientList) {
            if(i.getName().compareTo(check.getName()) == 0){
                return true;    //matching ingredient name found
            }
        }
        return false;   //No matching ingredient name
    }

    //set Methods
    protected void setName(String newRecipeName){
        this.recipeName = newRecipeName;
    }
    protected void setInstructions(String newInstructions){
        this.instructions = newInstructions;
    }
    protected void addIngredient(String input){
        List<String> inputSplit = Arrays.asList(input.split(","));
        String name = inputSplit.get(0);
        String measurement = inputSplit.get(1);
        int count = Integer.parseInt(inputSplit.get(2));
        Ingredient tempIngredient = new Ingredient(name, measurement, count);
        this.ingredientList.add(tempIngredient);
    }
    protected void setEmailBody(String body){
        this.emailBody = body;
    }
    protected void setEmailSubject(String subject){
        this.emailSubject = subject;
    }


    //get Methods
    protected String getRecipeName(){
        return this.recipeName;
    }
    protected String getInstructions(){
        return this.instructions;
    }
    protected List<Ingredient> getIngredientList(){
        return this.ingredientList;
    }
    protected String getEmailBody(){
        return this.emailBody;
    }
    protected String getEmailSubject(){
        return this.emailSubject;
    }
}
