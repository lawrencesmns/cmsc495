package edu.umuc.cmsc495.shoppinglist.Objects;

import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by James on 6/14/2016.
 */
public class SAXHandler_ShoppingList extends DefaultHandler{

    //DataLayer data;
    private String elementValue = null;

    private ShoppingList tempList = new ShoppingList();
    private Ingredient  tempIngredient = new Ingredient();
    private Recipe tempRecipe = new Recipe();

    public ShoppingList getShoppingList(){return tempList;}

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
        if (qName.equalsIgnoreCase("ShoppingList")) {
            // add it to the list
           Log.d("List added", tempList.getName());
        } else if (qName.equalsIgnoreCase("Name")) {
            tempList.setName(elementValue);
            //Log.i("list name;", elementValue);
        } else if (qName.equalsIgnoreCase("CreatedOn")) {
            tempList.setCreatedOn(elementValue);
        } else if (qName.equalsIgnoreCase("LastModifiedOn")) {
            tempList.setLastModifiedOn(elementValue);
        } else if (qName.equalsIgnoreCase("Ingredient")) {
            tempIngredient = new Ingredient();
            Log.i("new ingredient;", elementValue);
        } else if (qName.equalsIgnoreCase("IngName")) {
            tempIngredient.setName(elementValue);
            Log.i("ingredient name;", elementValue);
        } else if (qName.equalsIgnoreCase("Measurement")) {
            tempIngredient.setMeasurement(elementValue);
            Log.i("ingredient measurement;", elementValue);
        } else if (qName.equalsIgnoreCase("CountFull")) {
            tempIngredient.setCountFull(elementValue);
            Log.i("count full;", elementValue);
        } else if (qName.equalsIgnoreCase("CountPart")) {
            tempIngredient.setCountPartial(elementValue);
            Log.i("ingredientCountPartial;", elementValue);
        } else if (qName.equalsIgnoreCase("IsCrossedOut")) {
            tempIngredient.setCrossedOut(Boolean.parseBoolean(elementValue));
            tempList.addIngredient(tempIngredient);
            Log.i("ingredientCrossedOut;", elementValue);
        } else if (qName.equalsIgnoreCase("Recipe")) {
            tempList.addRecipe(tempRecipe);
            tempRecipe = new Recipe();
        } else if (qName.equalsIgnoreCase("RecipeName")) {
            tempRecipe.setName(elementValue);
        } else if (qName.equalsIgnoreCase("IngredientR")) {
            tempIngredient = new Ingredient();
        } else if (qName.equalsIgnoreCase("IngNameR")) {
            tempIngredient.setName(elementValue);
            Log.i("ingredient name;", elementValue);
        } else if (qName.equalsIgnoreCase("MeasurementR")) {
            tempIngredient.setMeasurement(elementValue);
            Log.i("ingredient measurement;", elementValue);
        } else if (qName.equalsIgnoreCase("CountFullR")) {
            tempIngredient.setCountFull(elementValue);
            Log.i("count full;", elementValue);
        } else if (qName.equalsIgnoreCase("CountPartR")) {
            tempIngredient.setCountPartial(elementValue);
            tempRecipe.addIngredient(tempIngredient);
        }


    }
}