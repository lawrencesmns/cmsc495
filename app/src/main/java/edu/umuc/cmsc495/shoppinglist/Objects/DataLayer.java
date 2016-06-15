package edu.umuc.cmsc495.shoppinglist.Objects;

import android.content.Context;
import android.util.Log;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by James on 6/7/2016.
 * Last Modified by james on 6/7/2016.
 */
public class DataLayer{

    //Class Variables
    String RECIPE_FILE_PREFIX = "r_";
    String SHOPPINGLIST_FILE_PREFIX = "sl_";
    char[] INVALID_FILE_NAME_CHARS = {'?',':','\\','\"','\'','*','|','/','<','>',';'};

    private List<Recipe> recipes = new ArrayList<Recipe>();




    protected boolean addRecipe(Recipe newRecipe){

        return  true;
    }

    protected boolean addShoppingList(ShoppingList shoppingList){

        return true;
    }

    protected boolean deleteRecipe(Recipe targetRecipe){

        return true;
    }

    protected boolean deleteShoppingList(ShoppingList targetShoppingList){

        return true;
    }

    protected FileList getRecipeList(){
        return null;
    }

    protected FileList getShoppingLists(){
        return null;
    }

    protected Recipe getRecipe(){

        return null;
    }

    protected ShoppingList getShoppingList(){
        return null;
    }

    //Checks the file name and attempts to read it into this.
    protected void parseFile(InputStream is){
        List<Recipe> recipes = null;

        try {
            // create a XMLReader from SAXParser
            XMLReader xmlReader = SAXParserFactory.newInstance().newSAXParser()
                    .getXMLReader();
            // create a SAXXMLHandler
            SAXHandler_Recipe saxHandler = new SAXHandler_Recipe();
            // store handler in XMLReader
            xmlReader.setContentHandler(saxHandler);
            // parses
            xmlReader.parse(new InputSource(is));

            // get the recipes
            recipes = saxHandler.getXMLData();

        } catch (Exception ex) {
            Log.d("XML Oops", ex.getMessage());
        }

        // appends recipes to end of list.
        this.recipes.addAll(recipes);
    }
}
