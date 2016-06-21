package edu.umuc.cmsc495.shoppinglist.Objects;

import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.*;

/**
 * Created by James on 6/14/2016.
 */
public class SAXHandler_Recipe extends DefaultHandler{

    //DataLayer data;
    private String elementValue = null;
    private Recipe tempRecipe = new Recipe();
    private Ingredient tempIngredient = new Ingredient();

    public Recipe getRecipe(){return tempRecipe;}

    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {
        //reset
        elementValue = "";
    }

    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        elementValue = new String(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        if (qName.equalsIgnoreCase("Recipe")) {
            // add it to the list
           // Log.d("Recipe loaded", tempRecipe.getName());
        } else if (qName.equalsIgnoreCase("Name")) {
            tempRecipe.setName(elementValue);
           // Log.i("recipe name;", elementValue);
        } else if (qName.equalsIgnoreCase("CreatedOn")) {
            tempRecipe.setCreatedOn(elementValue);
        } else if (qName.equalsIgnoreCase("LastModifiedOn")) {
                tempRecipe.setCreatedOn(elementValue);
           // Log.i("created on;", elementValue);
        } else if (qName.equalsIgnoreCase("Instructions")) {
            tempRecipe.setInstructions(elementValue);
           // Log.i("instructions;", elementValue);
        } else if (qName.equalsIgnoreCase("Ingredient")) {
            tempIngredient = new Ingredient();
            // Log.i("new ingredient;", elementValue);
        } else if (qName.equalsIgnoreCase("IngName")) {
            tempIngredient.setName(elementValue);
            // Log.i("ingredient name;", elementValue);
        } else if (qName.equalsIgnoreCase("Measurement")) {
            tempIngredient.setMeasurement(elementValue);
            // Log.i("ingredient measurement;", elementValue);
        } else if (qName.equalsIgnoreCase("CountFull")) {
            tempIngredient.setCountFull(elementValue);
            //  Log.i("count full;", elementValue);
        } else if (qName.equalsIgnoreCase("CountPart")) {
            tempIngredient.setCountPartial(elementValue);
            // Log.i("ingredientCountPartial;", elementValue);
        }
    }


}

//Log.d("localName", localName);
//Log.d("qName", qName);
//    if(qName.equalsIgnoreCase("Recipe")){
//        //new recipe
//       Log.i("Handler", "Creating new recipe object");
//      this.tempRecipe = new Recipe();
//  }


//public static List<Recipe> recipes = new ArrayList<Recipe>();
//    public static List<Recipe> getXMLData(){ return recipes; }
//    public static void setXMLData(List<Recipe> data){ SAXHandler_Recipe.recipes = data; }


