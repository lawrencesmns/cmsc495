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
import javax.xml.stream.*;

/**
 * Created by James on 6/7/2016.
 * Last Modified by james on 6/7/2016.
 */
public class DataLayer{

    //Class Variables
    private final String RECIPE_FILE_PREFIX = "r_";
    private final String SHOPPINGLIST_FILE_PREFIX = "sl_";
    private final String XML_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>";
    private final char[] INVALID_FILE_NAME_CHARS = {'?',':','\\','\"','\'','*','|','/','<','>',';'};

    Context context;
    private List<Recipe> recipes = new ArrayList<Recipe>();
    private List<ShoppingList> shoppingLists = new ArrayList<ShoppingList>();

    protected DataLayer(Context context){
        this.context = context;
    }

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

    protected List<Recipe> getRecipeList(){return this.recipes;}
    protected List<ShoppingList> getShoppingLists(){return this.shoppingLists; }

    //Writes a recipe to file
    protected boolean writeRecipe(Recipe recipe){
        XML_Stream_Handler xmlSH = new XML_Stream_Handler();
        return xmlSH.writeRecipe(this.context, recipe);
    }

    //Parses a recipe file
    protected void parseRecipe(InputStream is){
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

            is.close();

        } catch (Exception ex) {
            Log.d("XML Oops", ex.getMessage());
        }

        // appends recipes to end of list.
        this.recipes.addAll(recipes);
    }

    //Parses a shopping list file
    protected void parseList(InputStream is){
        List<ShoppingList> lists = new ArrayList<ShoppingList>();

        try {
            // create a XMLReader from SAXParser
            XMLReader xmlReader = SAXParserFactory.newInstance().newSAXParser()
                    .getXMLReader();
            // create a SAXXMLHandler
            SAXHandler_ShoppingList saxHandler = new SAXHandler_ShoppingList();
            // store handler in XMLReader
            xmlReader.setContentHandler(saxHandler);
            // parses
            xmlReader.parse(new InputSource(is));

            // get the recipes
            lists.addAll(saxHandler.getXMLData());

            is.close();

        } catch (Exception ex) {
            Log.d("XML Oops", ex.getMessage());
        }

        // appends recipes to end of list.
        this.shoppingLists.addAll(lists);
    }
}
