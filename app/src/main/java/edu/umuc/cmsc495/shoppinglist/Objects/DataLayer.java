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

    //Adds recipe to the list, then calls Save()
    protected boolean addRecipe(Recipe newRecipe){
        this.recipes.add(newRecipe);
        return saveRecipe(newRecipe);
    }
    private boolean saveRecipe(Recipe recipe){
        String fileName = "r_"+recipe.getRecipeName()+".xml";
        Log.d("save Recipe","fileName");
        if(writeRecipe(recipe)){
            return true;        //File written
        }else{
            return false;       //Something apparently broke
        }
    }

    //Call to remove the recipe from memory and the delete the file
    protected boolean deleteRecipe(Recipe targetRecipe){
        removeRecipe(targetRecipe);
        return true;
    }

    //Removes the recipe file
    private boolean removeRecipe(Recipe recipe){
        String fileName = "r_"+recipe.getRecipeName()+".xml";
        Log.d("delete Recipe","fileName");

        if(isFilePresent(fileName)){
            File fileSrc = context.getFilesDir();
            File[] allSrcFiles = fileSrc.listFiles();

            for(File child: allSrcFiles){
                if(child.getName().equals(fileName)){
                    child.delete();
                    return removeFromMemory(recipe);
                }
            }
        }
        return false;
    }

    //Removes recipe from active structures
    private boolean removeFromMemory(Recipe recipe){
        for (Recipe r: this.recipes){
            if(r.getRecipeName().equals(recipe.getRecipeName())){
                this.recipes.remove(r);
                return true;
            }
        }
        return false;
    }

    //Adds shopping list to memory to structures, then write to file.
    protected boolean addShoppingList(ShoppingList shoppingList){
        this.shoppingLists.add(shoppingList);
        return writeList(shoppingList);
    }
    private boolean saveShoppingList(ShoppingList shoppingList){
        String fileName = "sl_"+shoppingList.getName()+".xml";
        Log.d("save List","fileName");
        if(writeList(shoppingList)){
            return true;        //File written
        }else{
            return false;       //Something apparently broke
        }
    }

    protected boolean deleteShoppingList(ShoppingList targetShoppingList){
        return removeList(targetShoppingList);
    }
    private boolean removeList(ShoppingList shoppingList){
        String fileName = "r_"+shoppingList.getName()+".xml";
        Log.d("delete List","fileName");

        if(isFilePresent(fileName)){
            File fileSrc = context.getFilesDir();
            File[] allSrcFiles = fileSrc.listFiles();

            for(File child: allSrcFiles){
                if(child.getName().equals(fileName)){
                    child.delete();
                    return removeFromMemory(shoppingList);
                }
            }
        }
        return false;
    }
    //Removes recipe from active structures
    private boolean removeFromMemory(ShoppingList shoppingList){
        for (ShoppingList l: this.shoppingLists){
            if(l.getName().equals(l.getName())){
                this.shoppingLists.remove(l);
                return true;
            }
        }
        return false;
    }


    //Checks if String fileName is already present
    private boolean isFilePresent(String fileName){
        File fileSrc = context.getFilesDir();
        File[] allSrcFiles = fileSrc.listFiles();

        for(File child: allSrcFiles){
            if(child.getName().equals(fileName)){
                return true;
            }
        }

        return false;
    }







    //Writes a recipe to file
    protected boolean writeRecipe(Recipe recipe){
        XML_Stream_Handler xmlSH = new XML_Stream_Handler();
        return xmlSH.writeRecipe(this.context, recipe);
    }

    //Writes a recipe to file
    protected boolean writeList(ShoppingList list){
        XML_Stream_Handler xmlSH = new XML_Stream_Handler();
        return xmlSH.writeShoppingList(this.context, list);
    }

    //Parses a recipe file
    protected void parseRecipe(InputStream is){
        List<Recipe> recipes = new ArrayList<Recipe>();

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
            recipes.addAll(saxHandler.getXMLData());

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

    protected List<Recipe> getRecipeList(){return this.recipes;}
    protected List<ShoppingList> getShoppingLists(){return this.shoppingLists; }
}
