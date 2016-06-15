package edu.umuc.cmsc495.shoppinglist.Objects;

import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.*;
import java.util.*;

/**
 * Created by James on 6/14/2016.
 */
public class SAXHandler_Recipe extends DefaultHandler{

    //DataLayer data;
    String elementValue = null;
    Boolean elementOn = false;
    Recipe tempRecipe;

    public static List<Recipe> recipes = new ArrayList<Recipe>();
        public static List<Recipe> getXMLData(){ return recipes; }
        public static void setXMLData(List<Recipe> data){ SAXHandler_Recipe.recipes = data; }


    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {

        //reset
        elementValue = "";
        if(qName.equalsIgnoreCase("Recipe")){
            //new recipe
            tempRecipe = new Recipe();
        }

    }

    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        elementValue = new String(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        if (qName.equalsIgnoreCase("employee")) {
            // add it to the list
            recipes.add(tempRecipe);
        } else if (qName.equalsIgnoreCase("Name")) {
            tempRecipe.setName(elementValue);
            Log.i("recipe name;", elementValue);
        } else if (qName.equalsIgnoreCase("Instructions")) {
            tempRecipe.setInstructions(elementValue);
            Log.i("instructions;", elementValue);
        } else if (qName.equalsIgnoreCase("Ingredient")) {
            tempRecipe.addIngredient(elementValue);
            Log.i("ingredient;", tempRecipe.ingredientList.get(tempRecipe.ingredientList.size() - 1).getName());
        } else if (qName.equalsIgnoreCase("emailBody")) {
            tempRecipe.setEmailBody(elementValue);
            Log.i("email body;", elementValue);
        } else if (qName.equalsIgnoreCase("emailSubject")) {
            tempRecipe.setEmailSubject(elementValue);
            Log.i("email subject;", elementValue);
        }

    }
}
