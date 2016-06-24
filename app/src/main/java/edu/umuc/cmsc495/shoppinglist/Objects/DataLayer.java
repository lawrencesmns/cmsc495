package edu.umuc.cmsc495.shoppinglist.Objects;

import android.content.Context;
import android.util.Log;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParserFactory;

/**
 * Created by James on 6/7/2016.
 *
 * DataLayer functions as the data core of the app. It provides the containers of the recipes
 * and shopping lists, and the methods to add to or remove these objects.
 */
public class DataLayer{

    //Class Variables
    //Consts
    public final static char[] INVALID_FILE_NAME_CHARS = {'?',':','\\','\"','\'','*','|','/','<','>',';'};
    public final static String RECIPE_FILE_PREFIX = "r_";
    public final static String SHOPPING_LIST_FILE_PREFIX = "sl_";
    public final static String FILE_EXTENSION = ".xml";

    static Context context;

    //Constructor that stores the application context for file operations
    public DataLayer(Context context){
        this.context = context;
    }

   //region Recipe Handlers
    public boolean saveRecipe(Recipe recipe){
        return XML_Stream_Handler.writeRecipe(context, recipe);
    }

    public  boolean deleteRecipe(Recipe recipe){
        String fileName = RECIPE_FILE_PREFIX + recipe.getName() + FILE_EXTENSION;
        return removeFile(fileName);
    }

    //Parses a recipe file
    public static Recipe parseRecipe(String name){
        InputStream is = getFile(name, RECIPE_FILE_PREFIX);

        SAXHandler_Recipe saxHandler = new SAXHandler_Recipe();
        try {
            // create a XMLReader from SAXParser
            XMLReader xmlReader = SAXParserFactory.newInstance().newSAXParser()
                    .getXMLReader();
            // create a SAXXMLHandler
            saxHandler = new SAXHandler_Recipe();
            // store handler in XMLReader
            xmlReader.setContentHandler(saxHandler);
            // parses
            xmlReader.parse(new InputSource(is));

        } catch (Exception ex) {
            Log.d("XML Oops", ex.getMessage());
        } finally{
            try{
                is.close();
            }catch(Exception ex){}
        }
        return saxHandler.getRecipe();
    }

    //endregion

    //region Shopping List Handlers
    //Writes a shopping list to file
    public  boolean saveShoppingList(ShoppingList shoppingList){
        return XML_Stream_Handler.writeShoppingList(context, shoppingList);
    }

    public boolean deleteShoppingList(ShoppingList shoppingList){
        String fileName = SHOPPING_LIST_FILE_PREFIX + shoppingList.getName() + FILE_EXTENSION;
        return removeFile(fileName);
    }


    //Parses a shopping list file
    public static ShoppingList parseList(String name){
        InputStream is = getFile(name, SHOPPING_LIST_FILE_PREFIX);

        // create a SAXXMLHandler
        SAXHandler_ShoppingList saxHandler = new SAXHandler_ShoppingList();
        try {
            // create a XMLReader from SAXParser
            XMLReader xmlReader = SAXParserFactory.newInstance().newSAXParser()
                    .getXMLReader();

            // store handler in XMLReader
            xmlReader.setContentHandler(saxHandler);
            // parses
            xmlReader.parse(new InputSource(is));

        } catch (Exception ex) {
            Log.d("XML Oops", ex.getMessage());
        } finally{
            try{
                is.close();
            }catch(Exception ex){}
        }
        return saxHandler.getShoppingList();
    }

    //endregion

    //region File Handlers
    //Private method to remove the recipe from memory and also it's file.
    private boolean removeFile(String fileName){
        try {
            return context.deleteFile(fileName);
        }catch (Exception e){
            return false;
        }
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

    private static InputStream getFile(String name, String FileNamePrefix){
        File file = new File(context.getFilesDir() + "/" + FileNamePrefix + name + DataLayer.FILE_EXTENSION);
        InputStream is = null;
        try {
          is = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Sorry, that file doesn't exist.");
        }
        return is;
    }

    //endregion





    //Private method to remove the recipe from memory and also it's file.
    //  private boolean saveShoppingList(ShoppingList shoppingList){
    //      String fileName = "sl_"+shoppingList.getName()+".xml";
    //      Log.d("save List","fileName");
    //      return writeList(shoppingList);
    //  }


    // private ArrayList<Recipe> recipes = new ArrayList<>();
    // private ArrayList<ShoppingList> shoppingLists = new ArrayList<ShoppingList>();



    //Private method to remove recipe from active structures
    // private boolean removeFromMemory(Recipe recipe){
    //     for (Recipe r: this.recipes){
    //         if(r.getRecipeName().equals(recipe.getRecipeName())){
    //             this.recipes.remove(r);
    //             return true;
    //         }
    //     }
    //     return false;
    // }


    /**
     * Method to add a new shopping list into the active memory, and also write it to file.
     * @param shoppingList
     * @return boolean: true if the file was remvoed and deleted successfully.
     */
    // public boolean addShoppingList(ShoppingList shoppingList){
    //   this.shoppingLists.add(shoppingList);
    //    return writeList(shoppingList);
    // }


    /**
     * Method to remove a target shopping list from local memory and delete it's file.
     //     * @param targetShoppingList
     * @return
     */

    //Private method to remove the list from file
    // private boolean removeList(ShoppingList shoppingList){
    //     String fileName = "r_"+shoppingList.getName()+".xml";
    //     Log.d("delete List","fileName");

    //   if(isFilePresent(fileName)){
    //     File fileSrc = context.getFilesDir();
    //   File[] allSrcFiles = fileSrc.listFiles();

    //       for(File child: allSrcFiles){
    //         if(child.getName().equals(fileName)){
    //           child.delete();
    //         return true;
    //   return removeFromMemory(shoppingList);
    //   }
    //        }
    //   }
    //    return false;
    //  }







    // public List<Recipe> getRecipeList(){return this.recipes;}
   // public List<ShoppingList> getShoppingLists(){return this.shoppingLists; }
}

/**
 * Method to add a new recipe into the active memory, and also write it to file.
 //* @param newRecipe: Recipe to be added.
 * @return boolean: true if the recipe was successfully loaded and written
 */
//public boolean addRecipe(Recipe newRecipe){
// this.recipes.add(newRecipe);
//    return saveRecipe(newRecipe);
// }
//Removes recipe from active structures
//private boolean removeFromMemory(ShoppingList shoppingList){
//    for (ShoppingList l: this.shoppingLists){
//        if(l.getName().equals(l.getName())){
//            this.shoppingLists.remove(l);
//            return true;
//        }
//    }
//    return false;
// }




//Private method to debug, and hands off to recipe writer.
// private boolean saveRecipe(Recipe recipe){
//  String fileName = FileUtils.RECIPE_FILE_PREFIX + recipe.getName() + FileUtils.FILE_EXTENSION ;
//  Log.d("save Recipe","fileName");
//    return writeRecipe(recipe);
// }

/**
 * Method to remove a target recipe from local emmory and delete it's file.
 * @param targetRecipe: recipe to be deleted.
 * @return boolean: true if the file was successfuly destroyed.
 */
