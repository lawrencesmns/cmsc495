package edu.umuc.cmsc495.shoppinglist.Objects;

import android.content.Context;

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
   // private final static String XML_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>";
    protected static boolean writeRecipe(Context context, Recipe recipe){

        try{
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            //root element
            Element rootElement = doc.createElement("Recipe");
            doc.appendChild(rootElement);

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
            rootElement.appendChild(createdOn);

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
                ingName.appendChild(doc.createTextNode(ing.getMeasurement()));
                ingredient.appendChild(measurement);

                Element countFull = doc.createElement("CountFull");
                ingName.appendChild(doc.createTextNode(ing.getCountFullString()));
                ingredient.appendChild(countFull);

                Element countPartial = doc.createElement("CountPart");
                ingName.appendChild(doc.createTextNode(ing.getCountPartialString()));
                ingredient.appendChild(countPartial);

                ingredients.appendChild(ingredient);
            }

            rootElement.appendChild(ingredients);

            //Builds file path to write to
            String fileLocationPath = context.getFilesDir().getAbsolutePath();
            String newFileName = DataLayer.RECIPE_FILE_PREFIX + recipe.getName() + DataLayer.FILE_EXTENSION;
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

    protected static boolean writeShoppingList(Context context, ShoppingList list){

        try{
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            //root element
            Element rootElement = doc.createElement("ShoppingList");
            doc.appendChild(rootElement);

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
            String newFileName = DataLayer.SHOPPING_LIST_FILE_PREFIX + list.getName()+ DataLayer.FILE_EXTENSION;
            String filePathToCreate = fileLocationPath + "/" + newFileName;

            //Writes to XML file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult((new File(filePathToCreate)));
            transformer.transform(source, result);

            StreamResult localResult = new StreamResult(new File(filePathToCreate));
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
