package edu.umuc.cmsc495.shoppinglist.Objects;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by James on 6/14/2016.
 *
 * XML Getter/Setter class for Recipe object
 */
public class XML_GS_Recipe extends XML_GettersSetters{

    private ArrayList<String> recipeName = new ArrayList<String>();
        public ArrayList<String> getRecipes(){ return this.recipeName; }
        public void setRecipeName(String name){
            this.recipeName.add(name);
            Log.i("This is the name:", name);
        }

    private ArrayList<String> instructions = new ArrayList<String>();
    public ArrayList<String> getInstructions(){ return this.instructions; }
    public void setInstructions(String instructions){
        this.instructions.add(instructions);
        Log.i("instructions:", instructions);
    }

    private ArrayList<String> emailBody = new ArrayList<String>();
    public ArrayList<String> getEmailBody(){ return this.emailBody; }
    public void setEmailBody(String emailBody){
        this.emailBody.add(emailBody);
        Log.i("This is the email body:", emailBody);
    }

    private ArrayList<String> emailSubject = new ArrayList<String>();
    public ArrayList<String> getEmailSubject(){ return this.emailSubject; }
    public void setEmailSubject(String emailSubject){
        this.emailSubject.add(emailSubject);
        Log.i("emailSubject:", emailSubject);
    }

    private ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();
    public ArrayList<Ingredient> getIngredients(){ return this.ingredients; }
    public void setIngredients(Ingredient ingredients){
        this.ingredients.add(ingredients);
        Log.i("emailSubject:", ingredients.toString());
    }
}
