package edu.umuc.cmsc495.shoppinglist.Objects;

import android.content.Context;
import android.provider.DocumentsContract;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * Created by James on 6/15/2016.
 */
public class XML_Stream_Handler{

    protected boolean writeRecipe(Context context, Recipe recipe){

        try{
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            //root element
            Element rootElement = doc.createElement("Recipe");

            //Name Element
            Element recipeName = doc.createElement("Name");
            recipeName.appendChild(doc.createTextNode(recipe.getRecipeName()));
            rootElement.appendChild(recipeName);

            //Instructions Element
            Element recipeInstructions = doc.createElement("Instructions");
            recipeInstructions.appendChild(doc.createTextNode(recipe.getInstructions()));
            rootElement.appendChild(recipeInstructions);

            //Loops to add all ingredients
            for(Ingredient ing: recipe.getIngredientList()){
                Element ingredient = doc.createElement("Ingredient");
                ingredient.appendChild(doc.createTextNode(ing.toString()));
                rootElement.appendChild(ingredient);
            }

            //Builds file path to write to
            String fileLocationPath = context.getFilesDir().getAbsolutePath();
            String newFileName = "r_" + recipe.getRecipeName();
            String filePathToCreate = fileLocationPath + "/" + newFileName;

            //Writes to XML file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult((new File(filePathToCreate)));
            transformer.transform(source, result);

            if(result.getOutputStream() != null){
                try{
                    result.getOutputStream().close();
                }catch(Exception e){}
                result.setOutputStream(null);
            }


            return true;
        }catch (Exception e){
            return false;
        }

    }

    protected boolean writeShoppingList(Context context, ShoppingList list){

        try{
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            //root element
            Element rootElement = doc.createElement("ShoppingList");

            //Name Element
            Element listName = doc.createElement("Name");
            listName.appendChild(doc.createTextNode(list.getName()));
            rootElement.appendChild(listName);

            //Loops to add all ingredients
            for(Ingredient ing: list.getIngredientList()){
                Element ingredient = doc.createElement("Ingredient");
                ingredient.appendChild(doc.createTextNode(ing.toString()));
                rootElement.appendChild(ingredient);
            }

            //Builds file path to write to
            String fileLocationPath = context.getFilesDir().getAbsolutePath();
            String newFileName = "sl_" + list.getName();
            String filePathToCreate = fileLocationPath + "/" + newFileName;

            //Writes to XML file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult((new File(filePathToCreate)));
            transformer.transform(source, result);

            StreamResult localResult = new StreamResult(new File("C:/example.xml"));
            transformer.transform(source, localResult);

            if(result.getOutputStream() != null){
                try{
                    result.getOutputStream().close();
                    localResult.getOutputStream().close();
                }catch(Exception e){}
                result.setOutputStream(null);
            }

            return true;
        }catch (Exception e){
            return false;
        }
    }
}
