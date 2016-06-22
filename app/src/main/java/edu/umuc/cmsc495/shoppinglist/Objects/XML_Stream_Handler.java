package edu.umuc.cmsc495.shoppinglist.Objects;

import android.content.Context;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;
import java.io.FileOutputStream;
import java.io.StringWriter;

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
   // private final static String XML_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>";
    protected static boolean writeRecipe(Context context, Recipe recipe){

        try{
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            //root element
            Element rootElement = doc.createElement("Recipe");


            //Name Element
            Element recipeName = doc.createElement("Name");
            recipeName.appendChild(doc.createTextNode(recipe.getName()));
            rootElement.appendChild(recipeName);

            //Created On Element
            Element createdOn = doc.createElement("CreatedOn");
            recipeName.appendChild(doc.createTextNode(recipe.getCreatedOn()));
            rootElement.appendChild(createdOn);

            //Last Modified On Element
            Element lastModifiedOn = doc.createElement("LastModifiedOn");
            recipeName.appendChild(doc.createTextNode(recipe.getLastModifiedOn()));
            rootElement.appendChild(lastModifiedOn);

            //Instructions Element
            Element recipeInstructions = doc.createElement("Instructions");
            recipeInstructions.appendChild(doc.createTextNode(recipe.getInstructions()));
            rootElement.appendChild(recipeInstructions);

            Element ingredients = doc.createElement("Ingredients");

            //Loops to add all ingredients

            for(Ingredient ing: recipe.getIngredientList()){
                Element ingredient = doc.createElement("Ingredient");

                Element ingName = doc.createElement("IngName");
                ingName.appendChild(doc.createTextNode(ing.getName()));
                ingredient.appendChild(ingName);

                Element measurement = doc.createElement("Measurement");
                measurement.appendChild(doc.createTextNode(ing.getMeasurement()));
                ingredient.appendChild(measurement);

                Element countFull = doc.createElement("CountFull");
                countFull.appendChild(doc.createTextNode(ing.getCountFullString()));
                ingredient.appendChild(countFull);

                Element countPartial = doc.createElement("CountPart");
                countPartial.appendChild(doc.createTextNode(ing.getCountPartialString()));
                ingredient.appendChild(countPartial);

                ingredients.appendChild(ingredient);
            }

            rootElement.appendChild(ingredients);
            doc.appendChild(rootElement);

            //Builds file path to write to
            //String fileLocationPath = context.getFilesDir().getAbsolutePath();
            String newFileName = DataLayer.RECIPE_FILE_PREFIX + recipe.getName() + DataLayer.FILE_EXTENSION;
            //String filePathToCreate = fileLocationPath + "/" + newFileName;

            //Writes to XML file
            FileOutputStream fileos = context.openFileOutput(newFileName ,context.MODE_PRIVATE);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(fileos);
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

    protected static boolean writeShoppingList(Context context, ShoppingList list){

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

            //Created On Element
            Element createdOn = doc.createElement("CreatedOn");
            listName.appendChild(doc.createTextNode(list.getCreatedOn()));
            rootElement.appendChild(createdOn);

            //Last Modified On Element
            Element lastModifiedOn = doc.createElement("LastModifiedOn");
            listName.appendChild(doc.createTextNode(list.getLastModifiedOn()));
            rootElement.appendChild(lastModifiedOn);

            Element ingredients = doc.createElement("Ingredients");

            //Loops to add all ingredients

            for(Ingredient ing: list.getIngredientList()){
                Element ingredient = doc.createElement("Ingredient");

                Element ingName = doc.createElement("IngName");
                ingName.appendChild(doc.createTextNode(ing.getName()));
                ingredient.appendChild(ingName);

                Element measurement = doc.createElement("Measurement");
                measurement.appendChild(doc.createTextNode(ing.getMeasurement()));
                ingredient.appendChild(measurement);

                Element countFull = doc.createElement("CountFull");
                countFull.appendChild(doc.createTextNode(ing.getCountFullString()));
                ingredient.appendChild(countFull);

                Element countPartial = doc.createElement("CountPart");
                countPartial.appendChild(doc.createTextNode(ing.getCountPartialString()));
                ingredient.appendChild(countPartial);

                Element isCrossedOut = doc.createElement("IsCrossedOut");
                isCrossedOut.appendChild(doc.createTextNode(ing.getCrossed().toString()));
                ingredient.appendChild(isCrossedOut);

                ingredients.appendChild(ingredient);
            }

            rootElement.appendChild(ingredients);

            doc.appendChild(rootElement);

            //Builds file path to write to
           // String fileLocationPath = context.getFilesDir().getAbsolutePath();
            String newFileName = DataLayer.SHOPPING_LIST_FILE_PREFIX + list.getName()+ DataLayer.FILE_EXTENSION;
            //String filePathToCreate = fileLocationPath + "/" + newFileName;

            //Writes to XML file
            FileOutputStream fileos = context.openFileOutput(newFileName ,context.MODE_PRIVATE);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(fileos);
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
}
