package edu.umuc.cmsc495.shoppinglist.Objects;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by James on 6/14/2016.
 *
 * XML Getter/Setter class for ShoppingList object
 */
public class XML_GS_ShoppingList extends XML_GettersSetters{

    private ArrayList<String> listName = new ArrayList<String>();
        public ArrayList<String> getRecipes(){ return this.listName; }
        public void setListName(String name){
            this.listName.add(name);
            Log.i("list name:", name);
        }

    private ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();
        public ArrayList<Ingredient> getIngredients(){ return this.ingredients; }
        public void setIngredients(Ingredient newIngredient){
            this.ingredients.add(newIngredient);
            Log.i("ingredient name:", newIngredient.getName());
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


}
