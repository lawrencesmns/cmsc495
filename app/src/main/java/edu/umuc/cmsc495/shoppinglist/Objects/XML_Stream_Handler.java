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

            return true;
        }catch (Exception e){
            return false;
        }

    }
}
